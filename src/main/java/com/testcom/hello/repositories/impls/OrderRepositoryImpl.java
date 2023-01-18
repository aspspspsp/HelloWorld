package com.testcom.hello.repositories.impls;

import com.testcom.hello.entities.*;
import com.testcom.hello.repositories.GoodRepository;
import com.testcom.hello.repositories.OrderRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderRepositoryImpl implements OrderRepository {
    @Resource
    private GoodRepository goodRepository;

    // uuid, order
    private Map<String, Order> orderMap = new ConcurrentHashMap<>();

    // userId, orderAmount
    private Map<String, Integer> orderAmountMap = new ConcurrentHashMap<>();

    @Override
    @Synchronized
    public Order addOrder(String userId, String goodId) throws RuntimeException {
        UUID uuid = UUID.randomUUID();

        Good good = goodRepository.findById(goodId);
        if (good == null) {
            throw new RuntimeException("找不到商品");
        }

        String uuidString = uuid.toString();
        if (orderMap.get(uuidString) != null) {
            return this.addOrder(userId, goodId);
        }

        Order order = Order.builder()
                .uuid(uuidString)
                .goodId(goodId)
                .goodName(good.getName())
                .userId(userId)
            .build();

        orderMap.put(uuidString, order);

        if (!orderAmountMap.containsKey(userId)) {
            orderAmountMap.put(userId, 0);
        }

        Integer orderAmount = orderAmountMap.get(userId) + 1;
        orderAmountMap.put(userId, orderAmount);

        return order;
    }

    @Override
    public Order findById(String id) {
        Order order = this.orderMap.get(id);
        if (order == null) {
            throw new RuntimeException("找不到訂單");
        }

        return order;
    }

    @Override
    public OrderContainer findByGoodNamePattern(String goodNamePattern, int pageNum, int pageSize) {
        int total = 0;
        int startFrom = (pageNum - 1) * pageSize;
        int endTo = startFrom + pageSize;
        int i = 0;
        List<Order> result = new ArrayList<>();
        for(String userId : orderMap.keySet()) {

            if (startFrom >= i && endTo <= i) {
                Order order = orderMap.get(userId);
                String goodName = order.getGoodName();
                if(goodName.contains(goodNamePattern)) {
                    result.add(order);
                }
            }

            i += 1;
        }

        return new OrderContainer(result, total);
    }

    @Override
    public OrderContainer findByCreateDateAtRange(Date from, Date to, int pageNum, int pageSize) {
        int total = 0;
        int startFrom = (pageNum - 1) * pageSize;
        int endTo = startFrom + pageSize;
        int i = 0;
        List<Order> result = new ArrayList<>();
        for(String orderId : orderMap.keySet()) {

            if (startFrom >= i && endTo <= i) {
                Order order = orderMap.get(orderId);
                Date createAt = order.getCreateAt();
                if(createAt.before(to) && createAt.after(from)) {
                    result.add(order);
                }
            }

            i += 1;
        }

        return new OrderContainer(result, total);
    }

    @Override
    public List<String> getUserIdsByOrderAmountLargeThan(Integer n) {
        List<String> userIds = new ArrayList<>();
        for(String userId : orderAmountMap.keySet()) {
            Integer orderAmount = orderAmountMap.get(userId);
            if (orderAmount > n) {
                userIds.add(userId);
            }
        }

        return userIds;
    }

}
