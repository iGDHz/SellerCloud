package com.hz.sellcloud.controller.maincontroller;


import cn.hutool.core.util.IdUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hz.sellcloud.config.RedisManagement;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.domain.request.order.OrderUpdateRequest;
import com.hz.sellcloud.domain.response.CommonResponse;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.entity.*;
import com.hz.sellcloud.service.ICompaniesService;
import com.hz.sellcloud.service.IOrderSumService;
import com.hz.sellcloud.service.IOrdersService;
import com.hz.sellcloud.service.ISupermarketsService;
import com.hz.sellcloud.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("/order")
public class OrdersController extends BaseController {

    @Autowired
    IOrdersService ordersService;

    @Autowired
    AuthorizationServiceImpl authorizationService;

    @Autowired
    ISupermarketsService supermarketsService;

    @Autowired
    ICompaniesService companiesService;

    @Autowired
    IOrderSumService orderSumService;


    @Autowired
    RedisManagement redisManagement;

    @ResponseBody
    @ApiOperation("获取订单信息")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResponse searchProducts(@RequestParam("token") String token,
                                         @RequestParam(value = "order_id",required = false) String order_id, //订单号
                                         @RequestParam(value = "low_price",required = false) BigDecimal low_price,
                                         @RequestParam(value = "high_price",required = false)BigDecimal high_price,
                                         @RequestParam(value = "fast_date",required = false) Date fast_date,
                                         @RequestParam(value = "letast_date",required = false) Date letast_date,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "limit", required = false) Integer pagesize){
        //1.验证用户token
        page = page == null?0:page-1;
        pagesize = pagesize == null?10:pagesize;
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        //2.查询旗下supermarket
        List<Supermarkets> list = new ArrayList<>();
        if(user.getUserRole().equals("Admin")){
            Companies companies = companiesService.getByUserId(user.getUserId());
            list = supermarketsService.searchByCompanyId(companies.getCompanyId());
        }else{
            list = supermarketsService.searchByUserId(user.getUserId());
        }
        List<Integer> sidlist = list.parallelStream().map(item -> item.getSupermarkId()).collect(Collectors.toList());
        //3.数据库查询
        List<OrderVo> orderVos = ordersService.searchByConditions(order_id, low_price, high_price, fast_date, letast_date, page, pagesize, sidlist);
        return new CommonResponse(orderVos).sucess();
    }

    @ResponseBody
    @ApiOperation("更新订单信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @Transactional
    public CommonResponse updateOrder(@RequestBody OrderUpdateRequest request){
        //1.验证用户token
        Users user = TokenToUsers(request.getToken());
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        //2.更新订单信息
        List<OrderVo.productVo> products = request.getOrder().getProducts();
        OrderSum orderSum = new OrderSum();
        String uuid = IdUtil.randomUUID();
        final String orderid = StringUtils.isEmpty(request.getOrder().getOrder_id())?uuid:request.getOrder().getOrder_id();
        orderSum.setOrderId(orderid);
        orderSum.setOrderPrice(request.getOrder().getPrice());
        orderSum.setCreateTime(request.getOrder().getDate());
        List<Orders> orders = products.parallelStream().map(vo -> {
            Orders order = new Orders();
            order.setOrderId(orderid);
            order.setProductId(vo.getProduct_id());
            order.setTotal(vo.getQuantity());
            order.setOriginPrice(vo.getSignle());
            order.setPrice(vo.getPrice());
            order.setCreateTime(request.getOrder().getDate());
            orderSum.setSupermarketId((int) vo.getSupermarket().getSid());
            return order;
        }).collect(Collectors.toList());
        ordersService.deleteByOrderId(request.getOrder().getOrder_id());
        ordersService.saveOrUpdateBatch(orders);
        orderSumService.deleteByOrderId(request.getOrder().getOrder_id());
        orderSumService.saveOrUpdate(orderSum);
        //TODO 向redis消息队列添加数据
        List<OrderVo.productVo> productlist = request.getOrder().getProducts();
        for (OrderVo.productVo productVo : productlist) {
            redisService.lPush("orderlist",JSON.toJSONString(productVo));
        }
        return new CommonResponse().sucess();
    }

    @ResponseBody
    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @Transactional
    public CommonResponse deleteOrder(@RequestParam("token") String token,
                                      @RequestParam("order_id") String order_id){
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        ordersService.deleteByOrderId(order_id);
        orderSumService.deleteByOrderId(order_id);
        return new CommonResponse().sucess();
    }

}
