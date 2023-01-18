package com.testcom.hello.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Good {
    private String id;
    private String name;
}
