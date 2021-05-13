package com.yuan.middleware.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * kill -15 pid 会对运行的服务中的线程调用interrupt方法
 * 简单main方法和 springboot junit 测试不出kill效果，需要正常启动spring服务
 * LockSupport响应中断不抛出异常，Thread.interrupted()为true
 * sleep抛出中断异常
 * https://mp.weixin.qq.com/s/LrRyGVRR_9NXeaHXM3rd9g
 *
 * @author yuan
 * @date 2021/05/13
 */
@Slf4j
@RestController
@RequestMapping("/kill")
public class KillController {

    @GetMapping("/show")
    public void show() {
        System.out.println(1);
//        try {
//            TimeUnit.SECONDS.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LockSupport.park();
        System.out.println(Thread.interrupted());
        System.out.println(3);
    }
}
