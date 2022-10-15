package org.zgg.fastjson.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.util.Date;

//@JSONType(alphabetic = false)
//@JSONType(orders = {"bee","a","t","e","d"})
public class Alphabet {
    @JSONField(ordinal = 1, name = "bee") // 重新字段名字
    private String b;
    @JSONField(ordinal = 2)
    private String a;
    @JSONField(ordinal = 3)
    private String t;
    @JSONField(ordinal = 4)
    private String e;
    @JSONField(ordinal = 5, format = "yyyyMMdd") // 配置日期格式
    private Date d;

    public Alphabet() {
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Alphabet{" +
                "b='" + b + '\'' +
                ", a='" + a + '\'' +
                ", t='" + t + '\'' +
                ", e='" + e + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}
