package com.yuan.middleware.base.classload;

/**
 * 类加载
 * 何时触发初始化: 类会加载多次 - 静态变量和静态代码块只会初始化一次
 * 类加载时只会加载静态代码块和静态变量。只有创建类的实例时: 变量和初始化代码块和构造函数才会执行
 * <p>
 * 1. 为一个类型创建一个新的对象实例时（比如new、反射、序列化）
 * 2. 调用一个类型的静态方法时（即在字节码中执行invokestatic指令）
 * 3. 调用一个类型或接口的静态字段，或者对这些静态字段执行赋值操作时（即在字节码中，执行getstatic或者putstatic指令），
 * ### 不过用final修饰的静态字段除外，它被初始化为一个编译时常量表达式
 * 4. 调用JavaAPI中的反射方法时（比如调用java.lang.Class中的方法(Class.forName)，或者java.lang.reflect包中其他类的方法）
 * 5. 初始化一个类的派生类时（Java虚拟机规范明确要求初始化一个类时，它的超类必须提前完成初始化操作，接口例外）
 * 6. JVM启动包含main方法的启动类时。
 * 7. 对于静态字段，只有直接定义这个字段的类才会被初始化，因此通过其子类来引用父类中定义的静态字段，只会触发父类的初始化而不会触发子类的初始化。
 * <p>
 * 加载顺序: 静态变量和静态初始化块是依照他们在类中的定义顺序进行初始化的。同样，变量和初始化块也遵循这个规律。
 * 1. 父类--静态变量
 * 2. 父类--静态初始化块
 * 3. 子类--静态变量
 * 4. 子类--静态初始化块
 * 5. 父类--变量
 * 6. 父类--初始化块
 * 7. 父类--构造器
 * 8. 子类--变量
 * 9. 子类--初始化块
 * 10. 子类--构造器
 *
 * @author yuan
 */
public class ClassLoading {
    /**
     * final 修饰的静态字段被调用时不会加载类
     */
    public final static String NAME = "YUANJM";

    /**
     * static修饰 被调用时会加载类，加载静态变量和静态代码块
     */
    public static String PHONE = "13552607396";

    /**
     * 变量只有在创建类的实例时会被初始化
     */
    public String mail = "2459087172@qq.com";

    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("代码块");
    }

    public ClassLoading() {
        System.out.println("构造方法");
    }

}
