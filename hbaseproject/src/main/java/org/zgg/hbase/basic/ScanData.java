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
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

public class ScanData {

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
    
    //public static void scanData(String tableName,String startRow,String stopRow)throws IOException{  
    public static void scanData(String tableName)throws IOException{  
        init();  
        Table table = connection.getTable(TableName.valueOf(tableName));  
        Scan scan = new Scan();  
//        scan.setStartRow(Bytes.toBytes(startRow));  
//        scan.setStopRow(Bytes.toBytes(stopRow));  
        ResultScanner resultScanner = table.getScanner(scan);  
        for(Result result : resultScanner){  
            showCell(result);  
        }  
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
	
	public static void main(String[] args) throws IOException {
		
		scanData("user");

	}

}
