package com.hz.sellcloud;


import com.hz.sellcloud.utils.RegexValidate;
import org.junit.jupiter.api.Test;

public class RegexValidateTest {
    @Test
    public void test(){
        System.out.println(RegexValidate.isPhone("19860205360"));
        System.out.println(RegexValidate.isEmail("1165929365@qq.com"));
        System.out.println(RegexValidate.isEmail("!@42141242@qweqw"));
    }
}
