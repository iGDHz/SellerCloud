package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.entity.Orders;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.impl.OrdersServiceImpl;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Tag;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */

@Controller
@Api(tags = "订单管理")
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    OrdersServiceImpl ordersService;

    @Autowired
    RedisManagement redisManagement;

    public boolean isVaild(String token){
        Users user = redisService.getUser(token);
        if (user == null) return false;
        return true;
    }

    @ApiOperation("提交订单")
    @ResponseBody
    @RequestMapping(value = "/submitorders",method = RequestMethod.POST)
    public String SubmitOrder(@RequestParam("token") String token,
                              @RequestParam("id") @ApiParam("商品id") Integer productid,
                              @RequestParam("count") @ApiParam("商品总数") Integer productcount,
                              @RequestParam("oprice") @ApiParam("商品原价") BigDecimal oprice,
                              @RequestParam("price") @ApiParam("实际支付价格/优惠价") BigDecimal price,
                              @RequestParam("time") @ApiParam("订单支付时间") Date date){
        JSONObject res = new JSONObject();
        res.put("status",200);
        if(!isVaild(token)){
            res.put("status",403);
            res.put("msg","请先登录系统");
        }
        RedisServiceImpl curRedis = redisManagement.getCurRedis();
        Orders order = new Orders(productid, productcount, oprice, price, date);
        ordersService.save(order);
        curRedis.lPush("orders",JSON.toJSONString(order));//将订单存入redis缓存后汇总
        return  res.toJSONString();
    }


}
