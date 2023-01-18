package com.testcom.hello.repositories.impls;

import com.testcom.hello.entities.Good;
import com.testcom.hello.repositories.GoodRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GoodRepositoryImpl implements GoodRepository {

     private Map<String, Good> goodMap = new ConcurrentHashMap<>(100);

    /**
     * 添加測試產品
     */
    @PostConstruct
     private void init() {
         String id = "g1";
         Good good = Good.builder()
                 .id(id)
                 .name("測試商品1")
             .build();

         goodMap.put(id, good);
     }

     @Override
     public Good findById(String id) {
        Good good = this.goodMap.get(id);
        if (good == null) {
            throw new RuntimeException("找不到商品");
        }

        return good;
    }
}
