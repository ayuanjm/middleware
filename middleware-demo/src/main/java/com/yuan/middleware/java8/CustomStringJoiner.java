package com.yuan.middleware.java8;

import java.util.StringJoiner;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>StringJoiner</P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/23 2:30 下午
 */
public class CustomStringJoiner {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(",", "{", "}");
        stringJoiner.add("a");
        stringJoiner.add("b");
        stringJoiner.merge(stringJoiner);
        System.out.println(stringJoiner.length());
        System.out.println(stringJoiner.toString());
    }
}
