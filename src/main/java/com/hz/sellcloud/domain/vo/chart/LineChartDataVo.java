package com.hz.sellcloud.domain.vo.chart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class LineChartDataVo implements ChartDataModelVo {
    @Data
    public class LineData{
        String name;
        BigDecimal[] data;

        public LineData(){}

        public LineData(String name,BigDecimal[] data){this.name = name;this.data = data;}
    }
    List<LineData> data;
    List<String> date;

    public LineChartDataVo generate(List<ChartDataVo> list,int scope){
        this.date = generateDateList(scope);
        this.data = new ArrayList<>();
        Map<String,LineData> map = new HashMap<>();
        list.parallelStream().forEach((item) -> {
            LineData lineData = map.getOrDefault(item.getProduct_name(), new LineData(item.getProduct_name(),null));
            if(lineData.getData() == null){
                BigDecimal[] decimals = new BigDecimal[this.date.size()];
                Arrays.fill(decimals,new BigDecimal("0"));
                lineData.setData(decimals);
            }
            int index = this.date.indexOf(item.getDate());
            lineData.data[index] = item.getSales();
            map.put(item.getProduct_name(),lineData);
        });
        for (Map.Entry<String, LineData> entry : map.entrySet()) {
            this.data.add(entry.getValue());
        }
        return this;
    }
}
