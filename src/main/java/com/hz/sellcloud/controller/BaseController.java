package com.hz.sellcloud.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class BaseController {
    @Autowired
    protected RedisService redisService;
    //log4j2
    protected Logger logger = LogManager.getLogger(BaseController.class);

    /*
            @params: token用户token
            @return 返回用户对象
         */
    public Users TokenToUsers(String token){
        String jsonstr = (String) redisService.get(token);
        if(StringUtils.isEmpty(jsonstr)){
            return null;
        }
        return JSONUtil.toBean(jsonstr, Users.class);
    }
}
