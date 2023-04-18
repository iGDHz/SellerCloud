package com.hz.sellcloud.service.impl;


import com.hz.sellcloud.domain.vo.chart.ChartDataVo;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.mapper.ChartDataMapper;
import com.hz.sellcloud.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ChartDataServiceImpl implements ChartDataService {

    @Autowired
    ChartDataMapper chartDataMapper;


    @Override
    public List<ChartDataVo> getChartData(List<String> categorys, int chartType, List<String> products, int resultType, int scope, List<Integer> supermarkets) {
        List<ChartDataVo> chartData = chartDataMapper.getChartData(categorys, chartType, products, resultType, scope,supermarkets);
        return chartData;
    }

    @Override
    public BigDecimal getChartSum(List<String> categorys, int chartType, List<String> products, int resultType, int scope, List<Integer> supermarkets) {
        return chartDataMapper.getChartSum(categorys, chartType, products, resultType, scope,supermarkets);
    }

    @Override
    public BigDecimal getChartSales(List<String> categorys, int chartType, List<String> products, int resultType, int scope, List<Integer> supermarkets) {
        return chartDataMapper.getChartSales(categorys, chartType, products, resultType, scope,supermarkets);
    }

    @Override
    public void updateSum(List<OrderVo.productVo> result) {
        chartDataMapper.updateSum(result);
    }

    @Override
    public void updateSales(List<OrderVo.productVo> result) {
        chartDataMapper.updateSales(result);
    }
}
