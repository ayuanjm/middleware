package com.yuan.middleware.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于redis的秒杀
 *
 * @author yuan
 * @date 2019/12/17
 */
@Slf4j
@RestController
@RequestMapping("/myFilter")
public class SeckillController {

    @GetMapping("/show")
    public Object show(){
        log.info("show ..............");
        return null;
    }
}
