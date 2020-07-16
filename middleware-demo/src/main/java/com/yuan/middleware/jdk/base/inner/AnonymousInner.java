package com.yuan.middleware.jdk.base.inner;

/**
 * 匿名内部类
 * 匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，
 * 大部分匿名内部类用于接口回调。匿名内部类在编译的时候由系统自动起名为Outter$1.class。
 * 一般来说，匿名内部类用于继承其他类或是实现接口
 *
 * @author yuan
 * @date 2019/11/23
 */
public interface AnonymousInner {
    void show();
}

class Test {
    public static void main(String[] args) {
        /**
         * 匿名内部类，直接实现接口
         */
        new AnonymousInner() {
            @Override
            public void show() {

            }
        };
    }
}
