package com.yuan.middleware;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Copyright(c) 2018 SunYur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/23 2:12 下午
 */
public class Demo {
    public static void main(String[] args) {
        String[] words = {"mass", "as", "hero", "superhero"};
        Arrays.sort(words, Comparator.comparingInt(String::length));
        System.out.println(JSON.toJSONString(words));
        int indexOf = words[0].indexOf("a");
        System.out.println(indexOf);
    }
}

