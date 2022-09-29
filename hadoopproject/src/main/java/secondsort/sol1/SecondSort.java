package secondsort.sol1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

public class SecondSort {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/secondsort.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/secondsort";

    public static void main(String[] Args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH),conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);

        job.setJarByClass(SecondSort.class);

        job.setMapperClass(SortMapper.class);
        job.setMapOutputKeyClass(NewK2.class);
        job.setMapOutputValueClass(LongWritable.class);
//也可以添加自定义分区器和分组器
        job.setReducerClass(SortReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true)?0:1);

//        输出：【有去重，key和value有重复】
//        1       1
//        2       1
//        2       2
//        3       1
//        3       2
//        3       3
    }


    private static class SortMapper extends Mapper<Object, Text, NewK2 , LongWritable> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] token = value.toString().split("\t");
            NewK2 k = new NewK2(Long.parseLong(token[0]),Long.parseLong(token[1]));
            LongWritable v = new LongWritable(Long.parseLong(token[1]));
            context.write(k,v);
        }
    }

    private static class SortReducer extends Reducer<NewK2, LongWritable, LongWritable, LongWritable> {
        @Override
        protected void reduce(NewK2 key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            context.write(new LongWritable(key.getFirst()),new LongWritable(key.getSecond()));

        }
    }
}
