package org.zgg.hbase.counter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class SingleCounter {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 
		
		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("singlecounter"));
		
		long c1 = table.incrementColumnValue(Bytes.toBytes("r2"), Bytes.toBytes("colfam1"), 
				Bytes.toBytes("col1"), 1);
		
		long c2 = table.incrementColumnValue(Bytes.toBytes("r2"), Bytes.toBytes("colfam1"), 
				Bytes.toBytes("col1"), 5);
		
		long c3 = table.incrementColumnValue(Bytes.toBytes("r2"), Bytes.toBytes("colfam1"), 
				Bytes.toBytes("col1"), 0);
		
		long c4 = table.incrementColumnValue(Bytes.toBytes("r2"), Bytes.toBytes("colfam1"), 
				Bytes.toBytes("col1"), -2);
		
		System.out.println("c1:"+c1+"\t"
							+"c2:"+c2+"\t"
							+"c3:"+c3+"\t"
							+"c4:"+c4);
		table.close();
	    connection.close();

	}

}
