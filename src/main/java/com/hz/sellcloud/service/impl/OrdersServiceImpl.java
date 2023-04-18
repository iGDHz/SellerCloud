package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.entity.OrderSum;
import com.hz.sellcloud.entity.Orders;
import com.hz.sellcloud.mapper.OrdersMapper;
import com.hz.sellcloud.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<OrderVo> searchByConditions(String order_id, BigDecimal low_price, BigDecimal high_price, Date fast_date, Date letast_date, int page, int pagesize, List<Integer> sid) {
        List<OrderSum> orderSumList = ordersMapper.listByCondition(order_id, low_price, high_price, fast_date, letast_date, page, pagesize, sid);
        List<OrderVo> res = new ArrayList<>();
        for (OrderSum orderSum : orderSumList) {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrder_id(orderSum.getOrderId());
            orderVo.setSid(orderSum.getSupermarketId());
            orderVo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderSum.getCreateTime()));
            orderVo.setPrice(orderSum.getOrderPrice());
            List<OrderVo.productVo> productVos = ordersMapper.listById(orderSum.getOrderId());
            orderVo.setSname(productVos.get(0).getSupermarket().getSname());
            orderVo.setProducts(productVos);
            res.add(orderVo);
        }
        return res;
    }

    /*
        @param: order_id 订单id
        @return： 删除结果
     */
    @Override
    public boolean deleteByOrderId(String order_id) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",order_id);
        return remove(wrapper);
    }


}
