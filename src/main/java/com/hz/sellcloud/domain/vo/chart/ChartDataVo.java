package com.hz.sellcloud.domain.vo.chart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChartDataVo {
    String product_name;
    BigDecimal sales;
    String date;

    public ChartDataVo(String product_name,String date,BigDecimal sales){
        this.product_name = product_name;
        this.sales = sales;
        this.date = date;
    }

}
