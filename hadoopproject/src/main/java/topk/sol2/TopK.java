package topk.sol2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TopK implements WritableComparable<TopK> {

    private int num;

    public TopK() {}

    public TopK(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(num);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        num = in.readInt();

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TopK))
            return false;
        TopK other = (TopK)o;
        return this.num == other.num;
    }

    @Override
    public int hashCode() {
        return num;
    }


    @Override
    public int compareTo(TopK o) {
        return this.num-o.num>=0 ? -1 :1;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }

}
