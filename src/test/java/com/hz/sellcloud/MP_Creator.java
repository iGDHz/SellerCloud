package com.hz.sellcloud;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class MP_Creator {
    //D:\Hz_Java\sellcloud\src\main\java\com\hz\sellcloud
    @Test
    public void init(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/sellercloud?userUnicode=true&characterEncoding=utf-8", "root", "qzzx91112")
                .globalConfig(builder -> {
                    builder.author("hz") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Hz_Java\\sellcloud\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.hz.sellcloud") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Hz_Java\\sellcloud\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
