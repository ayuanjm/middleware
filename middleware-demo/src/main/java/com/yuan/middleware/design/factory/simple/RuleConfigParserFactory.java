package com.yuan.middleware.design.factory.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>为了节省内存和对象创建的时间，我们可以将具体 Computer 事先创建好缓存起来。</P>
 * https://www.jianshu.com/p/5cb52d84bd6d
 *
 * @author: YuanJiaMin
 * @date: 2021/2/24 10:33 上午
 */

public class RuleConfigParserFactory {
    private static final Map<String, Computer> cachedParsers = new HashMap<>();

    static {
        cachedParsers.put("hp", new HpComputer());
        cachedParsers.put("lenovo", new LenovoComputer());
    }

    public static Computer createParser(String configFormat) {
        if (configFormat == null || configFormat.isEmpty()) {
            return null;
        }
        Computer parser = cachedParsers.get(configFormat.toLowerCase());
        return parser;
    }

    public static void main(String[] args) {
        RuleConfigParserFactory.createParser("hp").start();
    }
}
