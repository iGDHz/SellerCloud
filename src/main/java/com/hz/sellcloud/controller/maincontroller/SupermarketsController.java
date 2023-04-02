package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.entity.Address;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.impl.AddressServiceImpl;
import com.hz.sellcloud.service.impl.SupermarketsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Controller
@Api("超市信息管理")
@RequestMapping("/supermarkets")
public class SupermarketsController extends BaseController {

    @Autowired
    SupermarketsServiceImpl supermarketsService;

    @Autowired
    AddressServiceImpl addressService;
    
    @RequestMapping(value = "/sign",method = RequestMethod.POST)
    @ApiOperation("商店信息注册")
    @ResponseBody
    public String SignCompany(@RequestParam("sname") @ApiParam("商店名称") String sname,
                              @RequestParam("addressid") @ApiParam("地址信息") String addressid ,
                              @RequestParam("companyid") @ApiParam("所属超市id") Integer cid,
                              @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
//        res.put("status",200);
//        Users user = redisService.getUser(token);
//        if(user == null){
//            res.put("status",403);
//            res.put("msg","请先登录");
//            return res.toJSONString();
//        }
//        Address address = addressService.getAddress(addressid);
//        if(address == null){
//            res.put("status",403);
//            res.put("msg","地址信息异常");
//        }
//        Supermarkets supermarket = supermarketsService.getOne(new QueryWrapper<Supermarkets>()
//                .eq("supermark_name",sname));
//        if(StringUtils.isEmpty(sname) || supermarket != null){
//            res.put("status",403);
//            res.put("msg","超市已经由其他用户注册或超市名为空");
//            return res.toJSONString();
//        }
//        supermarket = new Supermarkets();
//        supermarket.setSupermarkName(sname);
//        supermarket.setSupermarkRegionid(addressid);
//        supermarket.setsupermarkBelonged(cid);
//        supermarketsService.saveOrUpdate(supermarket);
//        res.put("msg","注册成功，请耐心等待审核通过");
        return res.toJSONString();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation("超市信息修改")
    @ResponseBody
    public String UpdateCompany(@RequestParam("sid") @ApiParam("商店id") Integer sid,
                                @RequestParam("sname") @ApiParam("商店名称") String sname,
                                @RequestParam("addressid") @ApiParam("地址信息") String addressid ,
                                @RequestParam("companyid") @ApiParam("所属超市id") Integer cid,
                                @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
//        res.put("status",200);
//        Users user = redisService.getUser(token);
//        if(user == null){
//            res.put("status",403);
//            res.put("msg","请先登录");
//            return res.toJSONString();
//        }
//        Address address = addressService.getAddress(addressid);
//        if(address == null){
//            res.put("status",403);
//            res.put("msg","地址信息异常");
//        }
//        Supermarkets supermarket = supermarketsService.getOne(new QueryWrapper<Supermarkets>()
//                .eq("supermark_name",sname).ne("supermark_id",sid));
//        if(StringUtils.isEmpty(sname) || supermarket != null){
//            res.put("status",403);
//            res.put("msg","超市已经由其他用户注册或超市名为空");
//            return res.toJSONString();
//        }
//        supermarket.setSupermarkId(sid);
//        supermarket.setSupermarkName(sname);
//        supermarket.setSupermarkRegionid(addressid);
//        supermarket.setsupermarkBelonged(cid);
//        supermarketsService.saveOrUpdate(supermarket);
//        res.put("msg","注册成功，请耐心等待审核通过");
        return res.toJSONString();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation("超市信息删除")
    @ResponseBody
    public String DeleteCompany(@RequestParam("cname") @ApiParam("超市id") int sid,
                                @RequestParam("cid") @ApiParam("公司id") int cid,
                                @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Users user = redisService.getUser(token);
        if(user == null){
            res.put("status",403);
            res.put("msg","请先登录");
            return res.toJSONString();
        }
        Supermarkets supermarket = supermarketsService.getOne(new QueryWrapper<Supermarkets>()
                .eq("supermark_id",sid).eq("supermark_belonged",cid));
        if(supermarket == null){
            res.put("status",403);
            res.put("status","超市不存在");
            return res.toJSONString();
        }
        supermarketsService.removeById(supermarket);
        res.put("msg","修改成功，请耐心等待审核通过");
        return res.toJSONString();
    }
}
