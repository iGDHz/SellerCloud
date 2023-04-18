package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

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


    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品分类")
    private Integer productCategory;

    @ApiModelProperty("商品所属超市")
    private Integer productSupermarket;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("销售数量")
    private BigDecimal productCount;

    @ApiModelProperty("日期（年月日）")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getProductSupermarket() {
        return productSupermarket;
    }

    public void setProductSupermarket(Integer productSupermarket) {
        this.productSupermarket = productSupermarket;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductCount() {
        return productCount;
    }

    public void setProductCount(BigDecimal productCount) {
        this.productCount = productCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductSum{" +
                "id=" + id +
                ", productId=" + productId +
                ", productCategory=" + productCategory +
                ", productSupermarket=" + productSupermarket +
                ", productName='" + productName + '\'' +
                ", productCount=" + productCount +
                ", date='" + date + '\'' +
                '}';
    }
}
