package org.zgg.hbase.basic;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class GetData {

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
    
    public static void getData(String tableName,String rowkey,
    		String colFamily,String col)throws  IOException{
    	
    	init();  
        Table table = connection.getTable(TableName.valueOf(tableName));  
        Get get = new Get(Bytes.toBytes(rowkey));  
        //获取指定列族数据  
        get.addFamily(Bytes.toBytes(colFamily));  
        //获取指定列数据  
        get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));  
        Result result = table.get(get);  
  
        showCell(result);  
        table.close();  
        close();
    }
    
    public static void showCell(Result result){  
        Cell[] cells = result.rawCells();  
        for(Cell cell:cells){  
            System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");  
            System.out.println("Timetamp:"+cell.getTimestamp()+" ");  
            System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");  
            System.out.println("column Name:"+new String(CellUtil.cloneQualifier(cell))+" ");  
            System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");  
        }  
    }  
	
	public static void main(String[] args) throws Exception {
		
		getData("user", "1", "info1", "name");
		System.out.println("获取成功");
		
	}

}
