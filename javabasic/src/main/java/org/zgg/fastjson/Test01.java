package org.zgg.fastjson;

import com.alibaba.fastjson.JSON;
import org.zgg.fastjson.bean.Group;
import org.zgg.fastjson.bean.User;
// 初步简单使用
public class Test01 {
    public static void main(String[] args) {
        encodeTest();
        decodeTest();
    }

    public static void encodeTest(){
        User user1 = new User();
        user1.setId(1L);
        user1.setName("zhangsan");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("lisi");

        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        group.addUser(user1);
        group.addUser(user2);

        String jsonString = JSON.toJSONString(group);
        System.out.println(jsonString);
    }

    public static void decodeTest(){
        String jsonString = "{\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":1,\"name\":\"zhangsan\"},{\"id\":2,\"name\":\"lisi\"}]}";
        Group group = JSON.parseObject(jsonString, Group.class);
        System.out.println(group);
    }

}
