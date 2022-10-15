package org.zgg.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.zgg.fastjson.bean.Alphabet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 测试 JSONType JSONField 注解
 *
 * JSONField: <a>https://github.com/alibaba/fastjson/wiki/JSONField<a/>
 *
 */
public class Test03 {
    public static void main(String[] args) throws ParseException {
        test();
    }

    public static void test() throws ParseException {
        Alphabet alphabet = new Alphabet();
        alphabet.setB("B");
        alphabet.setA("A");
        alphabet.setT("T");
        alphabet.setE("E");
        alphabet.setD(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-10-01 10:20:30"));

        String jsonString = JSON.toJSONString(alphabet, SerializerFeature.PrettyFormat);
        System.out.println(jsonString);
    }
}
/*
默认在序列化时是按照字段的字母顺序进行序列化
{
	"a":"A",
	"bee":"B",
	"d":"20221001",
	"e":"E",
	"t":"T"
}
要改变这种模式，按照在类中中定义的顺序，
1. 可以在类上使用注解 @JSONType(orders = {"b","a","t","e","d"}),
2. 可以在类的属性上使用注解 @JSONField(ordinal = 1)
{
	"bee":"B",
	"a":"A",
	"t":"T",
	"e":"E",
	"d":"20221001"
}
 */