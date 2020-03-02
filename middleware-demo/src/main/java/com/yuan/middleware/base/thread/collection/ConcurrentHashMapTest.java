package com.yuan.middleware.base.thread.collection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuan
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        map.put("a",1);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(1,1);
    }
}
