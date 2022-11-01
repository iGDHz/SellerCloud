package com.hz.sellcloud.service;

import com.hz.sellcloud.entity.Orders;

import java.util.List;

public interface IMapperReduceService {

    void ReduceOrder(List<Orders> ordersList);
}
