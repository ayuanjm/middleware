package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，调用latch.countDown()计数器的值就-1，
 * 当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 * <p>
 * //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 * public void await() throws InterruptedException { };
 * <p>
 * //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 * public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
 * <p>
 * //将count值减1
 * public void countDown() { };
 *
 * @author yuan
 * @date 2020/01/15
 */
public class CountDownLatchTest {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        reordering();
    }

    /**
     * 指令重排
     * @throws InterruptedException
     */
    private static void reordering() throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            //创建一个线程计数器值为1
            CountDownLatch latch = new CountDownLatch(1);

            Thread one = new Thread(() -> {
                try {
                    //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
                    latch.await();
                } catch (InterruptedException e) {
                }
                a = 1;
                x = b;
            });

            Thread other = new Thread(() -> {
                try {
                    //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
                    latch.await();
                } catch (InterruptedException e) {
                }
                b = 1;
                y = a;
            });
            //启动线程
            one.start();
            other.start();
            //计数器的值减1，由于原来的值为1，减1后为0，await失效 one other 线程继续执行
            latch.countDown();
            //暂停当前(在哪个线程中执行的join方法)线程，等待被调用线程指向结束之后再继续执行
            one.join();
            //暂停main线程，等待other线程执行完 再继续执行
            other.join();

            String result = "第" + i + "次 (" + x + "," + y + "）";
            if (x == 0 && y == 0) {
                /**
                 *  如果不发生指令重排序，不可能出现 x,y同时为0
                 *  没有发生指令重排时:
                 *      当x为0时 a必然为1，且 b=1还没有执行，这时 y=a=1
                 *      当y为0时 b必然为1，且 a=1还没有执行，这时 x=b=1
                 *
                 */
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }

}
