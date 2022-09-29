package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

public class ValueFilterTest {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("salary"));

		// 根据列值过滤
		Filter filter = new ValueFilter(CompareOperator.EQUAL,
				new SubstringComparator("2000"));  //单元格的值中包含2000的

		scan.setFilter(filter);

		ResultScanner scanner = table.getScanner(scan);			
		System.out.println("Results of scan:");
		for (Result result : scanner) {
			for (Cell cell : result.rawCells()) {
				System.out.println("Cell: " + cell);
				System.out.println("Value: " +
						Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength()));
				//Results of scan:
				//Cell: 1/info2:salary/1608553940135/Put/vlen=4/seqid=0
				//Value: 2000
			}
		}
		scanner.close();

	}
}
