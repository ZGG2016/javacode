package compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.net.URI;

public class MaxTemperatureWithCompression {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/sample.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/mtc";

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(MaxTemperatureWithCompression.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//        /*[*/FileOutputFormat.setCompressOutput(job, true);
//        FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);/*]*/

        FileOutputFormat.setCompressOutput(job, true);
        FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);

        job.setMapperClass(MaxTemperatureWithMapOutputCompression.MaxTemperatureMapper.class);
        job.setCombinerClass(MaxTemperatureWithMapOutputCompression.MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureWithMapOutputCompression.MaxTemperatureReducer.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
