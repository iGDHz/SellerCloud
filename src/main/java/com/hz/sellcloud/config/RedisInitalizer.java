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
        logger.info("###### START 初始化 Redis 连接池 START ######");
        RedisServiceImpl[] rediss = new RedisServiceImpl[maxnum];
        for (int i = 0; i < maxnum; i++) {
            String pre = "redisManagement.redis"+(i+1)+".";
            StringRedisTemplate template = redisTemplateObject(Integer.parseInt(environment.getProperty(pre+"database")),environment.getProperty(pre+"host"),environment.getProperty(pre+"password"),
                    environment.getProperty(pre+"port",Integer.class));
            rediss[i] = new RedisServiceImpl(template);
        }
        logger.info("###### END 初始化 Redis 连接池 END ######");
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
     * 连接池配置信息
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        // 当池内没有可用的连接时，最大等待时间
        poolConfig.setMaxWaitMillis(10000);
        // ------其他属性根据需要自行添加-------------
        return poolConfig;
    }

    /**
     * jedis连接工厂
     */
    private RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, int db,String hostName,String passWord,int port) {
        // 单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // 设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(hostName);
        // 设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(db);
        // 设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(passWord));
        // 设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(port);

        // 获得默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        // 指定jedisPoolConifig来修改默认的连接池构造器)
        jpcb.poolConfig(jedisPoolConfig);
        // 通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        // 单机配置 + 客户端配置 = jedis连接工厂
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        //JedisConnectionFactory需要调用AfterProperties初始化
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 设置序列化
     */
    private void setSerializer(StringRedisTemplate template) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());//设置Json序列化

    }

}
