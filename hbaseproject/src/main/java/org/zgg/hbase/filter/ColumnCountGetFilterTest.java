package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class ColumnCountGetFilterTest {

	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		//简单的过滤器，只返回前N列的行。
		 ColumnCountGetFilter filter=new ColumnCountGetFilter(1);  
         Scan scan=new Scan();  
         scan.setFilter(filter);  

         ResultScanner scanner=table.getScanner(scan);  
         
         for (Result result : scanner){
             for (Cell cell : result.rawCells()) {
                 System.out.println("Cell: " + cell);
                 System.out.println("Value: " +
                         Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                 cell.getValueLength()));
                 //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
                 //Value: 22
             }  
         }
         scanner.close();  
	}
}
