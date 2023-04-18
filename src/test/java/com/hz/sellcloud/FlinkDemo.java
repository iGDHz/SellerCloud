package com.hz.sellcloud;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.service.RedisService;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.*;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import org.apache.flink.util.Collector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class FlinkDemo {
    @Autowired
    RedisService redisService;

    @Test
    public void demo(){
        // 从命令行参数中获取Redis相关参数
//        final ParameterTool params = ParameterTool.fromArgs(args);
//        String host = params.get("redis.host", "localhost");
//        int port = params.getInt("redis.port", 6379);
//        String channel = params.get("redis.channel", "flink-demo-channel");
        String host = "localhost";
        int port = 6379;
        String channel = "flink-demo-channel";
        // 设置Flink执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 连接到Redis
        final Jedis jedis = new Jedis(host, port);
        // 订阅指定的channel
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                // 从Redis中接收到数据
                // 对数据进行转换和统计
                DataStream<Tuple2<String, Integer>> counts = env.fromElements(message)
                        .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                            public void flatMap(String line, Collector<Tuple2<String, Integer>> out) throws Exception {
                                // 将每行数据以空格分割为单词
                                String[] words = line.split("\\s+");
                                // 将每个单词计数为1，并发射到下游算子
                                for (String word : words) {
                                    out.collect(new Tuple2<>(word, 1));
                                }
                            }
                        })
                        .keyBy(0)
                        .sum(1);
                // 输出结果到控制台
                counts.addSink(new RichSinkFunction<Tuple2<String, Integer>>() {
                    @Override
                    public void invoke(Tuple2<String, Integer> value) throws Exception {
                        System.out.println(value.f0 + " " +value.f1);
                    }
                });

                // 执行任务
                try {
                    env.execute("Flink Demo");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, channel);
    }

    @Test
    public void reducedemo() throws Exception {
        Long length = redisService.lSize("orderlist");
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
        List<OrderVo.productVo> collect = reducedDataSet.collect();
        for (OrderVo.productVo productVo : collect) {
            System.out.println(productVo.getProduct_id() + " " + productVo.getPrice());
        }
    }

    @Test
    public void producer(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            OrderVo.productVo productVo = new OrderVo.productVo();
            productVo.setPrice(new BigDecimal(random.nextInt(100)));
            productVo.setProduct_id(random.nextInt(10));
            productVo.setName("测试商品");
            productVo.setPicture("123");
            productVo.setQuantity(1);
            productVo.setCategory_id(1);
            redisService.lPush("orderlist", JSON.toJSONString(productVo));
        }
    }

}