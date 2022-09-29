package org.zgg.hbase.basic;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class CreateTable {

	public static Configuration conf ;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws IOException {
		String[] cols = {"info1","info2"};
		String tName = "user";
		
		createTable(tName, cols);

	}

	public static void init() throws IOException{

		//BasicConfigurator.configure();

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

	public static void createTable(String tName,String[] cols) throws IOException {
		init();
		TableName tableName = TableName.valueOf(tName);
		if(admin.tableExists(tableName)) {
			System.out.println("table is exists! and it will be overwrited !");  
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}
		HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
		for(String col : cols) {
			HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
			hTableDescriptor.addFamily(hColumnDescriptor);
		}
		admin.createTable(hTableDescriptor);
		System.out.println("创建成功");
		close();
		}

	public static void close() throws IOException {
		admin.close();
		connection.close();
	}
}
