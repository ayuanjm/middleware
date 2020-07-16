package com.yuan.middleware.jdk.structure.hashmap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Hashcode implements Comparator {
    private int a;

    public Hashcode(int a) {
        this.a = a;
    }

    @Override
    public int hashCode() {
        return a % 10;
    }

    public static void main(String[] args) {
        List list =new LinkedList();
        list.get(1);
    }

    static void hash() {
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 1;
    }

}
