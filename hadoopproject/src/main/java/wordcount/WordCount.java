package wordcount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.compress.CodecPool;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.LineReader;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

public class WordCount {
    private static final String INPUT_PATH = "hdfs://zgg:9000/in/wc.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/wc";
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
//        System.out.println("getName" +":"+outPath.getName()); //path:wc
//        System.out.println("outPath" + ":" + outPath); //outPath:hdfs://zgg:9000/out/wc
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(WordCount.class);

        job.setMapperClass(SplitedMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

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

    public static class CountReducer extends Reducer<Text, IntWritable, Text,IntWritable>{
        private final IntWritable rlt = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable val : values){
                sum += val.get();
            }
            rlt.set(sum);
            context.write(key, rlt);
        }
    }
}
