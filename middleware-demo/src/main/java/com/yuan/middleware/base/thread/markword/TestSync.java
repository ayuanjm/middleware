package com.yuan.middleware.base.thread.markword;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author yuan
 * @date 2020/01/08
 */
public class TestSync {
    @SneakyThrows
    public static void main(String[] args) {
        TestSync o = new TestSync();
        System.out.println("不加synchronized");
        /**
         *  添加-XX:BiasedLockingStartupDelay=0关闭jdk偏向锁延迟 输出偏向锁101，这时Mark Word中的线程id为0初始态 没有指向线程
         *  为什么会有偏向锁延迟，因为JVM虚拟机自己有一些默认启动的线程，里面有好多sync代码，这些sync代码启动时就知道肯定会有竞争，
         *  如果使用偏向锁，就会造成偏向锁不断的进行锁撤销和锁升级的操作，效率较低。
         */
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            //由于没有线程竞争，输出偏向锁，将Mark Word中的线程id设置为当前线程id
            System.out.println("第一次加synchronized");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
            lock(o);
            Thread.sleep(1000);
        }

    }

    @SneakyThrows
    private static void lock(Object o) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                /**
                 * lock方法后有Thread.sleep(1000)，输出重量锁010，没有Thread.sleep(1000)输出轻量锁000
                 * 该方法与main方法存在锁竞争，没有休眠时间时由于main方法很快执行完锁竞争时间短，偏向锁升级为轻量锁，
                 * 有休眠时间时偏向锁升级为轻量锁，cas自旋多次没有获取到锁，轻量级锁就要膨胀为重量级锁，直接进入堵塞状态，此时不消耗CPU。
                 */
                synchronized (o) {
                    System.out.println("第二次加synchronized");
                    System.out.println(ClassLayout.parseInstance(o).toPrintable());
                }
            }
        }).start();
    }
}
