package com.hz.sellcloud.schedule;

import cn.hutool.json.JSONUtil;
import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.service.ChartDataService;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

@EnableScheduling
@Component
@Slf4j
public class ActivityScheduleTask {

    @Autowired
    RedisService redisService;

    @Autowired
    ChartDataService chartDataService;

    static String ReduceLock = "ORDER_REDUCE";

    //设置定时任务处理Redis当中的订单缓存
    @Scheduled(cron = "*/2 * * * * *")
    public void UpdateOrder() throws Exception {
        // 加锁防止未在规定时间内执行完reduce操作
        synchronized (ReduceLock){
            log.info("------------ Order Reduce Running ----------------");
            Long length = redisService.lSize("orderlist");
            if (length <= 0) return;
            List<OrderVo.productVo> orderlist = new ArrayList<>();
            while (length > 0){
                String product = redisService.rPop("orderlist");
                OrderVo.productVo productVo = JSONUtil.toBean(product, OrderVo.productVo.class);
                orderlist.add(productVo);
                length--;
            }
            // 1. 获取 Flink 批处理环境
            ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
            // 2. 加载数据集
            DataSet<OrderVo.productVo> dataSet = env.fromCollection(orderlist);
            // 3. 对商品id进行分类
            UnsortedGrouping<OrderVo.productVo> groupedDataSet = dataSet.groupBy("product_id");
            // 4. 进行reduce操作
            ReduceOperator<OrderVo.productVo> reducedDataSet = groupedDataSet.reduce((p1, p2) -> {
                BigDecimal price = p1.getPrice().add(p2.getPrice());
                p1.setPrice(price);
                return p1;
            });
            // 5. 处理打印结果
            List<OrderVo.productVo> result = reducedDataSet.collect();
            if(result != null && result.size() != 0){
                chartDataService.updateSum(result);
                chartDataService.updateSales(result);
            }
            log.info("------------ Order Reduce Finished ----------------");
        }

    }
}
