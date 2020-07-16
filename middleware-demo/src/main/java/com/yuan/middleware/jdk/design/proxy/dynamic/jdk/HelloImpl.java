package com.yuan.middleware.jdk.design.proxy.dynamic.jdk;

/**
 * @author yjm
 * @date 2020/3/20 7:00 下午
 */
public class HelloImpl implements Hello {
    @Override
    public void say(String message) {
        System.out.println(message);
    }
}
