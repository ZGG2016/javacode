package combine;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class combine02 {

	private static final String INPUT_PATH = "hdfs://zgg:9000/in/combinedata2.txt";
	private static final String OUT_PATH = "hdfs://zgg:9000/out/combine";
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		final FileSystem fileSystem = FileSystem.get(new URI(INPUT_PATH), conf);
		final Path outPath = new Path(OUT_PATH);
		if(fileSystem.exists(outPath)){
			fileSystem.delete(outPath, true);
		}
		
		final Job job = new Job(conf , combine02.class.getSimpleName());
		job.setJarByClass(combine02.class);
		//1.1ָ����ȡ���ļ�λ������
		FileInputFormat.setInputPaths(job, INPUT_PATH);
		//ָ����ζ������ļ����и�ʽ�����������ļ�ÿһ�н����ɼ�ֵ��
		//job.setInputFormatClass(TextInputFormat.class);
		
		//1.2 ָ���Զ����map��
		job.setMapperClass(MyMapper.class);
		//map�����<k,v>���͡����<k3,v3>��������<k2,v2>����һ�£������ʡ��
		//job.setMapOutputKeyClass(Text.class);
		//job.setMapOutputValueClass(LongWritable.class);
		
		//1.3 ����
		job.setPartitionerClass(MyPartitioner.class);
		//��һ��reduce��������
		job.setNumReduceTasks(2);
		
		//1.4 TODO ���򡢷���
		
		//1.5 ��Լ
		job.setCombinerClass(MyCombiner.class);
		
		//2.2 ָ���Զ���reduce��
		job.setReducerClass(MyReducer.class);
		//ָ��reduce���������
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		//2.3 ָ��д��������
		FileOutputFormat.setOutputPath(job, outPath);
		//ָ������ļ��ĸ�ʽ����
		//job.setOutputFormatClass(TextOutputFormat.class);
		
		//��job�ύ��JobTracker����
		job.waitForCompletion(true);
	}
	
	static class MyPartitioner extends Partitioner<Text, LongWritable>{
		@Override
		public int getPartition(Text key, LongWritable value, int numReduceTasks) {
			return (key.toString().equals("hello"))?0:1;
		}
	}
	
	/**
	 * KEYIN	��k1		��ʾ�е�ƫ����
	 * VALUEIN	��v1		��ʾ���ı�����
	 * KEYOUT	��k2		��ʾ���г��ֵĵ���
	 * VALUEOUT	��v2		��ʾ���г��ֵĵ��ʵĴ������̶�ֵ1
	 */
	static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		protected void map(LongWritable k1, Text v1, Context context) throws java.io.IOException ,InterruptedException {
			final String[] splited = v1.toString().split("\t");
			for (String word : splited) {
				context.write(new Text(word), new LongWritable(1));
				System.out.println("Mapper���<"+word+","+1+">");
			}
		}
	}
	
	/**
	 * KEYIN	��k2		��ʾ���г��ֵĵ���
	 * VALUEIN	��v2		��ʾ���г��ֵĵ��ʵĴ���
	 * KEYOUT	��k3		��ʾ�ı��г��ֵĲ�ͬ����
	 * VALUEOUT	��v3		��ʾ�ı��г��ֵĲ�ͬ���ʵ��ܴ���
	 *
	 */
	static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		protected void reduce(Text k2, Iterable<LongWritable> v2s, Context ctx) throws java.io.IOException ,InterruptedException {
			//��ʾ������ʾredcue�����������˶��ٴΣ���ʾk2�ж��ٸ�����
			System.out.println("MyReducer�������<"+k2.toString()+",...>");
			long times = 0L;
			for (LongWritable count : v2s) {
				times += count.get();
				//��ʾ������ʾ�����k2,v2�ļ�ֵ������
				System.out.println("MyReducer�����ֵ��<"+k2.toString()+","+count.get()+">");
			}
			ctx.write(k2, new LongWritable(times));
		}
	}


	static class MyCombiner extends Reducer<Text, LongWritable, Text, LongWritable>{
		protected void reduce(Text k2, Iterable<LongWritable> v2s, Context ctx) throws java.io.IOException ,InterruptedException {
			//��ʾ������ʾredcue�����������˶��ٴΣ���ʾk2�ж��ٸ�����
			System.out.println("Combiner�������<"+k2.toString()+",...>");
			long times = 0L;
			for (LongWritable count : v2s) {
				times += count.get();
				//��ʾ������ʾ�����k2,v2�ļ�ֵ������
				System.out.println("Combiner�����ֵ��<"+k2.toString()+","+count.get()+">");
			}
			
			ctx.write(k2, new LongWritable(times));
			//��ʾ������ʾ�����k2,v2�ļ�ֵ������
			System.out.println("Combiner�����ֵ��<"+k2.toString()+","+times+">");
		}
	}
}
