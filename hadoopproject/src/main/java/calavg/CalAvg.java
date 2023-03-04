package calavg;

import combine.combine01;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;
import wordcount.WordCount;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.Properties;

/*
* 需求：计算每个学生的平均分
*
* 数据源：
* 格式：学生编号，学科，分数
* 内容：
*       A,math,70
*       B,math,80
*       C,math,90
*       A,science,88
*       B,science,86
*       C,science,90
* 结果：
*       A       79.00
*       B       83.00
*       C       90.00
* */
public class CalAvg {

    private static final String INPUT_PATH = "hdfs://bigdata101:9000/in/calavg.txt";
    private static final String OUTPUT_PATH = "hdfs://bigdata101:9000/out/calavg";

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Properties properties = System.getProperties();
        properties.setProperty("HADOOP_USER_NAME","root");

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(CalAvg.class);

        job.setMapperClass(CalAvgMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(CalAvgReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class CalAvgMapper extends Mapper<Object,Text, Text, IntWritable>{

        private final Text id = new Text();
        private final IntWritable score = new IntWritable();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] idscore = value.toString().split(",");
            id.set(idscore[0]);
            score.set(Integer.parseInt(idscore[2]));
            context.write(id,score);
        }
    }

    public static class CalAvgReducer extends Reducer<Text, IntWritable,Text, Text>{

        private final Text avgscore = new Text();
        DecimalFormat df = new DecimalFormat("#.00");

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            for(IntWritable val:values){
                sum += val.get();
                count++;
            }
            avgscore.set(df.format(sum / count));

            context.write(key,avgscore);
        }
    }

}
