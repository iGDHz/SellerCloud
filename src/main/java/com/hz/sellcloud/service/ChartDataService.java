package com.hz.sellcloud.service;

import com.hz.sellcloud.domain.vo.chart.ChartDataVo;
import com.hz.sellcloud.domain.vo.order.OrderVo;

import java.math.BigDecimal;
import java.util.List;

public interface ChartDataService {
    List<ChartDataVo> getChartData(List<String> categorys,
                                   int chartType,
                                   List<String> products,
                                   int resultType,
                                   int scope,
                                   List<Integer> supermarkets);

    BigDecimal getChartSum(List<String> categorys, int chartType, List<String> products, int resultType, int scope, List<Integer> supermarkets);

    BigDecimal getChartSales(List<String> categorys, int chartType, List<String> products, int resultType, int scope, List<Integer> supermarkets);

    void updateSum(List<OrderVo.productVo> result);

    void updateSales(List<OrderVo.productVo> result);
}
