package com.yuan.middleware.jdk.base.thread.markword;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

/**
 * AtomicInteger CAS
 * <p>
 * OFFSET                                SIZE    TYPE DESCRIPTION                               VALUE
 * 0     Mark Word 4字节                4         (object header)                            01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 * 4     Mark Word 4字节                4         (object header)                            00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 * 8     类型指针(class对象指针) 4字节     4         (object header)                            05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
 * 12     实例数据 short i 2字节           2   short TestAtomicInteger.i                        0
 * 14     对齐 2字节                      2          (loss due to the next object alignment)
 * Instance size: 16 bytes 总共16字节 需要对齐成8字节的倍数(现在的64位机器一次读取64位，也就是8个字节，所以要求一个对象想要高效率的访问的时候需要是8个字节的倍数。HotSpot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍)
 * Space losses: 0 bytes internal + 2 bytes external = 2 bytes total  空间损失2字节，就是对齐的那两字节。
 *
 * @author yuan
 * @date 2020/01/08
 */
public class TestAtomicInteger {
    short i = 10;
    /**
     * 计算出来的只是指针的大小，不包括具体堆内存中的大小
     */
    byte[] array = new byte[1024];

    /**
     * 对象的创建包含3个步骤：为对象分配内存空间、执行构造方法、将对象的内存地址赋给引用。
     * 查看TestAtomicInteger testSync = new TestAtomicInteger()的汇编码:
     * 0 new #3 <com/yuan/middleware/base/thread/sync/TestAtomicInteger>
     * 3 dup
     * 4 invokespecial #4 <com/yuan/middleware/base/thread/sync/TestAtomicInteger.<init>>
     * 7 astore_1
     * <p>
     * new:为这个路径下的类创建对象分配内存空间，同时为属性赋值默认值 i=0。
     * invokespecial:执行<init>(称之为实例构造器)，<init>由非静态变量、非静态代码块以及对应的构造器组成。为属性赋值我们指定的值 i=10;
     * astore_1:将对象的内存地址赋给引用
     * invokespecial和astore_1可能会发生指令重排序，导致还没有执行<init>就将对象的内存地址赋给引用，但是这时的对象是不完整的而且不为null，如果使用这个对象就会引发一些问题。
     * DCL(double check lock)和volatile禁止指令重排实现完美单例模式。
     */
    public TestAtomicInteger() {
    }

    @SneakyThrows
    public static void main(String[] args) {
        TestAtomicInteger testSync = new TestAtomicInteger();
        System.out.println(ClassLayout.parseInstance(testSync).toPrintable());
//        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
//        unsafeField.setAccessible(true);
//        Field i = testSync.getClass().getDeclaredField("i");
//        //获取指定对象的Field字段的值,Unsafe类需要通过反射才能拿到实例对象,构造方法私有
//        Unsafe unsafe = (Unsafe) unsafeField.get(Unsafe.class);
//        /**
//         * (64位机器)属性i在对象testSync内存中的地址偏移量为12，因为对象头占了12个字节，Mark Word 8字节，类型指针 4字节(jvm开启压缩)
//         */
//        long offset = unsafe.objectFieldOffset(i);
//        System.out.println(offset);
//        /**
//         * var1 对象
//         * var2 对象内存偏移量
//         * var3 对象内存偏移量位置的期待值
//         * var4 期待值和内存偏移量相等后 修改后的值
//         * 该操作通过CPU的cmpxchg指令完成 是原子性的(new AtomicInteger().getAndIncrement();底层使用的就是CAS)
//         */
//        boolean success = unsafe.compareAndSwapInt(testSync, offset, 1, 2);
//        System.out.println(success);
//        System.out.println(testSync.i);
    }
}
