package topk.sol1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
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
 *  第二步：根据key和value新建一个新key，根据新key进行二次排序，并赋予序号
 */
public class Sort {

    private static final String IN = "hdfs://zgg:9000/out/topk/cnum";
    private static final String OUT = "hdfs://zgg:9000/out/topk/sort";

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
        job.setMapOutputValueClass(NullWritable.class);

        job.setReducerClass(AddNumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job,new Path(IN));
        FileOutputFormat.setOutputPath(job,new Path(OUT));

        System.exit(job.waitForCompletion(true)?0:1);
    }

    private static class InputMapper extends Mapper<Object, Text,TopK, NullWritable>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String[] str = value.toString().split("\t");
            context.write(new TopK(str[0], Integer.parseInt(str[1])), NullWritable.get());
        }
    }

    private static class AddNumReducer extends Reducer<TopK, NullWritable,Text,IntWritable>{

        private static int i = 0; //序号
        private static int prev = -1; //保存前一个value值

        @Override
        protected void reduce(TopK key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

            String k = key.getUrl();
            int v = key.getCount();

            if(prev == v){
                context.write(new Text(String.valueOf(i)+":"+k),new IntWritable(v));
            }else{
                context.write(new Text(String.valueOf(++i)+":"+k),new IntWritable(v));
                prev = v;
            }
        }
    }

//            1:url1  18
//            2:url6  12
//            2:url4  12
//            3:url5  11
//            3:url2  11
//            4:url9  10
//            5:url3  8
//            6:url0  7
//            7:url7  6
//            8:url8  5

}
