package com.yuan.middleware.base.clazz.polymorphic;

/**
 * 多态
 * 方法重写@Override
 * 哪些方法不可以被重写
 * 1、final方法
 * 2、静态方法
 * 3、private等子类中不可见的方法
 * 对象的多态性
 * 1、子类如果重写了父类的方法，通过子类对象调用的一定是子类重写过的代码
 * 2、非静态方法默认的调用对象是this
 * 3、this对象在构造器或者说<init>方法中就是正在创建的对象，所以通过子类实例化时触发的父类加载调用的非静态方法，
 *    调用对象this是子类对象，调用的方法是子类重写的方法。
 *    子类实例化时如果有父类，会触发父类加载 在初始化过程中如果父类方法被子类重写了，会调用子类的方法。
 *    通过父类的引用类型变量指向子类类型对象，访问成员变量时是访问的父类的成员变量。
 * @author yuan
 * @date 2019/11/24
 */
public class C1 {
    public static void main(String[] args) {
        C1 c = new C2();
        //下面这里打印的是c1的成员变量s=AAAA
        System.out.println(c.s);
        /**
         * 输出：null  BBBB  AAAA
         * 分析：
         * 1.初始化c2()时，先初始化c2的父类：c1()
         * 初始化c1()的静态变量和静态代码块
         * 初始化c2()的静态变量和静态代码块
         * 2.初始化c1()的非静态变量，初始化父类的构造函数。
         *   如果父类的构造函数中调用的方法被子类重写，那么调用的会是子类的方法(子类方法中可以使用子类的属性)。 如果有多个子类，调用的是实例化的子类的方法。
         *   父类引用不能单独调用子类属性，只有通过调用被子类重写的方法时 （方法中有子类属性）才可以,不能调用子类自己独有的方法
         * 3.初始化c2()的非静态变量，初始化子类的构造函数。
         * 由于父类第一次调用子类的call方法时，子类还没有初始化非静态变量以及构造方法，因此输出是null。
         *
         * 通过父类的引用类型变量指向子类类型对象，访问成员变量时是访问的父类的成员变量。
         *
         */
    }

    public String s = "AAAA";

    /**
     * 父类构造函数
     */
    public C1() {
        call();
    }

    public void call() {
        System.out.println(s);
    }

}

class C2 extends C1 {
    public String s = "BBBB";

    /**
     * 子类构造函数
     */
    public C2() {
        System.out.println(s);
    }

    /**
     * 重写父类中的call函数
     */
    @Override
    public void call() {
        System.out.println(s);
    }
}
