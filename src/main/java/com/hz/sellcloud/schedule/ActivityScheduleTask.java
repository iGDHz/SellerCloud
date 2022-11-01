package com.hz.sellcloud.schedule;

import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;

@EnableScheduling
@Component
public class ActivityScheduleTask {

    @Autowired
    RedisManagement redisManagement;

    //设置定时任务处理Redis当中的订单缓存
    @Scheduled(cron = "0 */30 * * * *")
    public void UpdateActivity(){
        RedisServiceImpl curRedis = redisManagement.getCurRedis();
        curRedis.Reduce();
    }
}
