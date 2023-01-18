package com.testcom.hello.repositories.impls;

import com.testcom.hello.entities.User;
import com.testcom.hello.entities.UserContainer;
import com.testcom.hello.repositories.UserRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserRepositoryImpl implements UserRepository {

    private Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    @Synchronized
    public void addUser(User user) throws RuntimeException {
        if (user.getUserId() == null) {
            throw new RuntimeException("請輸入帳號");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("請輸密碼");
        }

        User _user = this.userMap.get(user.getUserId());
        if (_user != null) {
            throw new RuntimeException("用戶存在");
        }

        userMap.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String userId) {
        User user = this.userMap.get(userId);
        if (user == null) {
            throw new RuntimeException("找不到用戶");
        }

        return user;
    }

    @Override
    public List<User> findByUserIds(List<String> userIds) {
        List<User> users = new ArrayList<>();
        for(String userId : userIds) {
            User user = this.userMap.get(userId);
            users.add(user);
        }

        return users;
    }

    @Override
    public UserContainer findByEmailPattern(String emailPattern, int pageNum, int pageSize) {
        int total = 0;
        int startFrom = (pageNum - 1) * pageSize;
        int endTo = startFrom + pageSize;
        int i = 0;
        List<User> result = new ArrayList<>();
        for(String userId : userMap.keySet()) {

            if (startFrom >= i && endTo <= i) {
                User user = userMap.get(userId);
                String email = user.getEmail();
                if(email.contains(emailPattern)) {
                    result.add(user);
                }
            }

            i += 1;
        }

        return new UserContainer(result, total);
    }

    @Override
    @Synchronized
    public User updateUser(User user) {
        if (user.getUserId() == null) {
            throw new RuntimeException("請輸入帳號");
        }

        User _user = this.userMap.get(user.getUserId());
        if (_user == null) {
            throw new RuntimeException("找不到用戶");
        }

        _user.setEmail(user.getEmail());
        _user.setPassword(user.getPassword());

        userMap.put(user.getUserId(), _user);

        return _user;
    }
}
