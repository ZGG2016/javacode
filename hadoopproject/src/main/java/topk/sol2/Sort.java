package topk.sol2;

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
 *  第二步：定义一个新key，根据新key进行排序
 *
 *  统计次数、赋予序号、取topk，类似方法1
 */
public class Sort {

    private static final String IN = "hdfs://zgg:9000/out/topk/cnum";
    private static final String OUT = "hdfs://zgg:9000/out/topk/res2";

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {

        BasicConfigurator.configure();

        FileSystem fs = FileSystem.get(new URI(IN),new Configuration());
        Path out = new Path(OUT);
        if(fs.exists(out)){
            fs.delete(out,true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(Sort.class);

        job.setMapperClass(InputMapper.class);
        job.setMapOutputKeyClass(TopK.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(GetKReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job,new Path(IN));
        FileOutputFormat.setOutputPath(job,new Path(OUT));

        System.exit(job.waitForCompletion(true)?0:1);
    }


    private static class InputMapper extends Mapper<Object, Text, TopK, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String[] ss = value.toString().split("\t");
            context.write(new TopK(Integer.parseInt(ss[1])), new Text(ss[0]));
        }
    }
    private static class GetKReducer extends Reducer<TopK, Text,Text,IntWritable> {

        @Override
        protected void reduce(TopK key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            for(Text val:values){
                context.write(val,new IntWritable(key.getNum()));
            }
        }
    }
//    url1    18
//    url6    12
//    url4    12
//    url5    11
//    url2    11
//    url9    10
//    url3    8
//    url0    7
//    url7    6
//    url8    5
}
