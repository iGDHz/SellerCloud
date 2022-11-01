package com.hz.sellcloud.controller;

import com.hz.sellcloud.service.impl.RedisServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class BaseController {
    @Resource(name = "redisService")
    protected RedisServiceImpl redisService;

    @Autowired
    protected UsersServiceImpl usersService;
    //log4j2
    protected Logger logger = LogManager.getLogger(BaseController.class);
}
