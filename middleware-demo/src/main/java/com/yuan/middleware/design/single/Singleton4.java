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
public class Singleton4 {
    private static Singleton4 instance;

    private Singleton4() {

    }

    /**
     * 线程不安全
     *
     * @return
     */
    public static Singleton4 getInstance() {
        if (instance == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton4();
        }
        return instance;
    }
}
