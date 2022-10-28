package com.hz.sellcloud.config;

import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class RedisManagement {
    @Resource(name = "redisServices")
    RedisServiceImpl[] redisServices;

    @Value("${redisManagement.dbnum}")
    private int maxnum;

    volatile AtomicInteger curRedis = new AtomicInteger(0);

    //防止AtomicInterger越界
    private final int incrementAndGet() {
        int current;
        int next;
        do {
            current = curRedis.get();
            next = current >= maxnum?0:(current+1)%maxnum;
        } while(!curRedis.compareAndSet(current, next));

        return next;
    }

    /*
        @return : 获取当前使用的Redis连接
     */
//    volatile int cur;
    public RedisServiceImpl getCurRedis(){
//        cur = curRedis.get();
        if(redisServices[curRedis.get()].getIsReduce()){
            synchronized (this){
                while (redisServices[curRedis.get()].getIsReduce()){
                    incrementAndGet();
                }
            }
        }
        return redisServices[curRedis.get()];
    }
}
