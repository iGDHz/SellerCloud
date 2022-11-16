package com.hz.entity;


import com.hz.utils.DateFormater;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.shaded.org.apache.commons.lang.time.DateUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 * */
public class Order implements WritableComparable<Object> {

    private Integer orderId;

    private Integer productId;

    private Integer total;

    private BigDecimal originPrice;

    private BigDecimal price;

    private LocalDateTime date;

    public Order(){

    }

    public Order(Integer productId, Integer total, BigDecimal originPrice, BigDecimal price,LocalDateTime date) {
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

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(orderId);
        output.writeInt(productId);
        output.writeInt(total);
        output.writeChars(originPrice.toString());
        output.writeChars(DateFormater.format(date));
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this.orderId = input.readInt();
        this.productId = input.readInt();
        this.total = input.readInt();
        this.price = new BigDecimal(input.readLine());
    }

    public void set(int productid, BigDecimal price, int total, LocalDateTime date) {
        this.productId = productid;
        this.price = price;
        this.total = total;
        this.date = date;
    }
}
