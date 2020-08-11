package com.yuan.middleware.jdk.base.thread.interview;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuanjm
 * @date 2020/8/11 2:50 下午
 */
@Slf4j
public class ThreadLocalTest {
    public static final ThreadLocal threadLocal = new InheritableThreadLocal();

    /**
     * public class Thread implements Runnable {
     *   ……
     *    if (inheritThreadLocals && parent.inheritableThreadLocals != null)
     *       this.inheritableThreadLocals=ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
     *   ……
     * }
     * 如果线程的inheritThreadLocals变量不为空，比如我们上面的例子，而且父线程的inheritThreadLocals也存在，
     * 那么我就把父线程的inheritThreadLocals给当前线程的inheritThreadLocals。
     * @param args
     */
    public static void main(String[] args) {
        threadLocal.set("帅得一匹");
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                log.info("张三帅么:" + threadLocal.get());
            }
        };
        t.start();
    }
}
