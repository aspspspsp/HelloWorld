package com.testcom.hello.controllers;

import com.testcom.hello.entities.ReqBase;
import com.testcom.hello.entities.User;
import com.testcom.hello.entities.UserContainer;
import com.testcom.hello.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("/user")
public class UserController {

    @Resource
    private UserRepository userRepository;

    /**
     * 創建user
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public ReqBase addUser(@RequestBody User user) {
        try {
            userRepository.addUser(user);
            return new ReqBase(200);
        } catch (RuntimeException ex) {
            return new ReqBase(500, ex.getMessage());
        }
    }

    /**
     * 按照id找user
     * @param userId
     * @return
     */
    @GetMapping("/findUser")
    public ReqBase findUserById(@RequestParam("id") String userId) {
        try {
            userRepository.findByUserId(userId);
            return new ReqBase(200);
        } catch (RuntimeException ex) {
            return new ReqBase(500, ex.getMessage());
        }
    }

    /**
     * 按照email找user
     * @param email
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/findByEmail")
    public UserContainer findUserByEmail(@RequestParam("email") String email, @RequestParam("page") Integer page,
                                         @RequestParam("pageNum") Integer pageSize) {
           UserContainer result = userRepository.findByEmailPattern(email, page, pageSize);
           return result;
    }

    /**
     * 更新user
     * @param user
     * @return
     */
    @PostMapping("/update")
    public ReqBase updateUser(@RequestBody User user) {
        try {
            userRepository.updateUser(user);
            return new ReqBase(200);
        } catch (RuntimeException ex) {
            return new ReqBase(500, ex.getMessage());
        }
    }
}
