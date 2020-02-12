package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author yuan
 * @date 2020/02/12
 */
public class LockSupportTest {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        String[] arrayA = {"A", "B", "C", "D", "E"};
        String[] arrayB = {"a", "b", "c", "d", "e"};


        t1 = new Thread(() -> {
            for (int i = 0; i < arrayA.length; i++) {
                System.out.print(arrayA[i]);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (int i = 0; i < arrayB.length; i++) {
                LockSupport.park();
                System.out.print(arrayB[i]);
                LockSupport.unpark(t1);
            }
        });

        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.start();

    }
}
