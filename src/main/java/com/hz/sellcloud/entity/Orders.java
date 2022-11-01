package com.hz.sellcloud.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Orders对象", description = "")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品数量")
    private Integer total;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

    @ApiModelProperty("实付价格")
    private BigDecimal price;

    @ApiModelProperty("付款时间")
    private Date date;

    public Orders(){

    }

    public Orders(Integer productId, Integer total, BigDecimal originPrice, BigDecimal price,Date date) {
        this.productId = productId;
        this.total = total;
        this.originPrice = originPrice;
        this.price = price;
        this.date = date;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "orderId=" + orderId +
            ", productId=" + productId +
            ", total=" + total +
            ", originPrice=" + originPrice +
            ", price=" + price +
        "}";
    }
}
