package com.hz.format;

import com.hz.entity.Order;
import com.hz.utils.DateFormater;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SellCloudInputFormat extends FileInputFormat<Text, Order> {


    @Override
    protected boolean isSplitable(JobContext context, Path filename) {//这是InputFormat的isSplitable方法
        //isSplitable方法就是是否要切分文件，这个方法显示如果是压缩文件就不切分，非压缩文件就切分。
        //        如果不允许分割，则isSplitable==false，则将第一个block、文件目录、开始位置为，长度为整个文件的长度封装到一个InputSplit，加入splits中
        //        如果文件长度不为且支持分割，则isSplitable==true,获取block大小，默认是MB
        return false;    //整个文件封装到一个InputSplit
        //要么就是return true;        //切分MB大小的一块一块，再封装到InputSplit
    }




    @Override
    public RecordReader<Text, Order> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException{

        return new OrderRecordReader();
    }



    public class OrderRecordReader extends RecordReader<Text, Order>{
        public LineReader in;//行读取器
        public Text line;//每行数据类型
        public Text lineKey;//自定义key类型，即k
        public Order lineValue;//自定义value类型，即v


        @Override
        public void close() throws IOException {
            if(in !=null){
                in.close();
            }
        }
        @Override
        public Text getCurrentKey() throws IOException, InterruptedException {//获取当前的key,即CurrentKey
            return lineKey;//返回类型是Text,即Text lineKey
        }
        @Override
        public Order getCurrentValue() throws IOException,InterruptedException {//获取当前的Value，即CurrentValue
            return lineValue;//返回类型是WeiBo,即WeiBo lineValue
        }
        @Override
        public float getProgress() throws IOException, InterruptedException {//获取进程，即Progress
            return 0f;//返回类型是float,即float
        }



        @Override
        public void initialize(InputSplit input, TaskAttemptContext context)throws IOException, InterruptedException{//初始化，都是模板
            FileSplit split=(FileSplit)input;//获取split
            Configuration job=context.getConfiguration();
            Path file=split.getPath();//得到文件路径
            FileSystem fs=file.getFileSystem(job);

            FSDataInputStream filein=fs.open(file);//打开文件
            in=new LineReader(filein,job); //输入流in
            line=new Text();//每行数据类型
            lineKey=new Text();//自定义key类型，即k。//新建一个Text实例作为自定义格式输入的key
            lineValue = new Order();//自定义value类型，即v。//新建一个TVPlayData实例作为自定义格式输入的value
        }


        //此方法读取每行数据，完成自定义的key和value
        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException{
            int linesize=in.readLine(line);
            if(linesize==0)  return false;

            //通过分隔符'\t'，将每行的数据解析成数组 pieces
            String[] pieces = line.toString().split("\t");
            if(pieces.length != 3){
                throw new IOException(String.format("Invalid record received whice length is %d",pieces.length));
            }

            int productid,total;
            BigDecimal price = null;
            LocalDateTime date = null;
            try{
                productid = Integer.parseInt(pieces[2].trim());//商品id
                total = Integer.parseInt(pieces[3]);
                //pieces[4]为原价（不考虑）
                price = new BigDecimal(pieces[5]);
                date = DateFormater.parse(pieces[6]);
            }catch(NumberFormatException nfe)
            {
                throw new IOException("Error parsing floating poing value in record");
            }


            //自定义key和value值
            lineKey.set(pieces[0]); //完成自定义key数据
            lineValue.set(productid,price,total,date);//完成自定义value数据

            return true;
        }



    }
}
