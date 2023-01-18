package com.testcom.hello.repositories;

import com.testcom.hello.entities.Good;

public interface GoodRepository {
    Good findById(String id);
}
