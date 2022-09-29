package customsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

//自定义的Mapper端的排序比较类
public class TextIntComparator extends WritableComparator {
    public TextIntComparator(Class<? extends WritableComparable> keyClass) {
        super(keyClass);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TextInt o1 = (TextInt) a;
        TextInt o2 = (TextInt) b;

        if(! o1.getStr().equals(o2.getStr()))
            return o1.getStr().compareTo(o2.getStr());
        else
            return o1.getValue() - o2.getValue();
    }
}
