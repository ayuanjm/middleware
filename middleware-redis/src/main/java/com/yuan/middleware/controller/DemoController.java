package com.yuan.middleware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author yuan
 */
@RestController
public class DemoController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/put")
    public String redisSet() {
        int i = (int) (Math.random() * 100);
        redisTemplate.opsForValue().set("key" + i, "value" + i, 300, TimeUnit.SECONDS);
        return "success " + "key" + i;
    }

}
