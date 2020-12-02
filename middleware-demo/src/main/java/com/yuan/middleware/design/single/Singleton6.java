package com.yuan.middleware.design.single;

/**
 * 懒汉式
 * 在内部类被加载和初始化时，才创建INSTANCE实例对象
 * 静态内部类不会自动随着外部内的加载和初始化而初始化，它是需要单独去加载和初始化的
 * 因为是在内部类加载和初始化时，创建的，因此是线程安全的
 * <p>
 * JVM必须确保一个类在初始化的过程中，如果是多线程需要同时初始化它，仅仅只能允许其中一个线程对其执行初始化操作，
 * 其余线程必须等待，只有在活动线程执行完对类的初始化操作之后，才会通知正在等待的其他线程。
 *
 * @author yuan
 */
public class Singleton6 {
    private Singleton6() {
    }

    private static class Inner {
        private static final Singleton6 INSTANCE = new Singleton6();
    }

    public static Singleton6 getInstance() {
        return Inner.INSTANCE;
    }
}
