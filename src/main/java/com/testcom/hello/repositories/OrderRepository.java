package com.testcom.hello.repositories;


import com.testcom.hello.entities.Order;
import com.testcom.hello.entities.OrderContainer;

import java.util.Date;
import java.util.List;

public interface OrderRepository {
    Order addOrder(String userId, String goodId);
    Order findById(String id);
    OrderContainer findByGoodNamePattern(String goodNamePattern, int pageNum, int pageSize);
    OrderContainer findByCreateDateAtRange(Date from, Date to, int pageNum, int pageSize);
    List<String> getUserIdsByOrderAmountLargeThan(Integer n);
}
