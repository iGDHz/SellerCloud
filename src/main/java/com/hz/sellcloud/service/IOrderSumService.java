package com.hz.sellcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.sellcloud.entity.OrderSum;
import com.hz.sellcloud.entity.Orders;

public interface IOrderSumService extends IService<OrderSum> {
    boolean deleteByOrderId(String order_id);
}
