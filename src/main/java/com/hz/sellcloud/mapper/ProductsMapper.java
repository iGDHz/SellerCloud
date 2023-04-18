package com.hz.sellcloud.mapper;

import com.hz.sellcloud.domain.request.products.ProductListRequest;
import com.hz.sellcloud.domain.vo.product.ProductVo;
import com.hz.sellcloud.entity.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hz
 * @since 2022-10-31
 */
public interface ProductsMapper extends BaseMapper<Products> {
    List<ProductVo> selectByConditions(ProductListRequest.Conditions conditions);

    long countByCondition(ProductListRequest.Conditions conditions);

    List<ProductVo> searchBySid(@Param("sid") int sid);
}
