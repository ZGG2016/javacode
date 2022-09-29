package org.zgg.hbase.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class Batch {
	
	private final static byte[] info = Bytes.toBytes("info");
	private final static byte[] prof = Bytes.toBytes("prof");
	private final static byte[] name = Bytes.toBytes("name");
	private final static byte[] salary = Bytes.toBytes("salary");
	private final static byte[] occupation = Bytes.toBytes("occupation");
	
	public static Configuration conf;  
    public static Connection connection;  
    public static Admin admin; 
    
    public static void init() throws IOException{
		conf = HBaseConfiguration.create(); 
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase");  
		//ConnectionFactory：创建客户端和hbase的连接
		connection = ConnectionFactory.createConnection(conf);
		//Admin:定义了对表的各种操作
		admin = connection.getAdmin();
	}
	
	public static void close() throws IOException {
		admin.close();
		connection.close();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		init();
    	
    	Table table = connection.getTable(TableName.valueOf("table1"));
		
		List<Row> batch = new ArrayList<Row>();
		
		Put put = new Put(Bytes.toBytes("1")); //第一行，往列族info的name列写入新数据
		put.addColumn(info, name, Bytes.toBytes("alice"));
		batch.add(put);
		
		Get get = new Get(Bytes.toBytes("2")); //第二行，获取列族prof的salary的内容
		get.addColumn(prof,salary);
		batch.add(get);
		
		Delete delete = new Delete(Bytes.toBytes("2"));
		delete.addColumn(prof, occupation);
		batch.add(delete);
		
		Object[] results = new Object[batch.size()]; 
		
		table.batch(batch, results);
		
		for (int i = 0; i < results.length; i++) { 
            System.out.println("Result[" + i + "]: type = " + 
                    results[i].getClass().getSimpleName() + "; " + results[i]); 
        } 		
		table.close();
		close();
	}
	
	
}
