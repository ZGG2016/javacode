package compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

public class MaxTemperatureWithMapOutputCompression {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/sample.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/mtmc";

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();
        conf.setBoolean(Job.MAP_OUTPUT_COMPRESS, true);
        conf.setClass(Job.MAP_OUTPUT_COMPRESS_CODEC, BZip2Codec.class,
                CompressionCodec.class);

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(MaxTemperatureWithMapOutputCompression.class);

        job.setMapperClass(MaxTemperatureMapper.class);

        job.setCombinerClass(MaxTemperatureReducer.class);

        job.setReducerClass(MaxTemperatureReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private static final int MISSING = 9999;

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString();
            String year = line.substring(15, 19);
            int airTemperature;
            if (line.charAt(87) == '+') { // parseInt doesn't like leading plus signs
                airTemperature = Integer.parseInt(line.substring(88, 92));
            } else {
                airTemperature = Integer.parseInt(line.substring(87, 92));
            }
            String quality = line.substring(92, 93);
            if (airTemperature != MISSING && quality.matches("[01459]")) {
                context.write(new Text(year), new IntWritable(airTemperature));
            }
        }
    }

    public static class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context)
                throws IOException, InterruptedException {

            int maxValue = Integer.MIN_VALUE;
            for (IntWritable value : values) {
                maxValue = Math.max(maxValue, value.get());
            }
            context.write(key, new IntWritable(maxValue));
        }
    }
}
