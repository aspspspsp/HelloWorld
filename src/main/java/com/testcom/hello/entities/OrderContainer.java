package com.testcom.hello.entities;

import lombok.Data;

import java.util.List;

@Data
public class OrderContainer {
    public OrderContainer(List<Order> orders, Integer totalCount) {
        this.orders = orders;
        this.totalCount = totalCount;
    }

    private List<Order> orders;
    private Integer totalCount;
}
