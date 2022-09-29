package customgroup;

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
import secondsort.sol1.NewK2;

import java.io.IOException;
import java.net.URI;

public class CustomGrouping {
    private static final String INPUT_PATH = "hdfs://zgg:9000/in/groupdata.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/groupdata";

    public static void main(String[] Args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH),conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);

        job.setJarByClass(CustomGrouping.class);

        job.setMapperClass(GroupMapper.class);
        job.setMapOutputKeyClass(NewK2.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setGroupingComparatorClass(MyGroupingComparator.class);

        job.setReducerClass(GroupReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true)?0:1);
    }

    private static class GroupMapper extends Mapper<Object, Text, NewK2 , LongWritable> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] token = value.toString().split("\t");
            NewK2 k = new NewK2(Long.parseLong(token[0]),Long.parseLong(token[1]));
            LongWritable v = new LongWritable(Long.parseLong(token[1]));
            context.write(k,v);
        }
    }

    private static class GroupReducer extends Reducer<NewK2, LongWritable, LongWritable, LongWritable> {
        @Override
        protected void reduce(NewK2 key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long min = Long.MAX_VALUE;
            for(LongWritable val : values){
                if (val.get() < min){
                    min = val.get();
                }
            }

            context.write(new LongWritable(key.getFirst()), new LongWritable(min));
        }
    }

}
