package com.hz.sellcloud.mapper;

import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.entity.OrderSum;
import com.hz.sellcloud.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    List<OrderSum> listByCondition(@Param("order_id") String order_id,
                                   @Param("low_price") BigDecimal low_price,
                                   @Param("high_price") BigDecimal high_price,
                                   @Param("fast_date") Date fast_date,
                                   @Param("letast_date") Date letast_date,
                                   @Param("page") int page,
                                   @Param("pagesize") int pagesize,
                                   @Param("list") List<Integer> sid);

    List<OrderVo.productVo> listById(String orderId);
}
