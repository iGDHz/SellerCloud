package com.hz.sellcloud.domain.request.chart;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataRequest {
    String token; //token
    List<String> categorys; //分类id
    int chartType; // 0:折线图 1：柱状图 2：饼图
    List<String> products; //商品名称
    int resultType; //0: 销售金额 1: 销售额
    int scope; //取值范围 0: 近7天， 1: 近一个月 2： 近3个月 3：近6个月 4： 近一年 5：近3年
    List<Integer> supermarkets;
}
