package com.hz.mapper;

import com.hz.entity.Order;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SellcloudMapper extends Mapper<Text, Order,Text,Text> {
    @Override
    protected void map(Text key, Order value, Context context) throws IOException, InterruptedException {
        context.write(new Text(String.valueOf(key)),new Text(value.getPrice().toString()));
    }
}
