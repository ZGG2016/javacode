package secondsort.sol2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SecondSort {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/secondsort.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/secondsort";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(SecondSort.class);

        job.setMapperClass(SecondSortMapper.class);
        job.setMapOutputKeyClass(TextInt.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setPartitionerClass(SecondSortPartitioner.class);
        job.setSortComparatorClass(TextIntComparator.class);
        job.setGroupingComparatorClass(TextComparator.class);

        job.setReducerClass(SecondSortReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        //输出：
//        1       1,1
//        2       1,1,2,2
//        3       1,1,2,2,3,3

    }

    private static class SecondSortMapper extends Mapper<Object, Text, TextInt, IntWritable> {

        public TextInt textInt = new TextInt();
        public IntWritable intp = new IntWritable();

        @Override
        protected void map(Object key, Text value,
                           Context context)
                throws IOException, InterruptedException {

            String[] ss = value.toString().split("\t");

            textInt.setStr(ss[0]);
            textInt.setValue(Integer.parseInt(ss[1]));

            intp.set(Integer.parseInt(ss[1]));

            // key:k+v
            // value:v
            context.write(textInt,intp);
        }
    }

    private static class SecondSortReducer extends Reducer<TextInt, IntWritable, Text, Text> {

        @Override
        protected void reduce(TextInt textInt, Iterable<IntWritable> values,
                              Context context)
                throws IOException, InterruptedException {

            StringBuilder stringCombine = new StringBuilder();
            for (IntWritable intWritable : values) {
                int value = intWritable.get();
                stringCombine.append(value).append(",");
            }
            int length = stringCombine.length();
            if(length > 0)
                stringCombine.deleteCharAt(length - 1);
            context.write(new Text(textInt.getStr()), new Text(stringCombine.toString()));
        }
    }

    //使用第一列值分区
    private static class SecondSortPartitioner extends Partitioner<TextInt, IntWritable> {

        @Override
        public int getPartition(TextInt textInt, IntWritable value, int numReducers) {
            return textInt.getStr().hashCode() & Integer.MAX_VALUE % numReducers;
        }
    }

    //自定义Mapper端的排序比较类
    private static class TextIntComparator extends WritableComparator {

        public TextIntComparator(){
            super(TextInt.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            TextInt o1 = (TextInt) a;
            TextInt o2 = (TextInt) b;

            if(!o1.getStr().equals(o2.getStr()))
                return o1.getStr().compareTo(o2.getStr());
            else
                return o1.getValue() - o2.getValue();
        }
    }

    //自定义分组器
    private static class TextComparator extends WritableComparator{
        public TextComparator(){
            super(TextInt.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            TextInt o1 = (TextInt) a;
            TextInt o2 = (TextInt) b;
            return o1.getStr().compareTo(o2.getStr());
        }
    }
}
