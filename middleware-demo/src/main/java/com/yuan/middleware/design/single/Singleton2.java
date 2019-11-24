package com.yuan.middleware.design.single;

/**
 * 饿汉式
 * <p>
 * 枚举类型：表示该类型的对象是有限的几个
 * 我们可以限定成一个，就成了单例
 * <p>
 * public enum Size{ SMALL, MEDIUM, LARGE, EXTRA_LARGE };
 * 实际上，这个声明定义的类型是一个类，它刚好有四个实例，在此尽量不要构造新对象。
 * https://www.cnblogs.com/liaojie970/p/6474733.html
 *
 * @author yuan
 */
public enum Singleton2 {
    /**
     * 相当于 Singleton2 INSTANCE = new Singleton2()
     * 反编译可以看到这个类的构造函数是私有的: private Singleton2() {}
     */
    INSTANCE
}
