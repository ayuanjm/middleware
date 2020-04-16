package com.yuan.middleware.base.clazz.load;

import com.yuan.middleware.base.thread.markword.TestAtomicInteger;

import java.lang.reflect.Field;

/**
 *
 * 类加载过程：
 * 1、类加载就是执行Java程序编译之后在字节码文件中生成的clinit()方法(称之为类构造器)，clinit()方法由静态变量和静态代码块组成。
 *
 * 2、子类的加载首先需要先加载父类，如果父类为接口。则不会调用父类的clinit方法。一个类中可以没有clinit方法。
 *
 * 3、clinit方法中的执行顺序为：父类静态变量初始化，父类静态代码块，子类静态变量初始化，子类静态代码块。
 *
 * 4、clinit()方法只执行一次。
 *
 * 对象实例化过程：
 * 1、对象实例化过程就是执行Java程序编译之后在字节码文件中生成的init()方法(称之为实例构造器)，init()方法由非静态变量、非静态代码块以及对应的构造器组成。
 *
 * 2、init()方法可以重载多个，有几个构造器就有几个init()方法，每次创建实例，调用哪一个构造器，就会调用相应的init()方法。
 *
 * 3、init()方法中的执行顺序为：父类变量初始化，父类代码块，父类构造器，子类变量初始化，子类代码块，子类构造器。
 *
 * clinit()方法优先于init()方法执行，所以整个顺序就是：
 * 父类静态变量初始化，父类静态代码块，子类静态变量初始化，子类静态代码块，父类非静态变量初始化，父类非静态代码块，父类构造器，子类非静态变量初始化，子类非静态代码块，子类构造器。
 *
 * 类加载时只会加载静态代码块和静态变量。只有创建类的实例时: 变量和初始化代码块和构造函数才会执行（创建实例包括类加载）
 *
 * 类加载：执行<clinit>
 * 创建类实例：执行<<init>，但是创建类实例之前会先类加载执行<clinit>。
 * 类初始化：执行<clinit>方法，<clinit>方法由静态变量显示赋值代码和静态代码块组成，<clinit>方法只执行一次
 * 实例初始化：执行<init>方法，<init>方法由非静态实例变量显示赋值代码和非静态代码块，对应构造器代码组成。
 *
 * 1. 为一个类型创建一个新的对象实例时（比如new、反射、序列化）
 * 只有这个会触发 变量和初始化代码块和构造函数执行，这个是实例初始化，其他6个都是类初始化
 * 2. 调用一个类型的静态方法时（即在字节码中执行invokestatic指令）
 * 3. 调用一个类型或接口的静态字段，或者对这些静态字段执行赋值操作时（即在字节码中，执行getstatic或者putstatic指令），
 * ### 不过用final修饰的静态字段已经赋值了(String和基本类型，不包含包装类型)的除外，它被初始化为一个编译时常量表达式
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
     * final修饰的静态字段被调用时也会加载类
     * 不过用final修饰的静态字段已经赋值了(String和基本类型，不包含包装类型)的除外，它被初始化为一个编译时常量表达式 不会加载类
     */
    public final static String NAME = "YUANJM";

    /**
     * ClassLoading.object 不会触发ClassLoading类的初始化
     * final修饰引用类型，虽然引用的指向不会改变，但是真实的实例属性值还是可以改变，比如引用类型有其他引用类型的属性
     */
    public final static TestAtomicInteger object = new TestAtomicInteger();
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

    public static void main(String[] args) throws Exception {
        Field mail = ClassLoading.class.getDeclaredField("mail");
        String o = (String) mail.get(ClassLoading.class);
        System.out.println(o);
    }

}
