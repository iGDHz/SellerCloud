package com.hz.sellcloud.mapper;

import com.hz.sellcloud.domain.vo.chart.ChartDataVo;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ChartDataMapper {
    List<ChartDataVo> getChartData(@Param("categorys") List<String> categorys,
                                   @Param("chartType") int chartType,
                                   @Param("products") List<String> products,
                                   @Param("resultType") int resultType,
                                   @Param("scope") int scope,
                                   @Param("supermarkets") List<Integer> supermarkets);
    BigDecimal getChartSum(@Param("categorys") List<String> categorys,
                           @Param("chartType") int chartType,
                           @Param("products") List<String> products,
                           @Param("resultType") int resultType,
                           @Param("scope") int scope,
                           @Param("supermarkets") List<Integer> supermarkets);
    BigDecimal getChartSales(@Param("categorys") List<String> categorys,
                             @Param("chartType") int chartType,
                             @Param("products") List<String> products,
                             @Param("resultType") int resultType,
                             @Param("scope") int scope,
                             @Param("supermarkets") List<Integer> supermarkets);

    void updateSum(@Param("list") List<OrderVo.productVo> result);

    void updateSales(@Param("list") List<OrderVo.productVo> result);
}
