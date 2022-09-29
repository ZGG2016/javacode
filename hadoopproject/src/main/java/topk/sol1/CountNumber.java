package topk.sol1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *  第一步：读取所有文件，统计各个url出现的次数
 */
public class CountNumber {

    private static final String IN = "hdfs://zgg:9000/in/topk/file*";
    private static final String OUT = "hdfs://zgg:9000/out/topk/cnum";

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {

        BasicConfigurator.configure();

        FileSystem fs = FileSystem.get(new URI(IN),new Configuration());
        Path out = new Path(OUT);
        if(fs.exists(out)){
            fs.delete(out,true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(CountNumber.class);

        job.setMapperClass(InputMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(CountReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job,new Path(IN));
        FileOutputFormat.setOutputPath(job,new Path(OUT));

        System.exit(job.waitForCompletion(true)?0:1);
    }

    private static class InputMapper extends Mapper<Object, Text,Text, IntWritable>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value,new IntWritable(1));
        }
    }

    private static class CountReduce extends Reducer<Text,IntWritable,Text,IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable val:values){
                sum += val.get();
            }
            context.write(key,new IntWritable(sum));
        }
    }
//    url0    7
//    url1    18
//    url2    11
//    url3    8
//    url4    12
//    url5    11
//    url6    12
//    url7    6
//    url8    5
//    url9    10
}
