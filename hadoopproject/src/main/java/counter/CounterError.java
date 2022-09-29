package counter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

/*
* 计数器:
*    实现文件行数，以及其中错误记录的统计
 * */
public class CounterError {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/counter.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/counter";

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        //使NLineInputFormat来分割一个小文件，近而模拟分布式大文件的处理
        conf.setInt("mapreduce.input.lineinputformat.linespermap", 5);
        Job job = Job.getInstance(conf);
        job.setInputFormatClass(NLineInputFormat.class);
        job.setJarByClass(CounterError.class);

        job.setMapperClass(CounterMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, outPath);

        if(job.waitForCompletion(true)) {
            System.out.println("Error num:" +
                    job.getCounters().findCounter(CounterMapper.FileRecorder.ErrorRecorder).getValue());
            System.out.println("Total num:" +
                    job.getCounters().findCounter(CounterMapper.FileRecorder.TotalRecorder).getValue());
        }
    }

    private static class CounterMapper extends Mapper<LongWritable, Text, LongWritable, IntWritable> {

        public static enum FileRecorder{
            ErrorRecorder,
            TotalRecorder
        }

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            if("error".equals(value.toString())){
                context.getCounter(FileRecorder.ErrorRecorder).increment(1);
            }
            context.getCounter(FileRecorder.TotalRecorder).increment(1);
        }
    }
}
