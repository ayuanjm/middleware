package com.yuan.middleware.jdk.base.clazz.load;

/**
 * 父类的初始化<clinit>
 *     1、j=method()
 *     2、父类的静态代码块
 *
 * 父类的实例方法：
 * 1、super()最前
 * 2、i = test()；
 * 3、父类的非静态代码块
 * 4、父类的无参构造(最后)
 *
 *
 * 非静态方法前面其实有一个默认的对象this
 * this在构造器或者<init>，它表示的是正在创建的对象，因为这里是在创建Son对象，所以
 * test()执行的是子类重写的代码(面向对象多态)
 * 这里 i=test()执行的是子类重写的test()方法
 * @author yuan
 * @date 2019/11/23
 */
public class Father {
    /**
     * i=test()执行的是子类重写的test,因为是子类实例化触发的父类加载
     */
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(1)");
    }

    public Father() {
        System.out.print("(2)");
    }

    {
        System.out.print("(3)");
    }

    public int test() {
        System.out.print("(4)");
        return 1;
    }

    public static int method(){
        System.out.print("(5)");
        return 1;
    }

    public static void main(String[] args) throws Exception {
        new Father().test();
    }
}
