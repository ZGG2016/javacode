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
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class QualifierFilterTest {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		//根据列名来取
		Filter filter = new QualifierFilter(CompareOperator.EQUAL,
				new SubstringComparator("salary"));

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
			//Cell: 1/info2:salary/1608553940135/Put/vlen=4/seqid=0
			//Value: 2000
			//Cell: 2/info2:salary/1608553940272/Put/vlen=4/seqid=0
			//Value: 2500
			//Cell: 3/info2:salary/1608553940405/Put/vlen=4/seqid=0
			//Value: 5000
			//Cell: 4/info2:salary/1608553940541/Put/vlen=4/seqid=0
			//Value: 6000

			rs.close();

		}
	}

}
