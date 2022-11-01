package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@TableName("product_sum")
@ApiModel(value = "ProductSum对象", description = "")
public class ProductSum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(type = IdType.AUTO)
    private Integer productId;

//    @ApiModelProperty("商品分类")
//    private Integer productBelonged;

    @ApiModelProperty("销售额")
    private BigDecimal productAmount;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
//    public Integer getProductBelonged() {
//        return productBelonged;
//    }
//
//    public void setProductBelonged(Integer productBelonged) {
//        this.productBelonged = productBelonged;
//    }
    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    @Override
    public String toString() {
        return "ProductSum{" +
            "productId=" + productId +
//            ", productBelonged=" + productBelonged +
            ", productAmount=" + productAmount +
        "}";
    }
}
