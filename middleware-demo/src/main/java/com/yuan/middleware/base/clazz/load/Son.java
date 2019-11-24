package com.yuan.middleware.base.clazz.load;

/**
 * 一个实例对象的创建包括：类初始化和实例初始化
 * 1. 一个类要创建实例需要先加载并初始化该类,main方法所在的类需要先加载和初始化
 * 2. 一个子类要初始化需要先初始化父类
 * 3. 一个类初始化就是执行<clinit>()方法
 * <clinit>方法由静态变量显示赋值代码和静态代码块组成
 * 类静态变量显示赋值代码和静态代码块代码从上到下顺序执行
 * <clinit>方法只执行一次
 * 4.实例初始化就是执行<init>方法
 * <init>方法由非静态实例变量显示赋值代码和非静态代码块，对应构造器代码组成。
 * 非静态实例变量显示赋值代码和非静态代码块从上到下顺序执行，而对应构造器代码最后执行
 * 每次创建实例对象，调用对应的构造器，执行的就是对应的<init>方法
 * <init>方法的首行是super()或super(实参列表)，即对应父类的<init>方法
 *
 * 子类的初始化<clinit>
 *      1、j=method()
 *      2、父类的静态代码块
 *
 * 先初始化父类:(5),(1)
 * 初始化子类:(10),(6)
 *
 * 子类的实例方法<init>：
 * 1、super()最前，super代表父类初始化，不是创建父类实例，是加载父类
 * 2、i = test()；
 * 3、子类的非静态代码块
 * 4、子类的无参构造(最后)
 *
 * 因为创建了两个Son对象，因此实例方法<init>执行了两次，但是<clinit>方法只会执行一次
 *
 * @author yuan
 * @date 2019/11/23
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    public Son() {
        //super(); 写或不写都存在，在子类构造器中一定会调用父类的构造器
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    @Override
    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 1;
    }

    public static void main(String[] args) throws Exception {
        Son s = new Son();
        System.out.println();
        Son son = new Son();
    }

}
