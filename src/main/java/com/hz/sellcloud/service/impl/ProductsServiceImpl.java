package com.hz.sellcloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.domain.request.products.ProductListRequest;
import com.hz.sellcloud.domain.vo.product.ProductNameVo;
import com.hz.sellcloud.domain.vo.product.ProductVo;
import com.hz.sellcloud.entity.Products;
import com.hz.sellcloud.mapper.ProductsMapper;
import com.hz.sellcloud.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hz
 * @since 2022-10-31
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    ProductsMapper productsMapper;
    @Override
    public List<ProductVo> selectByConditon(ProductListRequest.Conditions conditions) {
        return  productsMapper.selectByConditions(conditions);
    }

    /*

     */
    @Override
    public long countByCondition(ProductListRequest.Conditions conditions) {
        return productsMapper.countByCondition(conditions);
    }

    @Override
    public boolean UpdateProduct(Products product) {
        if(product.getProductId() != null){
            if(product.getProductPicture() == null){
                Products products = getById(product.getProductId());
                product.setProductPicture(products.getProductPicture());
            }
        }
        return saveOrUpdate(product);
    }

    @Override
    public List<ProductVo> searchBySid(int sid) {
        return productsMapper.searchBySid(sid);
    }

    @Override
    public List<ProductNameVo> getByConditions(List<Integer> supermarket, List<String> categorys) {
        QueryWrapper<Products> queryWrapper = new QueryWrapper<>();
        if(categorys.size() != 0)queryWrapper.in("product_category",categorys);
        if(supermarket.size() != 0)queryWrapper.in("product_belonged",supermarket);
        queryWrapper.select("distinct product_name");
        List<Products> productsList = list(queryWrapper);
        List<ProductNameVo> res = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            ProductNameVo productNameVo = new ProductNameVo();
            productNameVo.setId(i);
            productNameVo.setName(productsList.get(i).getProductName());
            res.add(productNameVo);
        }
        return res;
    }

    /*
        @params: conditions 查询条件
        @return ： 返回查询条件
     */
    public QueryWrapper WrapperConditions(ProductListRequest.Conditions conditions){
        QueryWrapper<ProductVo> queryWrapper = new QueryWrapper<>();
        String name = conditions.getName();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("product_name",name);
        }
        int category_id = conditions.getCategory_id();
        if(category_id != 0){
            queryWrapper.eq("product_category",category_id);
        }
        BigDecimal low_price = conditions.getLow_price();
        if(low_price != null){
            queryWrapper.ge("product_price",low_price);
        }
        BigDecimal high_price = conditions.getHigh_price();
        if(high_price != null){
            queryWrapper.le("product_price",high_price);
        }

        return queryWrapper;
    }
}
