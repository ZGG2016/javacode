package org.zgg.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.zgg.fastjson.bean.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 测试 SerializerFeature 和 Feature
 */
public class Test02 {
    public static void main(String[] args) throws ParseException {
//        test1();
        test2();
    }

    private static void test2() {
        String jsonString = "{\n" +
                "\t\"bee\":\"B\",\n" +
                "\t\"\":\"A\",\n" +
                "\t\"t\":\"T\",\n" +
                "\t\"e\":\"E\",\n" +
                "\t\"d\":\"20221001\"\n" +
                "}";
        JSONObject object = JSON.parseObject(jsonString,
                                                Feature.AllowSingleQuotes,
                                                Feature.NonStringKeyAsString,
                                                Feature.OrderedField  // 按指定原始顺序排序
                                            );
        // {"bee":"B","":"A","t":"T","e":"E","d":"20221001"}
        System.out.println(object);

    }

    public static void test1() throws ParseException {
        Product product = new Product();
        product.setPid(1L);
        product.setPname("手机");
        product.setPbrand(null);
        product.setPcolor("b/lack");
        product.setPdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-10-01 10:20:30"));
        product.setPattrs(null);

        String s1 = JSON.toJSONString(product,
                                        SerializerFeature.UseSingleQuotes,
                                        SerializerFeature.WriteNullListAsEmpty,
                                        SerializerFeature.WriteNullStringAsEmpty,
                                        SerializerFeature.WriteSlashAsSpecial,
                                        SerializerFeature.WriteDateUseDateFormat,
                                        SerializerFeature.PrettyFormat
                                     );
        System.out.println(s1);

        /*
        {
            'pattrs':[],
            'pbrand':'',
            'pcolor':'b\/lack',
            'pdate':'2022-10-01 10:20:30',
            'pid':1,
            'pname':'手机'
        }
         */

    }
}
