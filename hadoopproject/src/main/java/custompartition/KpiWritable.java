package custompartition;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class KpiWritable implements WritableComparable<KpiWritable> {

    private long upPackNum;
    private long downPackNum;
    private long upPayLoad;
    private long downPayLoad;


    public KpiWritable() {
    }


    public KpiWritable(String upPackNum, String downPackNum, String upPayLoad, String downPayLoad){
        this.upPackNum = Long.parseLong(upPackNum);
        this.downPackNum = Long.parseLong(downPackNum);
        this.upPayLoad = Long.parseLong(upPayLoad);
        this.downPayLoad = Long.parseLong(downPayLoad);
    }

    public long getUpPackNum() {
        return upPackNum;
    }

    public void setUpPackNum(long upPackNum) {
        this.upPackNum = upPackNum;
    }

    public long getDownPackNum() {
        return downPackNum;
    }

    public void setDownPackNum(long downPackNum) {
        this.downPackNum = downPackNum;
    }

    public long getUpPayLoad() {
        return upPayLoad;
    }

    public void setUpPayLoad(long upPayLoad) {
        this.upPayLoad = upPayLoad;
    }

    public long getDownPayLoad() {
        return downPayLoad;
    }

    public void setDownPayLoad(long downPayLoad) {
        this.downPayLoad = downPayLoad;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upPackNum);
        out.writeLong(downPackNum);
        out.writeLong(upPayLoad);
        out.writeLong(downPayLoad);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.upPackNum = in.readLong();
        this.downPackNum = in.readLong();
        this.upPayLoad = in.readLong();
        this.downPayLoad = in.readLong();
    }

    @Override
    public String toString() {
        return "KpiWritable{" +
                "upPackNum=" + upPackNum +
                ", downPackNum=" + downPackNum +
                ", upPayLoad=" + upPayLoad +
                ", downPayLoad=" + downPayLoad +
                '}';
    }

    @Override
    public int compareTo(KpiWritable o) {
        return (int) (this.upPackNum-o.upPackNum);
    }
}
