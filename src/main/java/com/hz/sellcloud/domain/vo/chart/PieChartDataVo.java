package com.hz.sellcloud.domain.vo.chart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PieChartDataVo implements ChartDataModelVo {
    @Data
    class piedata{
        String name;
        List<piedata> children;
        BigDecimal value;

        public piedata(){}

        public piedata(String name, List<piedata> children, BigDecimal value) {
            this.name = name;
            this.children = children;
            this.value = value;
        }
    }

    List<piedata> data;

    @Override
    public ChartDataModelVo generate(List<ChartDataVo> list, int scope) {
        Map<String,Map<String,Map<String,piedata>>> SupToNode = new HashMap<>(); // 超市-Node
//        Map<String,piedata> CategoryToNode = new HashMap<>(); // 超市/分类 - Node
//        Map<String,piedata> NameToNode = new HashMap<>(); // 超市/分类/商品 - Node
        //1.初始化map
        for (ChartDataVo vo : list) {
            String[] str = StrGenerate(vo.getProduct_name());
            String sup = str[0],category = str[1],product = str[2];
            //1.按照超市查询节点
            Map<String, Map<String, piedata>> CategoryToNode = SupToNode.getOrDefault(sup, new HashMap<>());
            //2.根据分类查找节点
            Map<String, piedata> NameToNode = CategoryToNode.getOrDefault(category, new HashMap<>());
            //3.查找指定节点
            piedata node = NameToNode.getOrDefault(product, new piedata(vo.getProduct_name(), null, new BigDecimal("0")));
            node.value = node.getValue().add(vo.getSales());
            NameToNode.put(product,node);
            CategoryToNode.put(category,NameToNode);
            SupToNode.put(sup,CategoryToNode);
        }
        //2.格式化data结构
        this.data = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<String, piedata>>> Snode : SupToNode.entrySet()) {
            String supermarket = Snode.getKey();
            Map<String, Map<String, piedata>> CategoryToNode = Snode.getValue();
            List<piedata> Second = new ArrayList<>();
            piedata secondNode= new piedata(supermarket,Second , null);
            for (Map.Entry<String, Map<String, PieChartDataVo.piedata>> Cnode : CategoryToNode.entrySet()) {
                String category = Cnode.getKey();
                Map<String, PieChartDataVo.piedata> NameToNode = Cnode.getValue();
                List<piedata> Third = new ArrayList<>();
                piedata thirdnode = new piedata(category,Third , null);
                for (Map.Entry<String, piedata> Nnode : NameToNode.entrySet()) {
                    piedata value = Nnode.getValue();
                    Third.add(value);
                }
                Second.add(thirdnode);
            }
            this.data.add(secondNode);
        }
        return this;
    }

    public String[] StrGenerate(String str){
        String regex = "[/]";
        return str.split(regex);
    }
}
