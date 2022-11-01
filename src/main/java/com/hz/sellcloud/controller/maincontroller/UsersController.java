package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.entity.Authorization;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.AuthorizationServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import com.hz.sellcloud.utils.Md5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
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
@Api(tags = "用户管理")
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    AuthorizationServiceImpl authorizationService;

    @Resource(name = "redisService")
    RedisService redisService;

    @RequestMapping(value = "/getuser",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取用户")
    public String getUser(String username){
        JSONObject res = new JSONObject();
        res.put("status",200);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        Users user = usersService.getOne(queryWrapper);
        if(user == null){
            res.put("status",404);
            res.put("msg","用户不存在");
        }
        res.put("user", JSON.toJSON(user));
        return res.toJSONString();
    }

    @RequestMapping(value = "/sign",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户注册")
    public String SignUser(@RequestParam("/username") @ApiParam("用户名(用户名长度不超过64字节)") String username,
                           @RequestParam("/password") @ApiParam("用户密码") String password){
        JSONObject res = new JSONObject();
        res.put("status",200);
        if(username.getBytes().length > 64){
            res.put("status",403);
            res.put("msg","用户名长度过长，请重新设置");
            return res.toJSONString();
        }
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        Users user = usersService.getOne(queryWrapper);
        if(user != null){
            res.put("status",404);
            res.put("msg","用户已存在，请更换用户名注册");
            return res.toJSONString();
        }
        boolean save = usersService.save(new Users(username, Md5Utils.md5(password), 0));
        if(save){
            res.put("msg","用户注册成功");
            return res.toJSONString();
        }
        res.put("status","500");
        return res.toJSONString();
    }

    @RequestMapping(value = "/authorize",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户授权")
    public String AuthorUser(@RequestParam("/token") @ApiParam("用户token") String token,
                             @RequestParam("username") @ApiParam("授权用户名") String username,
                             @RequestParam("password") @ApiParam("授权用户密码") String password,
                             @RequestParam("supermarkid") @ApiParam("超市id") Integer supermarkid,
                             @RequestParam("toauthor") @ApiParam("授权/取消授权") boolean toauth){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Object author = redisService.get(token);
        if(author == null){
            res.put("status",403);
            res.put("msg","用户未授权或者长时间未操作需重新登录");
            return JSONObject.toJSONString(res);
        }
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("user_password",Md5Utils.md5(password));
        Users user = usersService.getOne(queryWrapper);
        if(user == null){
            res.put("staus",404);
            res.put("msg","用户不存在或密码错误");
            return JSONObject.toJSONString(user);
        }
        if(toauth) {authorizationService.saveOrUpdate(new Authorization(user.getUserId(), supermarkid));
        res.put("msg","授权用户"+username+"操作"+supermarkid+"权限");}
        else {
            QueryWrapper<Authorization> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getUserId());
            wrapper.eq("supermark_id",supermarkid);
            authorizationService.remove(wrapper);
            res.put("msg","取消授权用户"+username+"操作"+supermarkid+"权限");
        }
        return JSONObject.toJSONString(res);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation("更新用户信息")
    @ResponseBody
    public String updateUsers(
            @RequestParam("token") @ApiParam("用户token") String token,
            @RequestParam("user") Users user){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Users ruser = (Users) redisService.get(token);
        //isVaild防止用户非法修改数据
        if(ruser == null || !isVaild(ruser,user)){
            res.put("status",403);
            res.put("msg","禁止越权修改用户信息");
            return res.toJSONString();
        }
        user.setUserId(ruser.getUserId());
        usersService.saveOrUpdate(user);
        res.put("user",user);
        redisService.set(token,JSONObject.toJSONString(user),60*60);
        return res.toJSONString();
    }

    public boolean isVaild(Users src,Users target){
        if((StringUtils.isEmpty(target.getUserName()) && !(target.getUserName().equals(src.getUserName())))
                || StringUtils.isEmpty(target.getUserPassword()) || StringUtils.isEmpty(target.getUserPassword())) return false;
        return true;
    }
}
