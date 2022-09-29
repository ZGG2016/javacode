package customsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

//自定义的Reducer端的排序比较类
public class TextComparator extends WritableComparator {
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TextInt o1 = (TextInt) a;
        TextInt o2 = (TextInt) b;
        return o1.getStr().compareTo(o2.getStr());
    }
}
