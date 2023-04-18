package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-10-31
 */
@ApiModel(value = "Products对象", description = "")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productPicture;

    @ApiModelProperty("商品分类")
    private Integer productCategory;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("商品所属超市")
    private Integer productBelonged;

    public Products() {
    }

    public Products(String productName, Integer productCategory, BigDecimal productPrice, Integer productBelonged) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productBelonged = productBelonged;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public Integer getProductBelonged() {
        return productBelonged;
    }

    public void setProductBelonged(Integer productBelonged) {
        this.productBelonged = productBelonged;
    }

    @Override
    public String toString() {
        return "Products{" +
            "productId=" + productId +
            ", productName=" + productName +
            ", productCategory=" + productCategory +
            ", productPrice=" + productPrice +
            ", productBelonged=" + productBelonged +
        "}";
    }
}
