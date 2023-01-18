package com.testcom.hello.entities;

import lombok.Data;

@Data
public class ReqBase {
    public ReqBase(Integer reqCode) {
        this.reqCode = reqCode;
    }

    public ReqBase(Integer reqCode, String msg) {
        this.reqCode = reqCode;
        this.msg = msg;
    }

    private Integer reqCode;
    private String msg;
}
