package com.hz.sellcloud.domain.request.products;

import lombok.Data;

import java.util.List;

@Data
public class ProductListNameRequest {
    String token;
    List<String> categorys;
    List<Integer> supermarkets;
}
