package com.yuan.middleware.base.inner;

/**
 * 在Java中，可以将一个类定义在另一个类里面或者一个方法里面，这样的类称为内部类。
 * 广泛意义上的内部类一般来说包括这四种：成员内部类、局部内部类、匿名内部类和静态内部类。
 * <p>
 * 成员内部类
 *
 * @author yuan
 * @date 2019/11/23
 */
public class MemberInner {
    private double radius = 0;
    public static int count = 1;

    public MemberInner() {
        //必须先创建成员内部类的对象，再进行访问
        getDrawInstance().drawSahpe();
    }

    /**
     * 虽然成员内部类可以无条件地访问外部类的成员，而外部类想访问成员内部类的成员却不是这么随心所欲了。
     * 在外部类中如果要访问成员内部类的成员，必须先创建一个成员内部类的对象，再通过指向这个对象的引用来访问
     *
     * @return
     */
    private Draw getDrawInstance() {
        return new Draw();
    }

    /**
     * 内部类
     * <p>
     * 这样看起来，类Draw像是类Circle的一个成员，Circle称为外部类。
     * 成员内部类可以无条件访问外部类的所有成员属性和成员方法（包括private成员和静态成员）。
     * 不过要注意的是，当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是成员内部类的成员。
     * 如果要访问外部类的同名成员，需要以下面的形式进行访问：
     * 外部类.this.成员变量
     * 外部类.this.成员方法
     */
    private class Draw {
        public void drawSahpe() {
            //外部类的private成员
            System.out.println(radius);
            //外部类的静态成员
            System.out.println(count);
        }
    }

    /**
     * 成员内部类是依附外部类而存在的，也就是说，如果要创建成员内部类的对象，前提是必须存在一个外部类的对象。
     * 创建成员内部类对象的一般方式如下：
     *
     * @param args
     */
    public static void main(String[] args) {
        //第一种方式：
        MemberInner circle = new MemberInner();
        //必须通过Circle对象来创建
        MemberInner.Draw draw = circle.new Draw();

        //第二种方式：
        MemberInner.Draw drawInstance = circle.getDrawInstance();

    }
}
