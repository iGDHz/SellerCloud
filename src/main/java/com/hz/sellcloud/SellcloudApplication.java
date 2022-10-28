package com.hz.sellcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@MapperScan("com.hz.sellcloud.mapper")
@EnableSwagger2
@SpringBootApplication
public class SellcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellcloudApplication.class, args);
    }

}
