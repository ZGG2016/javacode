package customsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

public class CustormSortMain {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/groupdata.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/groupdata";

    public static void main(String[] Args) throws Exception {
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH),conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);

        job.setJarByClass(CustormSortMain.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setMapperClass(CustormSortMapper.class);
        job.setMapOutputKeyClass(TextInt.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setPartitionerClass(SortPartitioner.class);
        job.setSortComparatorClass(TextIntComparator.class);
        job.setGroupingComparatorClass(TextComparator.class);

        job.setReducerClass(CustormSortReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static class CustormSortMapper extends Mapper<Object, Text, TextInt, IntWritable> {
        TextInt textInt = new TextInt();
        IntWritable intp = new IntWritable(0);

        @Override
        protected void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            int i =  Integer.parseInt(value.toString());

            textInt.setStr(key.toString());
            textInt.setValue(i);
            intp.set(i);
            context.write(textInt,intp);
        }

    }

    private static class CustormSortReducer extends Reducer<TextInt, IntWritable, Text, Text> {
        @Override
        protected void reduce(TextInt key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            StringBuilder stringCombine = new StringBuilder();
            for (IntWritable intWritable : values) {
                int value = intWritable.get();
                stringCombine.append(value).append(",");
            }
            int length = stringCombine.length();
            if(length > 0)
                stringCombine.deleteCharAt(length - 1);
            context.write(new Text(key.getStr()), new Text(stringCombine.toString()));

        }
    }
}

//原文连接：https://blog.csdn.net/syraphie/article/details/22691613