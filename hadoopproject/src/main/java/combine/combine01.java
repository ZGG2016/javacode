package combine;

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

import java.net.URI;

/**
 * 问：为什么使用Combiner？
 * 答：Combiner发生在Map端，对数据进行规约处理，数据量变小了，传送到reduce端的数据量变小了，传输时间变短，
 *    作业的整体时间变短。
 * 
 * 问：为什么Combiner不作为MR运行的标配，而是可选步骤哪？
 * 答：因为不是所有的算法都适合使用Combiner处理，例如求平均数。
 *
 * 问：Combiner本身已经执行了reduce操作，为什么在Reducer阶段还要执行reduce操作哪？
 * 答：combiner操作发生在map端的，处理一个任务所接收的文件中的数据，不能跨map任务执行；
 *    只有reduce可以接收多个map任务处理的数据。
 *
 */
public class combine01 {
	private static final String INPUT_PATH = "hdfs://zgg:9000/in/combinedata.txt";
	private static final String OUT_PATH = "hdfs://zgg:9000/out/combine";
	
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		final FileSystem fileSystem = FileSystem.get(new URI(INPUT_PATH), conf);
		final Path outPath = new Path(OUT_PATH);
		if(fileSystem.exists(outPath)){
			fileSystem.delete(outPath, true);
		}
		
		Job job = new Job(conf , combine01.class.getSimpleName());

		job.setMapperClass(MyMapper.class);
		//map输出的<k,v>类型。如果<k3,v3>的类型与<k2,v2>类型一致，则可以省略
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setCombinerClass(MyCombiner.class);

		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		FileInputFormat.setInputPaths(job, INPUT_PATH);
		FileOutputFormat.setOutputPath(job, outPath);

		job.waitForCompletion(true);
	}
	
	/**
	 * KEYIN	即k1		表示行的偏移量
	 * VALUEIN	即v1		表示行文本内容
	 * KEYOUT	即k2		表示行中出现的单词
	 * VALUEOUT	即v2		表示行中出现的单词的次数，固定值1
	 */
	static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		protected void map(LongWritable k1, Text v1, Context context) throws java.io.IOException ,InterruptedException {

			final String[] splited = v1.toString().split("\t");
			for (String word : splited) {
				context.write(new Text(word), new LongWritable(1));
				System.out.println("Mapper输出<"+word+","+1+">");
			}
		}
	}
	
	/**
	 * KEYIN	即k2		表示行中出现的单词
	 * VALUEIN	即v2		表示行中出现的单词的次数
	 * KEYOUT	即k3		表示文本中出现的不同单词
	 * VALUEOUT	即v3		表示文本中出现的不同单词的总次数
	 *
	 */
	static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		protected void reduce(Text k2, Iterable<LongWritable> v2s, Context ctx) throws java.io.IOException ,InterruptedException {
			//显示次数表示redcue函数被调用了多少次，表示k2有多少个分组
			System.out.println("MyReducer输入分组<"+k2.toString()+",...>");
			long times = 0L;
			for (LongWritable count : v2s) {
				times += count.get();
				//显示次数表示输入的k2,v2的键值对数量
				System.out.println("MyReducer输入键值对<"+k2.toString()+","+count.get()+">");
			}
			ctx.write(k2, new LongWritable(times));
		}
	}


	static class MyCombiner extends Reducer<Text, LongWritable, Text, LongWritable>{
		protected void reduce(Text k2, Iterable<LongWritable> v2s, Context ctx) throws java.io.IOException ,InterruptedException {
			//显示次数表示redcue函数被调用了多少次，表示k2有多少个分组
			System.out.println("Combiner输入分组<"+k2.toString()+",...>");
			long times = 0L;
			for (LongWritable count : v2s) {
				times += count.get();
				//显示次数表示输入的k2,v2的键值对数量
				System.out.println("Combiner输入键值对<"+k2.toString()+","+count.get()+">");
			}

			ctx.write(k2, new LongWritable(times));
			//显示次数表示输出的k2,v2的键值对数量
			System.out.println("Combiner输出键值对<"+k2.toString()+","+times+">");
		}
	}
}
