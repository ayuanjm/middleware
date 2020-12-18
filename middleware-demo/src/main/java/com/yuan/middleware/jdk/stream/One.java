package com.yuan.middleware.jdk.stream;

import com.yuan.middleware.jdk.base.stream.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/11/25 2:11 下午
 * Description:
 */
public class One {
    public static void main(String[] args) {
        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 22);
        User u4 = new User(14, "e", 28);
        User u5 = new User(16, "d", 26);
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
        b(list);

    }

    /**
     * @param list:
     * @return
     * @Description: 筛选后转map
     * @author yuanjiamin
     * @date 2020/11/25 3:29 下午
     */
    private static void c(List<User> list) {
        Map<String, Integer> collect = list.stream().filter(user -> user.getAge() > 25).
                // (existing, replacement) -> existing)) 当key值重复时，使用新的key替换旧的 (a,b)-> b,也就是当放入b时发现和a重复，用b替换a
                        collect(Collectors.toMap(User::getUserName, User::getAge, (a, b) -> b));
        System.out.println(collect);
    }

    /**
     * @param list:
     * @return
     * @Description: 排序输出
     * @author yuanjiamin
     * @date 2020/11/25 3:22 下午
     */
    private static void b(List<User> list) {
        list.stream().forEach(user -> System.out.print(user.getAge() + " "));
        System.out.println();
        list.stream().sorted(Comparator.comparing(User::getAge)).forEach(user -> System.out.print(user.getAge() + " "));
        List<User> collect = list.stream().sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());
        System.out.println();
        collect.stream().forEach(user -> System.out.print(user.getAge() + " "));
        // .map可以将一个流的元素按照一定的映射规则映射到另一个流中
        List<Integer> integerList = collect.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(integerList);
    }

    /**
     * @return
     * @Description: 遍历输出
     * @author yuanjiamin
     * @date 2020/11/25 3:21 下午
     */
    private static void a() {
        List<String> list = Arrays.asList("a", "b", "c");
        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
        stream.forEach(System.out::print);
        System.out.println();
        //乱序输出，多线程并行执行
        parallelStream.forEach(System.out::print);
    }
}
