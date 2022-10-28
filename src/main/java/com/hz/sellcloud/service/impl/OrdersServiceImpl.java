package com.hz.sellcloud.service.impl;

import com.hz.sellcloud.entity.Orders;
import com.hz.sellcloud.mapper.OrdersMapper;
import com.hz.sellcloud.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
