package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.entity.Address;
import com.hz.sellcloud.mapper.AddressMapper;
import com.hz.sellcloud.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    public List<Address> SearchNameById(String addressid){
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        if(addressid == null){
            wrapper = new QueryWrapper<>();
            wrapper.apply("address_areaId = address_regionId");
            List<Address> list = list(wrapper);
            return list;
        }
        wrapper.eq("address_regionId",addressid);
        wrapper.apply("address_areaId <> address_regionId");
        List<Address> list = list(wrapper);
        return list;
    }

}
