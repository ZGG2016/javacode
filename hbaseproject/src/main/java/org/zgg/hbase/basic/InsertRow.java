package org.zgg.hbase.basic;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class InsertRow {

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
    
    public static void insertRow(String tableName,String rowkey,String colFamily,
    		String col,String val) throws IOException{
    	
    	init();
    	/*
    	 * Table类对表中数据的操作
    	 * 
    	 * TableName对表的操作
    	 * */
    	Table table = connection.getTable(TableName.valueOf(tableName));
    	
    	Put p1 = new Put(Bytes.toBytes(rowkey)); //相当于指定插入的行
    	p1.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
    	table.put(p1);   	  
    	
        table.close();  
        close(); 
    }
    
    public static void main(String[] args) throws Exception{
    	   	
    	insertRow("user", "1", "info1", "name", "tom");
    	insertRow("user", "1", "info1", "age", "22");
    	insertRow("user", "1", "info2", "occupation", "teacher");
    	insertRow("user", "1", "info2", "salary", "2000");
    	
    	insertRow("user", "2", "info1", "name", "jack");
    	insertRow("user", "2", "info1", "age", "20");
    	insertRow("user", "2", "info2", "occupation", "police");
    	insertRow("user", "2", "info2", "salary", "2500");
    	
    	insertRow("user", "3", "info1", "name", "mike");   	    	
    	insertRow("user", "3", "info1", "age", "25");
    	insertRow("user", "3", "info2", "occupation", "programmer");
    	insertRow("user", "3", "info2", "salary", "5000");
    	
    	insertRow("user", "4", "info1", "name", "marcus");   	    	
    	insertRow("user", "4", "info1", "age", "30");
    	insertRow("user", "4", "info2", "occupation", "sporter");
    	insertRow("user", "4", "info2", "salary", "6000");
    	   	
//    	insertRow("students","jack","basicinfo","age","28");
//    	insertRow("students", "jack", "moreinfo", "tel", "119");
//    	
//    	insertRow("students", "jim", "basicinfo", "age", "28");
//    	insertRow("students", "jim", "moreinfo", "tel", "112");
//    	
//    	insertRow("students", "peter", "basicinfo", "age", "27");
//    	insertRow("students", "peter", "moreinfo", "tel", "110");
//    	
//    	insertRow("students", "tom", "basicinfo", "age", "27");
//    	insertRow("students", "tom", "moreinfo", "tel", "111");
    	
    	System.out.println("插入成功");
    }
	
}
