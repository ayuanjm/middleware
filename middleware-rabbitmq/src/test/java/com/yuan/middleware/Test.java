package com.yuan.middleware;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class Test {

    public static void main(String[] args) {
        SortedSet<Integer> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());
        confirmSet.add(1);
        confirmSet.add(22);
        confirmSet.add(10);
        confirmSet.add(6);
        confirmSet.remove(10);
        for (Integer integer : confirmSet) {
            System.out.println(integer);
        }

    }
}
