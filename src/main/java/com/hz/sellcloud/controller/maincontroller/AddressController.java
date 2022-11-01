package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.entity.Address;
import com.hz.sellcloud.service.impl.AddressServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(tags = "地址信息")
@RequestMapping("/address")
public class AddressController extends BaseController {
    @Autowired
    AddressServiceImpl addressService;

    @RequestMapping(value = "/getaddress" , method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取对应区号地址（为0时返回所有省对应区号）")
    public String getAddress(@RequestParam("addressid") @ApiParam("区号") String addressId){
        JSONObject res = new JSONObject();
        List<Address> addressList;
        logger.info("获取AddressId为"+addressId+"的地址信息");
        if(addressId.equals("0")){
            addressList = addressService.SearchNameById(addressId);
        }else {
            addressList = addressService.SearchNameById(addressId);
        }
        res.put("status",1);
        res.put("address", JSON.toJSON(addressList));
        return res.toJSONString();
    }
}
