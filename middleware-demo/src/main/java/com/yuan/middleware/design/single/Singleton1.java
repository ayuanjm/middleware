package com.yuan.middleware.design.single;

import java.util.Date;

/**
 * 饿汉式 在类初始化时直接创建对象，不存在线程安全问题，不管你是否需要这个对象都会创建
 * <p>
 * 1. 构造器私有化
 * 2. 自行创建，并且用静态变量保存
 * 3. 向外提供这个实例
 * 4. 强调这是一个单例，我们可以用final修饰，不能改变引用，可以改变属性。
 *
 * @author yuan
 */
public class Singleton1 {
    public static final Singleton1 INSTANCE = new Singleton1();

    private static Date date = new Date();

    private Singleton1() {

    }
}
