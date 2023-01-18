package com.testcom.hello.controllers;

import com.testcom.hello.entities.*;
import com.testcom.hello.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController("/order")
public class OrderController {

    @Resource
    private OrderRepository orderRepository;

    /**
     * 添加訂單
     * @param order
     * @return
     */
    @PostMapping("/addOrder")
    public ReqBase addOrder(@RequestBody Order order) {
        try {
            orderRepository.addOrder(order.getUserId(), order.getGoodId());
            return new ReqBase(200);
        } catch (RuntimeException ex) {
            return new ReqBase(500, ex.getMessage());
        }
    }

    /**
     * 找訂單byOrderId
     * @param orderId
     * @return
     */
    @GetMapping("/findOrder")
    public ReqBase findUserById(@RequestParam("id") String orderId) {
        try {
            orderRepository.findById(orderId);
            return new ReqBase(200);
        } catch (RuntimeException ex) {
            return new ReqBase(500, ex.getMessage());
        }
    }

    /**
     * 找訂單by產品name
     * @param goodNamePattern
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/findByGoodName")
    public OrderContainer findByGoodName(@RequestParam("goodNamePattern") String goodNamePattern, @RequestParam("page") Integer page,
                                         @RequestParam("pageNum") Integer pageSize) {
        OrderContainer result = orderRepository.findByGoodNamePattern(goodNamePattern, page, pageSize);
        return result;
    }

    /**
     * 找訂單by購買日期
     * @param fromStr
     * @param toStr
     * @param page
     * @param pageSize
     * @return
     * @throws ParseException
     */
    @GetMapping("/findByCreateAtRange")
    public OrderContainer findByCreateAtRange(@RequestParam("from") String fromStr, @RequestParam("to") String toStr,
                                              @RequestParam("page") Integer page, @RequestParam("pageNum") Integer pageSize) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date from = ft.parse(fromStr);
        Date to = ft.parse(toStr);

        OrderContainer result = orderRepository.findByCreateDateAtRange(from, to, page, pageSize);
        return result;
    }

}
