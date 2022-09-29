package noreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

/*
* 没有Reduce
*
* */
public class NoReduce {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/wc.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/wc";
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(NoReduce.class);

        job.setMapperClass(SplitedMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
        System.exit(job.waitForCompletion(true)? 0: 1);

    }

    public static class SplitedMapper extends Mapper<Object,Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1);  //value
        private final Text word = new Text();  //key
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] token = value.toString().split(" ");
            for(String str : token){
                word.set(str);
                context.write(word,one);
            }
        }
    }
//    a       1
//    aa      1
//    aa      1
//    abcdehello      1
//    bb      1
//    bcdhel  1
//    cc      1
//    cc      1
//    dd      1
//    flink   1
//    hadoop  1
//    hadoop  1
//    hadoop  1
//    hello   1
//    hello   1
//    spark   1
}