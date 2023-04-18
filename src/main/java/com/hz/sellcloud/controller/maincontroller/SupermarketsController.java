package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.domain.request.products.ProductListRequest;
import com.hz.sellcloud.domain.request.supermarket.SupermarketUpdateLiceseRequest;
import com.hz.sellcloud.domain.request.user.UserSignRequest;
import com.hz.sellcloud.domain.response.CommonResponse;
import com.hz.sellcloud.domain.vo.supermarket.SupermarketVo;
import com.hz.sellcloud.entity.Address;
import com.hz.sellcloud.entity.Companies;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.*;
import com.hz.sellcloud.service.impl.AddressServiceImpl;
import com.hz.sellcloud.service.impl.CompaniesServiceImpl;
import com.hz.sellcloud.service.impl.SupermarketsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@Api(tags = "超市信息管理")
@RequestMapping("/supermarket")
public class SupermarketsController extends BaseController {

    @Autowired
    ISupermarketsService supermarketsService;


    @Autowired
    ICompaniesService companiesService;

    @Autowired
    IAddressService addressService;

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/updateLicense",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新用户超市信息")
    public CommonResponse UpdateUser(HttpServletRequest httprequest){
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) httprequest;
        String token = request.getParameter("token");
        MultipartFile license = request.getFile("license");
        //1.验证用户token
        Users user = TokenToUsers(token);
        if(token == null){
            return new CommonResponse().error(403,"用户token异常");
        }
        //2.修改用户信息
        Supermarkets supermarket = supermarketsService.getByUserId(user.getUserId());
        String supermarkLicense = supermarket.getSupermarkLicense();
        fileService.removeFile(supermarkLicense);
        supermarket.setSupermarkLicense(fileService.uploadFile(license));
        //3.数据库更新数据
        supermarketsService.saveOrUpdate(supermarket);
        //4.redis更新用户信息
        redisService.set(token, JSON.toJSONString(user),60*60*24);
        return new CommonResponse().sucess();
    }

    @ResponseBody
    @ApiOperation("获取超市信息")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResponse searchProducts(@RequestParam("token") String token){
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse<>().error(403,"token失效");
        }
        List<Supermarkets> list;
        if(user.getUserRole().equals("Admin")){
            Companies companies = companiesService.getByUserId(user.getUserId());
            list = supermarketsService.searchByCompanyId(companies.getCompanyId());
        }else{
            list = supermarketsService.searchByUserId(user.getUserId());
        }
        List<SupermarketVo> voList = list.parallelStream().map(this::MapToVo).collect(Collectors.toList());
        return new CommonResponse(voList).sucess();
    }

    public SupermarketVo MapToVo(Supermarkets supermarkets){
        SupermarketVo vo = new SupermarketVo();
        vo.setId(supermarkets.getSupermarkId());
        vo.setName(supermarkets.getSupermarkName());
        String license = "http://localhost:8082/file/down" + supermarkets.getSupermarkLicense();
        license = license.replace("\\", "/");
        vo.setLicense(license);
        vo.setAddress_areaId(supermarkets.getSupermarkRegionid());
        vo.setAddress_detail(supermarkets.getSupermarkDetail());
        vo.setCompany_id(supermarkets.getSupermarkBelonged());
        return vo;
    }
}
