package com.hz.sellcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;

@Configuration
public class EnvConfig {
    @Autowired
    private Environment environment;

    /*
        @param : key key值
        @return : 配置文件中对应参数值
     */
    public int getIntProperty(String key){
        return environment.getProperty(key,Integer.class);
    }

    /*
        @param : key key值
        @return : String 配置文件中对应key的值
     */
    public String getStrProperty(String key){
        return environment.getProperty(key,String.class);
    }

    /*
        @param : key key值
        @return : BigDecimal 配置文件中的小数
     */
    public BigDecimal getAccuracyProperty(String key){
        return  new BigDecimal(environment.getProperty(key,String.class));
    }
}
