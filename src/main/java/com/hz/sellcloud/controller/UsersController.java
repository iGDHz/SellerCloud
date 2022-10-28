package com.hz.sellcloud.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    @RequestMapping(value = "/getuser",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("测试")
    public Users getUser(){
        String username = "hz";
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        Users user = usersService.getOne(queryWrapper);
       return usersService.getOne(queryWrapper);
    }
}
