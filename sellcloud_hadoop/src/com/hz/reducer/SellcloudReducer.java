package com.hz.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

public class SellcloudReducer extends Reducer<Text,Text, Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        BigDecimal res = new BigDecimal("0");
        Iterator<Text> iterator = values.iterator();
        while (iterator.hasNext()){
            Text text = iterator.next();
            String value = new String(text.getBytes());
            BigDecimal price = new BigDecimal(value);
            res = res.add(price);
        }
        context.write(key,new Text(res.toString()));
    }
}
