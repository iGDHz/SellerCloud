package com.hz.sellcloud.service;

import com.hz.sellcloud.domain.request.products.ProductListRequest;
import com.hz.sellcloud.domain.vo.product.ProductNameVo;
import com.hz.sellcloud.domain.vo.product.ProductVo;
import com.hz.sellcloud.entity.Products;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hz
 * @since 2022-10-31
 */
public interface IProductsService extends IService<Products> {

    List<ProductVo> selectByConditon(ProductListRequest.Conditions conditions);


    /*

     */
    long countByCondition(ProductListRequest.Conditions conditions);

    boolean UpdateProduct(Products product);

    List<ProductVo> searchBySid(int sid);

    List<ProductNameVo> getByConditions(List<Integer> supermarket, List<String> categorys);
}
