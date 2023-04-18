package com.hz.sellcloud;

import com.alibaba.fastjson.JSON;
import com.hz.sellcloud.config.EnvConfig;
import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.entity.Orders;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class SellcloudApplicationTests {

//    @Autowired
//    RedisServiceImpl redisService;
    @Autowired
    EnvConfig envConfig;
//    @Autowired
//    RedisManagment redisManagment;
    @Autowired
    RedisManagement redisManagement;

    @Resource(name = "redisService")
    RedisServiceImpl redisService;

    @Resource(name = "redisServices")
    RedisServiceImpl[] redisServices;
    
    
    @Test
    void contextLoads() {
    }

    @Test
    void EnvironmentTest(){
        redisServices[0].del("hzkey");
        redisServices[1].del("hzkey");
        for (int i = 0; i < redisServices.length; i++) {
            RedisService template = redisServices[i];
            System.out.println(template.get("hzkey"));
            template.set("hzkey","hzvalue");
            System.out.println(template.get("hzkey"));
        }
    }

    @Test
    void RedisManagementTest() throws InterruptedException {
        new Thread(()->{
            while (true){
                RedisServiceImpl curRedis = redisManagement.getCurRedis();
                System.out.println(curRedis);
                curRedis.setIsReduce(true);
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                redisServices[0].setIsReduce(false);
                redisServices[1].setIsReduce(false);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while(true) ;
    }

    @Test
    public void RedisTest(){

//        redisService.set("key","val");
//        Object key = redisService.get("key");
//        System.out.println(key);
        redisService.set("hello","world");
        System.out.println(redisService.get("hello"));
    }
    
    @Test
    public void ReduceTest() throws InterruptedException {
        new Thread(()->{
            while (true){
                RedisServiceImpl curRedis = redisManagement.getCurRedis();
                Long size = curRedis.lSize("orders");

                System.out.println("["+curRedis+"]当前Redis中的使用情况(共"+size+"条数据):");
                curRedis.lRange("orders",0,size)
                        .stream().forEach((order)->{
                    System.out.printf(order+",");
                });
                System.out.println();
            }
        }).start();
        for (int i = 0; i < 3; i++) {
            Random random = new Random(67);
            new Thread(()->{
                while(true){
                    RedisServiceImpl curRedis = redisManagement.getCurRedis();
                    Generator.OrdersGenerator ordersGenerator = new Generator.OrdersGenerator(10); //样本数
                    ArrayList<Orders> list = Stream.generate(ordersGenerator::getOrders)
                            .limit(100)
                            .collect(Collectors.toCollection(ArrayList::new));//测试数
                    for (Orders order : list) {
                        curRedis.lPush("orders",JSON.toJSONString(order));
                    }
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    curRedis.Reduce();
                }

            }).start();
        }
        Thread.sleep(1000_000_000);
    }

    @Test
    public void RedisFaid() throws InterruptedException {
        Timer timer = new Timer();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                redisServices[0].set("key" + i, "r0value" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                redisServices[1].set("key" + i, "r1value" + i);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                redisService.set("key" + i, "value" + i);
            }
        });
        System.out.println("time："+timer.duration()+"ms");
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                redisService.set("key" + (1000+i), "value" + (1000+i));
            }
        });
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        System.out.println("time："+timer.duration()+"ms");
    }

    @Test
    public void RedisSerialTest(){
        redisService.set("chinakey","中文key");
    }
    class Timer{
        long time;

        public Timer() {
            this.time = System.currentTimeMillis();
        }

        public long duration(){
            long pre = time;
            time = System.currentTimeMillis();
            return time-pre;
        }
    }
}
