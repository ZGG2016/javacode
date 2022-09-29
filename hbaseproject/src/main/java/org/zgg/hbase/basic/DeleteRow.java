package org.zgg.hbase.basic;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class DeleteRow {
	
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
    
    public static void deleteRow(String tableName,String rowkey,
    		String colFamily,String col) throws IOException{
    	
    	init();
    	
    	Table table = connection.getTable(TableName.valueOf(tableName));
    	
    	Delete delete = new Delete(Bytes.toBytes(rowkey));
    	
    	//delete.addFamily(Bytes.toBytes(colFamily)); //删除指定列族  
    	
    	delete.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col));  //删除指定列  
    	table.delete(delete);
    	table.close();  
        close();  
    		
    }
    
    public static void main(String[] args) throws Exception{
    	
    
    	deleteRow("user", "1", "info1", "age");
    	System.out.println("删除成功");
    }    
}
