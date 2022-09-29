package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class KeyOnlyFilterTest {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		// 仅返回 kv 的 k 部分，v被重写为空
        // 可以用来计数，但是效率没有FirstKeyOnlyFilter高
        KeyOnlyFilter filter=new KeyOnlyFilter();  
        Scan scan=new Scan();  
        scan.setFilter(filter);  
                 
        ResultScanner scanner=table.getScanner(scan);

        int i=0;
        System.out.println("Results of scan:");
	    for (Result result : scanner){
	        i++;
	    }

	    scanner.close();

        System.out.println(i);
	}
}
