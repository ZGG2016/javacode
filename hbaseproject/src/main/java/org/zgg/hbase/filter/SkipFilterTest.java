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

public class SkipFilterTest {

	public static void main(String[] args) throws Exception {

		Configuration conf = HBaseConfiguration.create();
		
		conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
		conf.set("hbase.zookeeper.quorum","zgg");
		conf.set("hbase.zookeeper.property.clientPort","2181");  
		conf.set("zookeeper.znode.parent","/hbase"); 

		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));

		Scan scan = new Scan();
//		scan.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("salary"));

		Filter filter1 = new ValueFilter(CompareOperator.NOT_EQUAL,
				new SubstringComparator("programmer"));

	    scan.setFilter(filter1);

	    ResultScanner scanner1 = table.getScanner(scan);
	   
	    System.out.println("Results of scan #1:");

	    int n = 0;
	    for (Result result : scanner1) {
	      for (Cell cell : result.rawCells()) {
	        System.out.println("Cell: " + cell);
	        n++;
	        //Results of scan #1:
			  //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
			  //Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
			  //Cell: 1/info2:occupation/1608553940091/Put/vlen=7/seqid=0
			  //Cell: 1/info2:salary/1608553940135/Put/vlen=4/seqid=0
			  //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
			  //Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
			  //Cell: 2/info2:occupation/1608553940240/Put/vlen=6/seqid=0
			  //Cell: 2/info2:salary/1608553940272/Put/vlen=4/seqid=0
			  //Cell: 3/info1:age/1608553940337/Put/vlen=2/seqid=0
			  //Cell: 3/info1:name/1608553940305/Put/vlen=4/seqid=0
			  //Cell: 3/info2:salary/1608553940405/Put/vlen=4/seqid=0
			  //Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
			  //Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
			  //Cell: 4/info2:occupation/1608553940504/Put/vlen=7/seqid=0
			  //Cell: 4/info2:salary/1608553940541/Put/vlen=4/seqid=0
			  //Total cell count for scan #1: 15
	      }
	    }
		scanner1.close();

		System.out.println("Total cell count for scan #1: " + n);

		// 封装了一个用户提供的过滤器，如果Cell没通过，会过滤掉整行
	    Filter filter2 = new SkipFilter(filter1);

	    scan.setFilter(filter2); 
	    ResultScanner scanner2 = table.getScanner(scan);

	    n = 0;
	    System.out.println("Results of scan #2:");	   
	    for (Result result : scanner2) {
	      for (Cell cell : result.rawCells()) {
	        System.out.println("Cell: " + cell);
	        n++;
	        //Results of scan #2:
			  //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
			  //Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
			  //Cell: 1/info2:occupation/1608553940091/Put/vlen=7/seqid=0
			  //Cell: 1/info2:salary/1608553940135/Put/vlen=4/seqid=0
			  //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
			  //Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
			  //Cell: 2/info2:occupation/1608553940240/Put/vlen=6/seqid=0
			  //Cell: 2/info2:salary/1608553940272/Put/vlen=4/seqid=0
			  //Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
			  //Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
			  //Cell: 4/info2:occupation/1608553940504/Put/vlen=7/seqid=0
			  //Cell: 4/info2:salary/1608553940541/Put/vlen=4/seqid=0
			  //Total cell count for scan #2: 12
	      }
	    }
	    scanner2.close();	  
	    System.out.println("Total cell count for scan #2: " + n);
	}
}
