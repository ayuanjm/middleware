package com.yuan.middleware.jdk.structure.hashmap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author yuanjm
 * @date 2020/6/27 11:38 下午
 */
public class TreeMapTest {
    public static void main(String[] args) {
        //比较key值，默认从小到大排序
        TreeMap<Object, Object> map = new TreeMap<>();
        map.put(3, 3);
        map.put(2, 5);
        map.put(4, 4);
        for (Object o : map.keySet()) {
            System.out.print(o);
        }
        System.out.println();
        //底层是treeMap，比较key值，默认从小到大排序
        TreeSet<Object> treeSet = new TreeSet<>();
        treeSet.add("a");
        treeSet.add("c");
        treeSet.add("b");
        Iterator<Object> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        System.out.println();
        //底层是包装的hashMap node节点 添加before,after节点，根据put顺序排序
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, 2);
        linkedHashMap.put(5, 2);
        linkedHashMap.put(3, 2);
        for (Object o : linkedHashMap.keySet()) {
            System.out.print(o);
        }
    }

}
