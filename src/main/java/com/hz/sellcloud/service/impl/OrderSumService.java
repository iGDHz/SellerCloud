package com.hz.sellcloud.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hz.sellcloud.entity.OrderSum;
import com.hz.sellcloud.mapper.OrderSumMapper;
import com.hz.sellcloud.service.IOrderSumService;
import org.springframework.stereotype.Service;

@Service
public class OrderSumService extends ServiceImpl<OrderSumMapper, OrderSum> implements IOrderSumService {
    @Override
    public boolean deleteByOrderId(String order_id) {
        QueryWrapper<OrderSum> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",order_id);
        return remove(queryWrapper);
    }
}
