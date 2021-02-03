package com.yuan.middleware.jdk.base.thread.interview;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author yuanjm
 * @date 2020/8/11 2:50 下午
 */
@Slf4j
public class ThreadLocalTest {
    public String name;

//    public static final ThreadLocal<> threadLocal = new InheritableThreadLocal();

    public static final ThreadLocal<ThreadLocalTest> threadLocal = new InheritableThreadLocal();
    public static final ThreadLocal<ThreadLocalTest> transmittableThreadLocal = new TransmittableThreadLocal<>();

    /**
     * public class Thread implements Runnable {
     * ……
     * if (inheritThreadLocals && parent.inheritableThreadLocals != null)
     * this.inheritableThreadLocals=ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
     * ……
     * }
     * 如果线程的inheritThreadLocals变量不为空，比如我们上面的例子，而且父线程的inheritThreadLocals也存在，
     * 那么我就把父线程的inheritThreadLocals给当前线程的inheritThreadLocals。
     * 父线程的inheritThreadLocals赋值给子线程的inheritThreadLocals，不是直接把引用赋值给子线程，而是遍历父线程的ThreadLocalMap，
     * 将里面的Entry数组遍历，在子线程创建一个新的Entry数组，Entry数组内部的key value属性和父线程的一样，属于浅拷贝。
     * 如果是直接threadLocal.set()，那么子线程的修改不影响父线程。
     * 如果是threadLocal.get()取出值，再修改值的内部属性，会影响父线程。
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest localTest = new ThreadLocalTest();
        localTest.name = "before";
        threadLocal.set(localTest);
        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get().name);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get().name);
            ThreadLocalTest threadLocalTest = threadLocal.get();
            threadLocalTest.name = "curr";
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get().name);
        }).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get().name);
    }

//    private static void extracted() throws InterruptedException {
//        threadLocal.set("帅得一匹");
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                log.info("张三帅么:" + threadLocal.get());
//                threadLocal.set("a");
//            }
//        };
//        t.start();
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(threadLocal.get());
//    }
}
