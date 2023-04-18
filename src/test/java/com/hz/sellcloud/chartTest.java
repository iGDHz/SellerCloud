package com.hz.sellcloud;

import com.hz.sellcloud.domain.vo.order.OrderVo;
import com.hz.sellcloud.domain.vo.supermarket.SupermarketSimpleVo;
import com.hz.sellcloud.service.ChartDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class chartTest {

    @Autowired
    ChartDataService chartDataService;

    @Test
    public void searchChartDataService(){
        List<String> categorys = new ArrayList<>();
        categorys.add("1");
        categorys.add("2");
        List<String> products = new ArrayList<>();
        products.add("test");
    }

    @Test
    public void insertTest(){
        OrderVo.productVo productVo = new OrderVo.productVo();
        productVo.setProduct_id(2);
        productVo.setCategory_id(1);
        SupermarketSimpleVo supermarketSimpleVo = new SupermarketSimpleVo();
        supermarketSimpleVo.setSid(5);
        productVo.setSupermarket(supermarketSimpleVo);
        productVo.setName("商品1");
        productVo.setPrice(new BigDecimal("12.32"));
        productVo.setQuantity(2);
        List<OrderVo.productVo> list = new ArrayList<>();
        list.add(productVo);
        chartDataService.updateSum(list);
        chartDataService.updateSales(list);
    }
}
