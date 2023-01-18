package com.testcom.hello.repositories;


import com.testcom.hello.entities.User;
import com.testcom.hello.entities.UserContainer;

import java.util.List;

public interface UserRepository {
    void addUser(User user);
    User findByUserId(String userId);
    List<User> findByUserIds(List<String> userIds);
    UserContainer findByEmailPattern(String emailPattern, int pageNum, int pageSize);
    User updateUser(User user);
}
