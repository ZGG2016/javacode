package org.zgg.hbase.comparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

public class BinaryComponentComparatorTest {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("user"));

        Scan scan = new Scan();
        // 比较在指定位置的指定值与单元格中的指定值
        byte[] partialValue = Bytes.toBytes(1);
        int partialValueOffset = 1;

        Filter filter = new RowFilter(CompareOperator.EQUAL,
                new BinaryComponentComparator(partialValue,partialValueOffset));

        scan.setFilter(filter);

        ResultScanner scanner = table.getScanner(scan);
        System.out.println("Results of scan:");
        for (Result result : scanner) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell);
                System.out.println("Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
            }
        }

        scanner.close();
    }
}
//https://issues.apache.org/jira/browse/HBASE-22969
