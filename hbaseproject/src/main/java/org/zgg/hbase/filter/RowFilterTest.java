package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.BasicConfigurator;

public class RowFilterTest {

	public static void main(String[] args) throws Exception {
//		BasicConfigurator.configure();

		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		Scan scan = new Scan();

	    // 用于根据键进行过滤。
		// 过滤出rowkey大于3的行
		Filter filter = new RowFilter(CompareOperator.GREATER,
				new BinaryComparator(Bytes.toBytes("3")));
	    		
	    scan.setFilter(filter);
	    ResultScanner scanner = table.getScanner(scan);
	    System.out.println("Scanning table user...");

	    for (Result result : scanner) {
			for (Cell cell : result.rawCells()) {
				System.out.println("Cell: " + cell);
				System.out.println("Value: " +
						Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength()));
				//Scanning table user...
				//Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
				//Value: 30
				//Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
				//Value: marcus
				//Cell: 4/info2:occupation/1608553940504/Put/vlen=7/seqid=0
				//Value: sporter
				//Cell: 4/info2:salary/1608553940541/Put/vlen=4/seqid=0
				//Value: 6000
			}
	    }
	    scanner.close();
	}

}
