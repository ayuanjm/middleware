package com.yuan.spring;

import java.util.HashMap;

/**
 * @author yjm
 * @date 2020/4/27 5:02 下午
 */
public class Demo {
    public static void main(String[] args) {
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        HashMap<Object, Object> map = new HashMap<>();
        objectThreadLocal.set(map);
        System.out.println(objectThreadLocal.get());
        map.put(1,1);
        System.out.println(objectThreadLocal.get());
    }
}
