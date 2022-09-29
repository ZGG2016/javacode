package dupdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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

/*
 * 数据去重：利用shuffle特性，key相同的数据进入同一分区，即在一个reduce方法中处理，
 *         所以，到reduce方法后，是这样形式(key,value-list)，这时只取key就达到了去重的目的。
 *
 * 2017-7-1
 * 2018-7-1
 * 2019-7-1
 * */

public class DupData {
	
	static final String input_path = "hdfs://zgg:9000/in/dup.txt";
	static final String output_path = "hdfs://zgg:9000/out/dup";

	public static void main(String[] args) throws Exception{
		
		BasicConfigurator.configure(); 
		Configuration conf = new Configuration();
		
		FileSystem fileSystem = FileSystem.get(new URI(input_path), conf);
		Path outPath = new Path(output_path);
		if(fileSystem.exists(outPath)){
			fileSystem.delete(outPath, true);
		}
		
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(DupData.class);
		
		job.setMapperClass(DedupMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setReducerClass(DedupReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job,new Path(input_path));
		FileOutputFormat.setOutputPath(job, outPath);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
	
	public static class DedupMapper extends Mapper<Object, Text, Text, NullWritable>{

		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			context.write(value, NullWritable.get());  //key:2017-7-1 a   value:空
		}
	}
	
	public static class DedupReducer extends Reducer<Text,NullWritable,Text,Text>{

		@Override
		protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
			context.write(key, new Text(" "));   //key:2017-7-1 a   value:空
		}
	}

}
