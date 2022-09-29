package customgroup;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;
import secondsort.sol1.NewK2;

/**
 * 问：为什么自定义该类？
 * 答：业务要求分组是按照第一列分组，但是NewK2的比较规则决定了不能按照第一列分。只能自定义分组比较器。
 */
public class MyGroupingComparator implements RawComparator<NewK2> {
    /**
     * @param b1 表示第一个参与比较的字节数组
     * @param s1 表示第一个参与比较的字节数组的起始位置
     * @param l1 表示第一个参与比较的字节数组的偏移量
     *
     * @param b2 表示第二个参与比较的字节数组
     * @param s2 表示第二个参与比较的字节数组的起始位置
     * @param l2 表示第二个参与比较的字节数组的偏移量
     */
    @Override
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        return WritableComparator.compareBytes(b1,s1,8,b2,s2,8);
    }

    @Override
    public int compare(NewK2 o1, NewK2 o2) {
        return (int) (o1.getFirst() - o2.getFirst());
    }
}
