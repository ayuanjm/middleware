package com.yuan.middleware.base.thread.interview;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字对于基本类型的修改可以在随后对多个线程的读保持一致，
 * 但是对于引用类型如数组，实体bean，仅仅保证引用的可见性，但并不保证引用内容的可见性。
 * 也就是说修改了引用类型里的属性值，不会改变引用类型：没有体现对引用类型进行写操作。
 *
 * @author yuan
 * @date 2020/02/09
 */
@Data
public class TestVolatile {
    private volatile static A a = new A();

    @Data
    static class A {
        int size = 0;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("t1:" + a.size);
            while (true) {
                if (a.size == 5) {
                    break;
                }
            }
            System.out.println("t1,结束");
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                a.size++;
                System.out.println("t2:" + a.size);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
