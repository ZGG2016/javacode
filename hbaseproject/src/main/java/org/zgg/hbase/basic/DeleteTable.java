package org.zgg.hbase.basic;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class DeleteTable {

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
    
    public static void deleteTable(String tableName) throws IOException{
    	init();
    	
    	TableName tn = TableName.valueOf(tableName);
    	
    	if(admin.tableExists(tn)){
    		admin.disableTable(tn);
    		admin.deleteTable(tn);
    	}
    	System.out.println("删除成功");
    	close();  
    }
    
    public static void main(String[] args) throws Exception{
    	
    	deleteTable("table1");
    	
    }
	
}
