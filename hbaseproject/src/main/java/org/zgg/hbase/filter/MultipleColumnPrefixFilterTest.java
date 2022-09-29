package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class MultipleColumnPrefixFilterTest {
    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("user"));


        //取前缀为 a 和 o 的列名
        byte[][] prefixes = new byte[][] {Bytes.toBytes("a"), Bytes.toBytes("o")};

        Scan scan = new Scan();

        // 根据多个前缀来匹配(age,name,occupation,salary)这些列名
        Filter f = new MultipleColumnPrefixFilter(prefixes);
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
                //Cell: 1/info1:age/1608553940055/Put/vlen=2/seqid=0
                //Value: 22
                //Cell: 1/info2:occupation/1608553940091/Put/vlen=7/seqid=0
                //Value: teacher
                //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
                //Value: 20
                //Cell: 2/info2:occupation/1608553940240/Put/vlen=6/seqid=0
                //Value: police
                //Cell: 3/info1:age/1608553940337/Put/vlen=2/seqid=0
                //Value: 25
                //Cell: 3/info2:occupation/1608553940369/Put/vlen=10/seqid=0
                //Value: programmer
                //Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
                //Value: 30
                //Cell: 4/info2:occupation/1608553940504/Put/vlen=7/seqid=0
                //Value: sporter
            }
        }
        rs.close();

    }
}
