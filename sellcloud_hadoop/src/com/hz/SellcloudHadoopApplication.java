package com.hz;

import com.hz.format.SellCloudInputFormat;
import com.hz.mapper.SellcloudMapper;
import com.hz.reducer.SellcloudReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class SellcloudHadoopApplication {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherargs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherargs.length < 2){
            System.err.println("Usage: wordcount <in> {<int> ...} <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "SellCloudMapReduce");
        job.setInputFormatClass(SellCloudInputFormat.class);
        job.setJarByClass(SellcloudHadoopApplication.class);
        job.setMapperClass(SellcloudMapper.class);
        job.setCombinerClass(SellcloudReducer.class);
        job.setReducerClass(SellcloudReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        for (int i = 0; i < otherargs.length - 1; i++) {
            FileInputFormat.addInputPath(job,new Path(otherargs[i]));
        }
        FileOutputFormat.setOutputPath(job,new Path(otherargs[otherargs.length-1]));
        if(job.waitForCompletion(true)){
            for (int i = 0; i < otherargs.length-1; i++) {
                Path path = new Path(otherargs[i]);
                FileSystem system = FileSystem.get(conf);
                system.deleteOnExit(path);
            }
            System.exit(0);
        }
        System.exit(1);
    }
}
