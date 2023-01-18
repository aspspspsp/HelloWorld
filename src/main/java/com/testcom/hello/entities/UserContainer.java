package com.testcom.hello.entities;

import lombok.Data;

import java.util.List;

@Data
public class UserContainer {
    public UserContainer(List<User> users, Integer totalCount) {
        this.users = users;
        this.totalCount = totalCount;
    }

    private List<User> users;
    private Integer totalCount;
}
