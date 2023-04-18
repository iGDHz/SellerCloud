package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("product_sales")
@ApiModel(value = "ProductSales对象", description = "")
public class ProductSales implements Serializable {

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

    @ApiModelProperty("销售金额")
    private BigDecimal productSales;

    @ApiModelProperty("日期（年月日）")
    private String date;


    @Override
    public String toString() {
        return "ProductSales{" +
                "id=" + id +
                ", productId=" + productId +
                ", productCategory=" + productCategory +
                ", productSupermarket=" + productSupermarket +
                ", productName='" + productName + '\'' +
                ", productSales=" + productSales +
                ", date='" + date + '\'' +
                '}';
    }

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

    public BigDecimal getProductSales() {
        return productSales;
    }

    public void setProductSales(BigDecimal productSales) {
        this.productSales = productSales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
