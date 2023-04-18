package com.hz.sellcloud.domain.vo.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductListVo {
    long total;
    List<ProductVo> products;
}
