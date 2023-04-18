package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hz.sellcloud.entity.ProductSales;
import com.hz.sellcloud.entity.ProductSum;
import com.hz.sellcloud.mapper.ProductSalesMapper;
import com.hz.sellcloud.mapper.ProductSumMapper;
import com.hz.sellcloud.service.IProductSalesService;
import com.hz.sellcloud.service.IProductSumService;
import org.springframework.stereotype.Service;

@Service
public class ProductSalesServiceImpl extends ServiceImpl<ProductSalesMapper, ProductSales> implements IProductSalesService {

}
