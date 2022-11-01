package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.entity.Companies;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.CompaniesServiceImpl;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Api(tags = "公司管理")
@RequestMapping("/companies")
public class CompaniesController extends BaseController {

    @Autowired
    CompaniesServiceImpl companiesService;


    @RequestMapping(value = "/sign",method = RequestMethod.POST)
    @ApiOperation("公司信息注册")
    @ResponseBody
    public String SignCompany(@RequestParam("cname") @ApiParam("公司名称") String cname,
                              @RequestParam("message") @ApiParam("公司信息") String message,
                              @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Users user = redisService.getUser(token);
        if(user == null){
            res.put("status",403);
            res.put("msg","请先登录");
            return res.toJSONString();
        }
        Companies companies = new Companies();
        companies.setCompanyName(cname);
        companies.setCompanyMessage(message);
        companies.setCompanyUser(user.getUserId());
        Companies company = companiesService.getOne(new QueryWrapper<Companies>()
                .eq("company_name",cname));
        if(StringUtils.isEmpty(cname) || company != null){
            res.put("status",403);
            res.put("msg","公司已经由其他用户注册或公司名为空");
            return res.toJSONString();
        }
        companiesService.saveOrUpdate(companies);
        res.put("msg","注册成功，请耐心等待审核通过");
        return res.toJSONString();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation("公司信息修改")
    @ResponseBody
    public String UpdateCompany(
                                @RequestParam("cid") @ApiParam("公司id") Integer cid,
                                @RequestParam("cname") @ApiParam("公司名称") String cname,
                              @RequestParam("message") @ApiParam("公司信息") String message,
                              @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Users user = redisService.getUser(token);
        if(user == null){
            res.put("status",403);
            res.put("msg","请先登录");
            return res.toJSONString();
        }
        Companies companies = new Companies();
        companies.setCompanyId(cid);
        companies.setCompanyName(cname);
        companies.setCompanyMessage(message);
        companies.setCompanyUser(user.getUserId());
        Companies company = companiesService.getOne(new QueryWrapper<Companies>()
                .eq("company_name",cname).ne("company_id",cid));
        if(StringUtils.isEmpty(cname) || companies.getCompanyUser() != user.getUserId()){
            res.put("status",403);
            res.put("status","修改信息不能为空或权限不足");
            return res.toJSONString();
        }
        companiesService.saveOrUpdate(companies);
        res.put("msg","修改成功，请耐心等待审核通过");
        return res.toJSONString();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation("公司信息删除")
    @ResponseBody
    public String DeleteCompany(@RequestParam("cname") @ApiParam("公司id") int id,
                                @RequestParam("token") @ApiParam("用户token") String token){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Users user = redisService.getUser(token);
        if(user == null){
            res.put("status",403);
            res.put("msg","请先登录");
            return res.toJSONString();
        }
        Companies company = companiesService.getOne(new QueryWrapper<Companies>()
                .eq("company_id",id).eq("company_user",user.getUserId()));
        if(company == null){
            res.put("status",403);
            res.put("status","公司不存在");
            return res.toJSONString();
        }
        companiesService.removeById(company);
        res.put("msg","修改成功，请耐心等待审核通过");
        return res.toJSONString();
    }

    @RequestMapping(value = "/getlist",method = RequestMethod.POST)
    @ApiOperation("公司信息列表查询")
    @ResponseBody
    public String getCompany(@RequestParam("token") @ApiParam("用户token")String token){
        Users user = redisService.getUser(token);
        JSONObject res = new JSONObject();
        res.put("status",200);
        if(user == null){
            res.put("status",403);
            res.put("msg","请先登录");
            return res.toJSONString();
        }
        List<Companies> list = companiesService.list();
        res.put("list", JSON.toJSON(list));
        return res.toJSONString();
    }
}
