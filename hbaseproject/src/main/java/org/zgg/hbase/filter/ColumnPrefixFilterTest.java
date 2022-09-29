package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

public class ColumnPrefixFilterTest {
    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("user"));


        //取前缀为 n 的列名
        byte[] prefix = Bytes.toBytes("n");

        Scan scan = new Scan();

        // 根据前缀来匹配(age,name,occupation,salary)这些列名
        Filter f = new ColumnPrefixFilter(prefix);
        scan.setFilter(f);

        ResultScanner rs = table.getScanner(scan);
        System.out.println("Scanning table user...");

        for (Result result : rs) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell);
                System.out.println("Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                //Scanning table user...
                //Cell: 1/info1:name/1608553940007/Put/vlen=3/seqid=0
                //Value: tom
                //Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
                //Value: jack
                //Cell: 3/info1:name/1608553940305/Put/vlen=4/seqid=0
                //Value: mike
                //Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
                //Value: marcus
            }
        }
        rs.close();

    }
}
