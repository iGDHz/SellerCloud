package com.hz.sellcloud.domain.vo.chart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class BarChartDataVo implements ChartDataModelVo{
    @Data
    class bardata{
        String category;
        List<productdata> data;

        public bardata() {
        }

        public bardata(String category, List<productdata> data) {
            this.category = category;
            this.data = data;
        }
    }

    @Data
    class productdata{
        String name;
        BigDecimal[] data;
    }
    List<bardata> data;
    List<String> date;
    @Override
    public ChartDataModelVo generate(List<ChartDataVo> list, int scope){
        Map<String,bardata> SupToData = new HashMap<>();
        Map<String,productdata> NameToData = new HashMap<>();
        this.date = generateDateList(scope);
        this.data = new ArrayList<>();
        for (ChartDataVo vo : list) {
            String[] str = StrGenerate(vo.getProduct_name());
            String sup = str[0],category = str[1],product = str[2];
            if(NameToData.containsKey(vo.getProduct_name())){
                // 叶子节点映射表
                productdata productdata = NameToData.get(vo.getProduct_name());
                int index = date.indexOf(vo.getDate());
                productdata.data[index] = vo.getSales();
            }else if(SupToData.containsKey(sup)){
                // root节点映射表
                bardata bardata = SupToData.get(sup);
                // ----------------------------初始化叶子节点
                productdata productdata = new productdata();
                BigDecimal[] decimals = new BigDecimal[this.date.size()];
                Arrays.fill(decimals,new BigDecimal("0"));
                productdata.setData(decimals);
                productdata.setName(vo.getProduct_name());
                int index = date.indexOf(vo.getDate());
                productdata.data[index] = vo.getSales();
                // ----------------------------初始化叶子节点
                NameToData.put(vo.product_name, productdata);
                bardata.setCategory(sup);
                bardata.getData().add(productdata);
                SupToData.put(sup,bardata);
            }else{
                bardata bardata = new bardata();
                this.data.add(bardata);
                List<productdata> plist = new ArrayList<>();
                bardata.setData(plist);
                bardata.setCategory(sup);

                productdata productdata = new productdata();
                BigDecimal[] decimals = new BigDecimal[this.date.size()];
                Arrays.fill(decimals,new BigDecimal("0"));
                productdata.setData(decimals);
                productdata.setName(vo.getProduct_name());
                int index = date.indexOf(vo.getDate());
                productdata.data[index] = vo.getSales();
                plist.add(productdata);

                SupToData.put(sup,bardata);
                NameToData.put(vo.getProduct_name(),productdata);
            }
        }
        return this;
    }

    public String[] StrGenerate(String str){
        String regex = "[/]";
        return str.split(regex);
    }
}
