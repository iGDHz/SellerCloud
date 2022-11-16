package com.hz.entity;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;

public class ProductSum implements WritableComparable<Object> {
    private Integer productId;
    private Integer belonged;
    private BigDecimal productAmount;


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(productId);
        output.writeInt(belonged);
        output.writeChars(productAmount.toString());
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this.productId = input.readInt();
        this.belonged = input.readInt();
        this.productAmount = new BigDecimal(input.readLine());
    }
}
