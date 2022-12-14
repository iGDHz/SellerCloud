package com.hz.sellcloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class RedisInitalizer {
    Logger logger = LogManager.getLogger(RedisInitalizer.class);

    @Value("${redisManagement.dbnum}")
    private int maxnum;

    @Autowired
    private Environment environment;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean("redisService")
    public RedisServiceImpl RedisService(){
        return new RedisServiceImpl(redisTemplate);
    }

    @Bean("redisServices")
    public RedisServiceImpl[] RedisConnections(){
        logger.info("###### START ????????? Redis ????????? START ######");
        RedisServiceImpl[] rediss = new RedisServiceImpl[maxnum];
        for (int i = 0; i < maxnum; i++) {
            String pre = "redisManagement.redis"+(i+1)+".";
            StringRedisTemplate template = redisTemplateObject(i,environment.getProperty(pre+"host"),environment.getProperty(pre+"password"),
                    environment.getProperty(pre+"port",Integer.class));
            rediss[i] = new RedisServiceImpl(template);
        }
        logger.info("###### END ????????? Redis ????????? END ######");
        return rediss;
    }

    private StringRedisTemplate redisTemplateObject(Integer dbIndex,String host,String pwd,int port) {
        StringRedisTemplate redisTemplateObject = new StringRedisTemplate();
        redisTemplateObject.setConnectionFactory(redisConnectionFactory(jedisPoolConfig(), dbIndex,host,pwd,port));
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    /**
     * ?????????????????????
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        // ??????????????????????????????????????????????????????
        poolConfig.setMaxWaitMillis(10000);
        // ------????????????????????????????????????-------------
        return poolConfig;
    }

    /**
     * jedis????????????
     */
    private RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, int db,String hostName,String passWord,int port) {
        // ?????????jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // ??????redis????????????host??????ip??????
        redisStandaloneConfiguration.setHostName(hostName);
        // ??????????????????????????????
        redisStandaloneConfiguration.setDatabase(db);
        // ????????????
        redisStandaloneConfiguration.setPassword(RedisPassword.of(passWord));
        // ??????redis?????????????????????
        redisStandaloneConfiguration.setPort(port);

        // ?????????????????????????????????
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        // ??????jedisPoolConifig????????????????????????????????????)
        jpcb.poolConfig(jedisPoolConfig);
        // ????????????????????????jedis???????????????
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        // ???????????? + ??????????????? = jedis????????????
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        //JedisConnectionFactory????????????AfterProperties?????????
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * ???????????????
     */
    private void setSerializer(StringRedisTemplate template) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

}
