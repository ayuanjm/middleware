package com.yuan.middleware.demo.controller;

import com.yuan.middleware.demo.service.TestAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuan
 */
@Slf4j
@RestController
public class AsyncController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TestAsync testAsync;

    @GetMapping("/async")
    public String async() {
        testAsync.show();
        log.info("异步测试开始。。。");
        testAsync.info();
        log.info("异步测试结束。。。");

        return "success";
    }
}
