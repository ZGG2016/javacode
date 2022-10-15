package org.zgg.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.zgg.fastjson.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * JSONObject  JSONArray
 */
public class Test04 {
    public static void main(String[] args) {
//        testJSONObject();
        testJSONArray();
    }
    
    // public class JSONObject extends JSON implements Map<String, Object>,.....
    public static void testJSONObject(){
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("b",2);
        jsonObject1.put("a",1);
        // {"a":1,"b":2}
        System.out.println(jsonObject1);
        System.out.println("-----------------");

        HashMap<String, Object> map = new HashMap<>();
        map.put("b",2);
        map.put("a",1);
        JSONObject jsonObject2 = new JSONObject(map);
        // {"a":1,"b":2}
        System.out.println(jsonObject2);
        System.out.println("-----------------");

        JSONObject jsonObject3 = new JSONObject(true);
        jsonObject3.put("b",2);
        jsonObject3.put("a",1);
        // {"b":2,"a":1}
        System.out.println(jsonObject3);
        System.out.println("-----------------");

        Map javaObject = jsonObject3.toJavaObject(Map.class);
        // {"b":2,"a":1}
        System.out.println(javaObject);
        System.out.println("-----------------");

        User user = new User();
        user.setId(1L);
        user.setName("zhangsan");
        JSONObject jsonObject4 = (JSONObject) JSON.toJSON(user);
        System.out.println(jsonObject4);
        System.out.println("-----------------");

        jsonObject3.put("c",3);
        jsonObject3.fluentPut("d",4);
        // {"b":2,"a":1,"c":3,"d":4}
        System.out.println(jsonObject3);
        // 3
        System.out.println(jsonObject3.get("c"));
    }
    
    // public class JSONArray extends JSON implements List<Object>,.....
    public static void testJSONArray(){
        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.addAll(Arrays.asList("a","b","c"));
        System.out.println(jsonArray1);
        System.out.println("--------------------------");

        ArrayList<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        JSONArray jsonArray2 = new JSONArray(list);
        System.out.println(jsonArray2);
        System.out.println("--------------------------");

        jsonArray1.add("f");
        System.out.println(jsonArray1);
        jsonArray1.add(1,"d");
        System.out.println(jsonArray1);

        JSONArray jsonArray = jsonArray1.fluentAdd(2, "e");
        System.out.println(jsonArray);
    }
}
