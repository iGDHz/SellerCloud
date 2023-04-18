package com.hz.sellcloud.domain.request.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductListRequest {
    String token;
    @Data
    public class Conditions{
        int userId;
        String name; //按照名称模糊查询
        int category_id; //按照分类查询
        BigDecimal low_price; //最低价格
        BigDecimal high_price; //最高价格
        int page = 1; //当前页码
        int limit = 10; //每页大小
    }

    Conditions conditions = new Conditions();
}
