package com.yuan.middleware.base.clazz.load;

import lombok.Data;

/**
 * 1、加载
 * 由类加载器负责根据一个类的全限定名来读取此类的二进制字节流到JVM内部，并存储在运行时内存区的方法区，然后将其转换为一个与目标类型对应的java.lang.Class对象实例
 * 2、验证
 * 格式验证：验证是否符合class文件规范
 * 语义验证：检查一个被标记为final的类型是否包含子类；检查一个类中的final方法是否被子类进行重写；
 * 确保父类和子类之间没有不兼容的一些方法声明（比如方法签名相同，但方法的返回值不同）
 * 操作验证：在操作数栈中的数据必须进行正确的操作，对常量池中的各种符号引用执行验证（通常在解析阶段执行，检查是否可以通过符号引用中描述的全限定名定位到指定类型上，以及类成员信息的访问修饰符是否允许访问等）
 * 3、准备
 * 为类中的所有静态变量分配内存空间，并为其设置一个初始值（由于还没有产生对象，实例变量不在此操作范围内）
 * 被final修饰的static变量（常量），会直接赋值；
 * 4、解析
 * 将常量池中的符号引用转为直接引用（得到类或者字段、方法在内存中的指针或者偏移量，以便直接调用该方法），这个可以在初始化之后再执行。
 * 解析需要静态绑定的内容。// 所有不会被重写的方法和域都会被静态绑定
 * 以上2、3、4三个阶段又合称为链接阶段，链接阶段要做的是将加载到JVM中的二进制字节流的类数据信息合并到JVM的运行时状态中。
 * 5、初始化（先父后子）
 * 5.1 为静态变量赋值 5.2 执行static代码块 注意：static代码块只有jvm能够调用
 *
 * @author yjm
 * @date 2020/3/6 1:08 下午
 */
public class ClassLoadingProcedure {
    public static void main(String[] args) {
        System.out.println(T.count);
    }

    @Data
    static
    class T {
        /**
         * 类加载：加载，验证，准备，解析，初始化
         * 初始化就是执行<clinit>()方法
         * <clinit>方法由静态变量显示赋值代码和静态代码块组成，类静态变量显示赋值代码和静态代码块代码从上到下顺序执行
         * 初始化的时执行先执行new T(),new一个对象时如果是第一次使用该类，就可以分为两个过程：加载并初始化类和创建对象。
         * 但是这里已经处于加载T类的过程中，所以不需要再次加载，而是直接创建对象，执行<init>方法
         * <init>方法由非静态实例变量显示赋值代码和非静态代码块，对应构造器代码组成。
         * 非静态实例变量显示赋值代码和非静态代码块从上到下顺序执行，而对应构造器代码最后执行
         * <p>
         * 加载T类在初始化之前和正常加载类一样，初始化时先执行第一行类静态变量显示赋值代码
         * new T()，由于加载过类T类，直接创建对象,执行<init>,
         * 先执行非静态实例变量显示赋值代码a = show，    输出show方法a：10
         * 再执行非静态代码块,                        输出非静态代码块a:12
         * 再执行构造方法,                           输出构造方法count：1
         * 再执行第二行类静态变量显示赋值代码count=2
         * 再执行静态代码块，                         输出静态代码块
         * 最后执行System.out.println(T.count)，     输出 2
         */
        public static T t = new T();
        public static int count = 2;
        public int a = show();

        static {
            System.out.println("静态代码块");
        }

        {
            System.out.println("非静态代码块a:" + a);
        }

        public int show() {
            System.out.println("show方法a:" + a);
            a = 12;
            return a;
        }

        public T() {
            count++;
            System.out.println("构造方法count:"+count);
        }
    }
}