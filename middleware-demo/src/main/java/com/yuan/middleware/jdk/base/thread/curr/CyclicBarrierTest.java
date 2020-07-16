package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier(int parties, Runnable barrierAction)
 * 当满足cyclicBarrier.await()的次数满足parties，才会执行barrierActio，
 * 否则一直等待，只有await()的次数刚好是parties倍数才会停止
 * <Phaser></Phaser>
 *
 * @author yuan
 * @date 2020/02/08
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        //每5个await后才会执行一次 barrierAction：满人，发车,没有满5就一直等待，刚好是5的倍数就会停止。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,
                () -> System.out.println("满人，发车"));
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"start");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName()+"end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
