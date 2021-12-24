package com.huangbaba;

import com.huangbaba.pojo.Users;
import com.huangbaba.service.RecordService;
import com.huangbaba.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
class RuangonApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @Test
    void contextLoads() {
        System.out.println(recordService.selectRecord("汤智文"));
    }
}
