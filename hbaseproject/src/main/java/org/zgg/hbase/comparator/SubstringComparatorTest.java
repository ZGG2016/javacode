package org.zgg.hbase.comparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class SubstringComparatorTest {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("user"));

        // 确定一个给定的子串是否在一个值中。比较不区分大小写。
        // occupation 中包含 p 的
        SubstringComparator comp = new SubstringComparator("p");
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("info2"),
                Bytes.toBytes("occupation"),
                CompareOperator.EQUAL,
                comp);

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
                //Cell: 2/info1:age/1608553940207/Put/vlen=2/seqid=0
                //Value: 20
                //Cell: 2/info1:name/1608553940173/Put/vlen=4/seqid=0
                //Value: jack
                //Cell: 2/info2:occupation/1608553940240/Put/vlen=6/seqid=0
                //Value: police
                //Cell: 2/info2:salary/1608553940272/Put/vlen=4/seqid=0
                //Value: 2500
                //Cell: 3/info1:age/1608553940337/Put/vlen=2/seqid=0
                //Value: 25
                //Cell: 3/info1:name/1608553940305/Put/vlen=4/seqid=0
                //Value: mike
                //Cell: 3/info2:occupation/1608553940369/Put/vlen=10/seqid=0
                //Value: programmer
                //Cell: 3/info2:salary/1608553940405/Put/vlen=4/seqid=0
                //Value: 5000
                //Cell: 4/info1:age/1608553940470/Put/vlen=2/seqid=0
                //Value: 30
                //Cell: 4/info1:name/1608553940436/Put/vlen=6/seqid=0
                //Value: marcus
                //Cell: 4/info2:occupation/1608553940504/Put/vlen=7/seqid=0
                //Value: sporter
                //Cell: 4/info2:salary/1608553940541/Put/vlen=4/seqid=0
                //Value: 6000
            }
        }

        scanner.close();
    }
}
