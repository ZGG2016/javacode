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
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.BasicConfigurator;

public class WordCount {

	public static void main(String[] args) throws Exception{
			
			// BasicConfigurator.configure();
			Configuration conf = HBaseConfiguration.create();
			
			conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
			conf.set("hbase.zookeeper.quorum","zgg");
			conf.set("hbase.zookeeper.property.clientPort","2181");  
			conf.set("zookeeper.znode.parent","/hbase"); 
			
			Job job = Job.getInstance(conf,"wordcount"); 
			job.setJarByClass(WordCount.class);
			
			Scan scan = new Scan();
			scan.setCaching(10000);  //设置缓存的行的数量，这些行将被传给scanner
			scan.setCacheBlocks(false);  //设置是否缓存blocks
			//注意导包
			TableMapReduceUtil.initTableMapperJob("test", scan, CountMapper.class, Text.class, IntWritable.class, job);
			TableMapReduceUtil.initTableReducerJob("test2",CountReducer.class,job);
			
			boolean b = job.waitForCompletion(true);
			if (!b) {
			  throw new IOException("error with job!");
			}

	}	
	public static class CountMapper extends TableMapper<Text, IntWritable>{

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			String words = Bytes.toString(value.getValue(Bytes.toBytes("info"), Bytes.toBytes("word")));
			String[] token = words.split(" ");
			for(String str : token) {
				context.write(new Text(str), new IntWritable(1));
			}			
		}		
	}
	
	public static class CountReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable>{

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, ImmutableBytesWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			Put put = new Put(Bytes.toBytes(key.toString()));
			int sum=0;
			for(IntWritable val : values) {
				sum+=val.get();
			}
			
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("word"), Bytes.toBytes(String.valueOf(sum)));
			context.write(null, put);
			
		}
		
	}
}


