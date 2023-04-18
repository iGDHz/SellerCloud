package com.hz.sellcloud.service;

import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
public interface IOrdersService extends IService<Orders> {


    List<OrderVo> searchByConditions(String order_id, BigDecimal low_price, BigDecimal high_price, Date fast_date, Date letast_date, int page, int pagesize, List<Integer> sid);

    boolean deleteByOrderId(String order_id);
}
