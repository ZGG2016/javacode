package secondsort.sol1;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 问：为什么实现该类？
 * 答：因为原来的v2不能参与排序，把原来的k2和v2封装到一个类中，作为新的k2
 *
 */
public class NewK2 implements WritableComparable<NewK2> {

    private Long first;
    private Long second;

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public NewK2(){}

    public NewK2(long first,long second){
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return this.first.hashCode() + this.second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof NewK2)){
            return false;
        }
        NewK2 k2 = (NewK2)obj;
        return (this.first.equals(k2.first)) && (this.second.equals(k2.second));
    }
    /**
     * 当k2进行排序时，会调用该方法.
     * 当第一列不同时，升序；当第一列相同时，第二列升序
     */
    @Override
    public int compareTo(NewK2 o) {
        final int minus = (int) (this.first - o.first);
        if(minus != 0){
            return minus;
        }
        return (int) (this.second - o.second);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(this.first);
        out.writeLong(this.second);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.first = in.readLong();
        this.second = in.readLong();
    }
}
