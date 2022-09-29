package topk.sol1;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *  实现WritableComparable接口，定义一个新key
 */
public class TopK implements WritableComparable<TopK> {

    private String url;
    private Integer count;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public TopK() { }

    public TopK(String url, Integer count) {
        this.url = url;
        this.count = count;
    }

    @Override
    public int hashCode() {
        return this.url.hashCode()+this.count.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TopK)){
            return false;
        }
        TopK objj =  (TopK)obj;
        return this.url.equals(objj.url) && this.count.equals(objj.count);
    }


    @Override
    public int compareTo(TopK o) {
        //直接根据计数值降序排列
        return this.count-o.count>=0 ? -1 :1;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        //要注意类型
        out.writeUTF(this.url);
        out.writeInt(this.count);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.url = in.readUTF();
        this.count = in.readInt();
    }
}
