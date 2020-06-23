package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量 Semaphore 是一个非负整数（ >=1 ）。当一个线程想要访问某个共享资源时，它必须要先获取 Semaphore。当 Semaphore > 0 时，
 * 获取该资源并使 Semaphore – 1 。如果Semaphore 值 = 0，则表示全部的共享资源已经被其他线程全部占用，线程必须要等待其他线程释放资源。
 * 当线程释放资源时，Semaphore 则 +1 。
 * <p>
 * 为了简单起见我们假设停车场仅有 5 个停车位。一开始停车场没有车辆所有车位全部空着，然后先后到来三辆车，停车场车位够，安排进去停车，然后又来三辆，
 * 这个时候由于只有两个停车位，所有只能停两辆，其余一辆必须在外面候着，直到停车场有空车位。当然，以后每来一辆都需要在外面候着。当停车场有车开出去，
 * 里面有空位了，则安排一辆车进去（至于是哪辆，要看选择的机制是公平还是非公平）。
 * 从程序角度看，停车场就相当于信号量 Semaphore ，其中许可数为 5 ，车辆就相对线程。当来一辆车时，许可数就会减 1 。
 * 当停车场没有车位了（许可数 == 0 ），其他来的车辆需要在外面等候着。如果有一辆车开出停车场，许可数 + 1，然后放进来一辆车。
 *
 * @author yuanjm
 * @date 2020/6/23 10:55 上午
 */
public class Semaphore1 {
    static class Parking {

        //信号量
        private Semaphore semaphore;

        Parking(int count) {
            semaphore = new Semaphore(count);
        }

        public void park() {
            try {
                //获取信号量当Semaphore>0时给Semaphore-1，如果Semaphore=0阻塞等待其他线程释放
                semaphore.acquire();
                long time = (long) (Math.random() * 10);
                System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "秒...");
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "开出停车场...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //给Semaphore+1
                semaphore.release();
            }
        }
    }


    static class Car extends Thread {
        Parking parking;

        Car(Parking parking) {
            this.parking = parking;
        }

        @Override
        public void run() {
            parking.park();     //进入停车场
        }
    }

    public static void main(String[] args) {
        Parking parking = new Parking(3);

        for (int i = 0; i < 5; i++) {
            new Car(parking).start();
        }
        show();
    }

    private static void show() {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //当Semaphore>0时给Semaphore-1，如果Semaphore=0阻塞等待其他线程释放
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //给Semaphore+1
                semaphore.release();
            }).start();
        }
    }
}