package org.zgg.hbase.withmapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.BasicConfigurator;
/*
 * 统计表user中每个人的薪水情况
 * 
 * */
public class WithMapreduce {

	public static void main(String[] args) throws Exception {
		
		//BasicConfigurator.configure();
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 
		
		Job job = Job.getInstance(conf,"mapreduce on hbase"); 
		
		job.setJarByClass(WithMapreduce.class);
		
		Scan scan = new Scan();
		scan.setCaching(10000);  //设置缓存的行的数量，这些行将被传给scanner
		scan.setCacheBlocks(false);  //设置是否缓存blocks
		//注意导包
		TableMapReduceUtil.initTableMapperJob("user", scan, MyMapper.class, Text.class, Text.class, job);
		TableMapReduceUtil.initTableReducerJob("user_salary",MyReducer.class,job);
		
		boolean b = job.waitForCompletion(true);
		if (!b) {
		  throw new IOException("error with job!");
		}
	}
	
	public static class MyMapper extends TableMapper<Text, Text>{

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			Text k = new Text(Bytes.toString(value.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));
			
			Text v = new Text(Bytes.toString(value.getValue(Bytes.toBytes("info2"), Bytes.toBytes("salary"))));
			
			context.write(k, v);			
		}	
	}
	
	public static class MyReducer extends TableReducer<Text, Text, ImmutableBytesWritable>{

		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Reducer<Text, Text, ImmutableBytesWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			Put put = new Put(Bytes.toBytes(key.toString()));
			
			for(Text val : values) {
				put.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("salary"), Bytes.toBytes(val.toString()));
			}
			context.write(null, put);			
		}		
	}
}
