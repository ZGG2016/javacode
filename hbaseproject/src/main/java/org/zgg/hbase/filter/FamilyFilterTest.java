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
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class FamilyFilterTest {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		//获取指定列族下的所有列
		Filter filter = new FamilyFilter(CompareOperator.EQUAL,
			      new SubstringComparator("info1"));

		Scan scan = new Scan();
		scan.setFilter(filter);
		ResultScanner rs = table.getScanner(scan);
		System.out.println("Scanning table user...");
		for (Result result : rs) {
			for (Cell cell : result.rawCells()) {
				System.out.println("Cell: " + cell);
				System.out.println("Value: " +
						Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength()));
			}
			//Scanning table user...
			//Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
			//Value: 22
			//Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
			//Value: tom
			//Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
			//Value: 20
			//Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
			//Value: jack
			//Cell: 3/info1:age/1608553940337/Put/vlen=2/seqid=0
			//Value: 25
			//Cell: 3/info1:name/1608553940305/Put/vlen=4/seqid=0
			//Value: mike
			//Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
			//Value: 30
			//Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
			//Value: marcus

			rs.close();

		}
	}
}
