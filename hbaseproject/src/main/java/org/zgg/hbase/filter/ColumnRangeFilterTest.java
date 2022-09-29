package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

public class ColumnRangeFilterTest {
    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("user"));

        //取的开始、结束行
        byte[] startRow = Bytes.toBytes("1");
        byte[] stopRow = Bytes.toBytes("2");

        //取的一个列族、和开始、结束列
        byte[] family = Bytes.toBytes("info1");
        byte[] startColumn = Bytes.toBytes("age");
        byte[] stopColumn = Bytes.toBytes("name");

        // (optional) 限制为第一行和第二行
        Scan scan = new Scan().withStartRow(startRow,true).withStopRow(stopRow,true);
        // (optional) 限制为一个列族info1
        scan.addFamily(family);

        // 选择那些在 minColumn 到 maxColumn 之间的列的所在 key。【参考结果来理解】
        Filter f = new ColumnRangeFilter(startColumn, true, stopColumn, true);
        scan.setFilter(f);

//        scan.setBatch(10); // set this if there could be many columns returned
        ResultScanner rs = table.getScanner(scan);
        System.out.println("Scanning table user...");

        for (Result result : rs) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell);
                System.out.println("Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
                //Value: 22
                //Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
                //Value: tom
                //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
                //Value: 20
                //Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
                //Value: jack
            }
        }
        rs.close();

    }
}
