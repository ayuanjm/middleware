package com.yuan.middleware.ans;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuanjm
 * @date 2020/8/14 10:33 下午
 */
public class Ans06 extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public Ans06(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public Integer put(int key, int value) {
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        Ans06 ans06 = new Ans06(2);
        ans06.put(1, 2);
        ans06.put(2, 2);
        ans06.put(3, 2);
        System.out.println(ans06.get(1));
    }
}
