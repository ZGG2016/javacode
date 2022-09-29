package customsort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TextInt implements WritableComparable<TextInt> {

    private String str;
    private int value;

    public TextInt() {}

    public TextInt(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(TextInt o) {
        return o.getStr().compareTo(this.getStr());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(str); //字符串
        out.writeInt(value);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        str = in.readUTF(); //字符串
        value = in.readInt();
    }
}
