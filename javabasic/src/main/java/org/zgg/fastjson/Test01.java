package org.zgg.fastjson;

import com.alibaba.fastjson.JSON;
import org.zgg.fastjson.bean.Group;
import org.zgg.fastjson.bean.User;

import java.util.List;

/**
 * 初步简单使用
 * 更多：
 *     <a>https://github.com/alibaba/fastjson</a>
 *     <a>http://i.kimmking.cn/2017/06/06/json-best-practice/</a>
 */
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

        // {"id":0,"name":"admin","users":[{"id":1,"name":"zhangsan"},{"id":2,"name":"lisi"}]}
        String jsonString = JSON.toJSONString(group);
        System.out.println(jsonString);
        System.out.println("--------------------------------");

        // 能展示出来层次结构
        String jsonString2 = JSON.toJSONString(group, true);
        System.out.println(jsonString2);
    }

    public static void decodeTest(){
        String jsonString1 = "{\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":1,\"name\":\"zhangsan\"},{\"id\":2,\"name\":\"lisi\"}]}";
        Group group1 = JSON.parseObject(jsonString1, Group.class);
        // Group(id=0, name=admin, users=[User(id=1, name=zhangsan), User(id=2, name=lisi)])
        System.out.println(group1);
        System.out.println("--------------------------------");

        // 解析json数组
        String jsonString2 = "[{\"id\":1,\"name\":\"admin1\",\"users\":[{\"id\":1,\"name\":\"zhangsan\"},{\"id\":2,\"name\":\"lisi\"}]}," +
                "{\"id\":2,\"name\":\"admin2\",\"users\":[{\"id\":1,\"name\":\"tom\"},{\"id\":2,\"name\":\"mike\"}]}]";
        List<Group> group2 = JSON.parseArray(jsonString2, Group.class);
        group2.forEach(System.out::println);

    }

}
