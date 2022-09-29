package org.zgg.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class FilterListTest {
    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.rootdir", "hdfs://zgg:9000/user/hbase");
        conf.set("hbase.zookeeper.quorum","zgg");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("user"));

        //多个过滤器的列表，
        // 这些过滤器两两间具有`FilterList.Operator.MUST_PASS_ALL`或`FilterList.Operator.MUST_PASS_ONE`关系。

        //工资在[1000，5000]范围内的
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
                Bytes.toBytes("info2"),
                Bytes.toBytes("salary"),
                CompareOperator.LESS_OR_EQUAL,
                Bytes.toBytes("5000")
        );
        list.addFilter(filter1);
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                Bytes.toBytes("info2"),
                Bytes.toBytes("salary"),
                CompareOperator.GREATER_OR_EQUAL,
                Bytes.toBytes("1000")
        );
        list.addFilter(filter2);

        Scan scan = new Scan();
        scan.setFilter(list);
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
            }
        }

        scanner.close();
    }
}
