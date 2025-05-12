package com.journalingN.journaling.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void mailTest(){
        redisTemplate.opsForValue().set("email","chandrakarnehil@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
    }
}
