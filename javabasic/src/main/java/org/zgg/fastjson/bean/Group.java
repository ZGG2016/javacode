package org.zgg.fastjson.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private Long       id;
    private String     name;
    private List<User> users = new ArrayList<User>();

    public void addUser(User user) {
        users.add(user);
    }
}
