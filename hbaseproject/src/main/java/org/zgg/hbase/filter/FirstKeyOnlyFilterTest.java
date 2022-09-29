package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class FirstKeyOnlyFilterTest {
    public static void main(String[] args) throws Exception {
//		BasicConfigurator.configure();

        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum", "zgg");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("user"));

        Scan scan = new Scan();

        // 仅会返回每行的第一个kv
        // 用作统计行数
        Filter filter = new FirstKeyOnlyFilter();

        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        System.out.println("Scanning table user...");

        int i=0;
        for (Result result : scanner) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell);
                System.out.println("Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
                //Value: 22
                //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
                //Value: 20
                //Cell: 3/info1:age/1608553940337/Put/vlen=2/seqid=0
                //Value: 25
                //Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
                //Value: 30
                i++;
            }
        }

        scanner.close();
        System.out.println(i);  // 4

    }
}
