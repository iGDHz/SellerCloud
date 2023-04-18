package com.hz.sellcloud.domain.vo.supermarket;

import lombok.Data;

@Data
public class SupermarketVo {
    int id;
    String name;
    String license;
    String address_areaId;
    String address_detail;
    int company_id;
}
