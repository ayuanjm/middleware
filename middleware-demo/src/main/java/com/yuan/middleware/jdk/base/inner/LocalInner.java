package com.yuan.middleware.jdk.base.inner;

/**
 * 局部内部类
 * 局部内部类是定义在一个方法或者一个作用域里面的类，它和成员内部类的区别在于局部内部类的访问仅限于方法内或者该作用域内。
 *
 * @author yuan
 */
public class LocalInner {
    public LocalInner() {
    }

    public LocalInner getWoman() {
        /**
         * 局部内部类
         * 局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
         */
        class Woman extends LocalInner {
            int age = 0;
        }
        return new Woman();
    }
}

/**
 * People和Man没有归属关系
 */
class Man {
    public Man() {
    }
}
