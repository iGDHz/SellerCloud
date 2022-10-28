package com.hz.sellcloud;

import com.hz.sellcloud.config.EnvConfig;
import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Resource(name = "redisServices")
    RedisServiceImpl[] templates;
    @Test
    void contextLoads() {
    }

    @Test
    void RedisTest(){
//        redisService.set("key","val");
//        Object key = redisService.get("key");
//        System.out.println(key);
    }

    @Test
    void EnvironmentTest(){
        templates[0].del("hzkey");
        templates[1].del("hzkey");
        for (int i = 0; i < templates.length; i++) {
            RedisService template = templates[i];
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
                curRedis.setIsReduce(new AtomicBoolean(true));
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                templates[0].setIsReduce(new AtomicBoolean(false));
                templates[1].setIsReduce(new AtomicBoolean(false));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while(true) ;
    }
}
