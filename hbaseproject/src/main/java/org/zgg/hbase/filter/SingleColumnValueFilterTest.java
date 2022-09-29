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
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class SingleColumnValueFilterTest {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		// 对单个列的值进行二元比较的构造函数。
		// 如果找到该列并且条件通过，则提交该行的所有列。如果条件失败，则不会发出提交。
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
			      Bytes.toBytes("info2"),
			      Bytes.toBytes("salary"),
				  CompareOperator.EQUAL,
			      new SubstringComparator("2000"));

		//判断如果没有找到对应的列，是否过滤掉整行
	    filter.setFilterIfMissing(true);

	    Scan scan = new Scan();
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
				//Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
				//Value: 22
				//Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
				//Value: tom
				//Cell: 1/info2:occupation/1608553940091/Put/vlen=7/seqid=0
				//Value: teacher
				//Cell: 1/info2:salary/1608553940135/Put/vlen=4/seqid=0
				//Value: 2000
	        }
	    }
	    
	    scanner.close();

	}

}
