package org.zgg.hbase.counter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class MultipleCounter {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 
		
		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("multiplecounter"));
		//首先创建Increment实例
		Increment increment = new Increment(Bytes.toBytes("r3"));
		
		//向添加的行增加列
		increment.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col1"), 1);
		increment.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col2"), 1);

		Result result = table.increment(increment);
		
		for(Cell cell : result.rawCells()){
			System.out.println("Cell:"+cell+" "+
								"Value:"+Bytes.toLong(cell.getValueArray(),cell.getValueOffset(),
										cell.getValueLength()));
		}
		table.close();
	    connection.close();
		
	}

}
