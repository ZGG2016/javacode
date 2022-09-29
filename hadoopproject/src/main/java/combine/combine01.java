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
 * �ʣ�Ϊʲôʹ��Combiner��
 * ��Combiner������Map�ˣ������ݽ��й�Լ������������С�ˣ����͵�reduce�˵���������С�ˣ�����ʱ���̣�
 *    ��ҵ������ʱ���̡�
 * 
 * �ʣ�ΪʲôCombiner����ΪMR���еı��䣬���ǿ�ѡ�����ģ�
 * ����Ϊ�������е��㷨���ʺ�ʹ��Combiner����������ƽ������
 *
 * �ʣ�Combiner�����Ѿ�ִ����reduce������Ϊʲô��Reducer�׶λ�Ҫִ��reduce�����ģ�
 * ��combiner����������map�˵ģ�����һ�����������յ��ļ��е����ݣ����ܿ�map����ִ�У�
 *    ֻ��reduce���Խ��ն��map����������ݡ�
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
		//map�����<k,v>���͡����<k3,v3>��������<k2,v2>����һ�£������ʡ��
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
