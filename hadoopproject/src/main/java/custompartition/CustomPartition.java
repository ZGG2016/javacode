package custompartition;

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

/*
* 现在需要统计手机的上行数据包，下行数据包，上行总流量，下行总流量。
* 分析：可以以手机号为key 以上4个字段为value传传递数据。
* */
public class CustomPartition {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/partitiondata.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/partitiondata";

    public static void main(String[] Args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }

        Job job = Job.getInstance(conf);

        job.setJarByClass(CustomPartition.class);

        job.setMapperClass(PartitionMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(KpiWritable.class);

        job.setPartitionerClass(KpiPartitioner.class);
        job.setNumReduceTasks(2);

        job.setReducerClass(PartitionReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

    private static class PartitionMapper extends Mapper<Object, Text, Text, KpiWritable> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] token = value.toString().split("\t");

            KpiWritable v2 = new KpiWritable(token[6],token[7],token[8],token[9]);
            context.write(new Text(token[0]),v2);
        }

    }

    private static class PartitionReducer extends Reducer<Text, KpiWritable, Text, KpiWritable> {
        @Override
        protected void reduce(Text key, Iterable<KpiWritable> values, Context context) throws IOException, InterruptedException {
            long upPackNum = 0L;
            long downPackNum = 0L;
            long upPayLoad = 0L;
            long downPayLoad = 0L;


            for (KpiWritable val : values) {
                upPackNum += val.getUpPackNum();
                downPackNum += val.getDownPackNum();
                upPayLoad += val.getUpPayLoad();
                downPayLoad += val.getDownPayLoad();
            }
            KpiWritable v3 = new KpiWritable(String.valueOf(upPackNum), String.valueOf(downPackNum),
                    String.valueOf(upPayLoad), String.valueOf(downPayLoad));
            context.write(key, v3);
        }
    }
}
