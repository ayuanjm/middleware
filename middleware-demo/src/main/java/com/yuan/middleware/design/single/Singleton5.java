package com.yuan.middleware.design.single;

/**
 * 懒汉式
 * 延迟创建这个实例对象
 * 1. 构造器私有化
 * 2. 用一个静态变量保存这个唯一的实例
 * 3. 提供一个静态方法，获取这个实例对象
 *
 * @author yuan
 */
public class Singleton5 {
    /**
     * instance = new Singleton5();
     *
     * 不是原子性操作，至少会经过三个步骤：
     *
     * 1. 分配内存
     * 2. 执行构造方法
     * 3. 指向地址
     *
     * 由于指令重排，导致A线程执行 instance = new Singleton5();的时候，可能先执行了第三步（还没执行第二步），
     * 此时线程B又进来了，发现instance已经不为空了，直接返回了instance，并且后面使用了返回的instance，
     * 由于线程A还没有执行第二步，导致此时instance还不完整，可能会有一些意想不到的错误，所以就有了下面一种单例模式。
     *
     * 这种单例模式增加一个volatile关键字来避免指令重排：
     */
    private static volatile Singleton5 instance;

    private Singleton5() {

    }

    /**
     * 线程安全
     *
     * @return
     */
    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized (Singleton5.class) {
                if (instance == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton5.getInstance();
    }
}
