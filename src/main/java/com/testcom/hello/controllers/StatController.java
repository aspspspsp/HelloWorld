package com.testcom.hello.controllers;

import com.testcom.hello.entities.User;
import com.testcom.hello.repositories.OrderRepository;
import com.testcom.hello.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("/stat")
public class StatController {
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    /**
     * 找訂單數大於>n
     * @param n
     * @return
     */
    @GetMapping("/getUserByOrderAmountLargeThan")
    public List<User> getUserByOrderAmountLargeThan(@RequestParam("n") Integer n) {
        List<String> userIds = orderRepository.getUserIdsByOrderAmountLargeThan(n);

        List<User> users = userRepository.findByUserIds(userIds);

        return users;
    }
}
