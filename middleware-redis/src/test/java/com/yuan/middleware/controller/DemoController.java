package com.yuan.middleware.controller;

import com.yuan.middleware.service.TestAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author yuan
 */
@Slf4j
@RestController
public class DemoController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TestAsync testAsync;

    @GetMapping("/put")
    public String redisSet() {
        int i = (int) (Math.random() * 100);
        stringRedisTemplate.opsForValue().set("key" + i, "value" + i, 300, TimeUnit.SECONDS);
        return "success " + "key" + i;
    }

    @GetMapping("/async")
    public String async() {
        testAsync.show();
        log.info("异步测试开始。。。");
        testAsync.info();
        log.info("异步测试结束。。。");

        return "success";
    }
}
