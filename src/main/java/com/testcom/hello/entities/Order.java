package com.testcom.hello.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Order {
    private String uuid;
    private String goodId;
    private String goodName;
    private String userId;
    private Date createAt;
    private Date updateAt;
}
