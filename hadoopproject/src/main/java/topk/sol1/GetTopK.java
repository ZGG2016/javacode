package topk.sol1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
 *  第三步：取出前NUM个值，如果并列的话，也一起取出。
 */
public class GetTopK {

    private static final String IN = "hdfs://zgg:9000/out/topk/sort";
    private static final String OUT = "hdfs://zgg:9000/out/topk/res1";

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {

        BasicConfigurator.configure();

        FileSystem fs = FileSystem.get(new URI(IN),new Configuration());
        Path out = new Path(OUT);
        if(fs.exists(out)){
            fs.delete(out,true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(GetTopK.class);

        job.setMapperClass(InputMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(GetKReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job,new Path(IN));
        FileOutputFormat.setOutputPath(job,new Path(OUT));

        System.exit(job.waitForCompletion(true)?0:1);
    }

    private static class InputMapper extends Mapper<Object, Text,Text, Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String[] sval = value.toString().split("\t");
            context.write(new Text(sval[0]), new Text(sval[1]));
        }
    }

    private static class GetKReducer extends Reducer<Text, Text,Text,Text>{
        private static final int NUM = 5;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            String[] skey = key.toString().split(":");

            for(Text val:values){
                if(Integer.parseInt(skey[0])<=NUM){
                    context.write(new Text(skey[1]),val);

                }
            }
        }
    }
//    url1    18
//    url4    12
//    url6    12
//    url2    11
//    url5    11
//    url9    10
//    url3    8
}
