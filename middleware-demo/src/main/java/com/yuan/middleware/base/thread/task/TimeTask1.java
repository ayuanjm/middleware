package com.yuan.middleware.base.thread.task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author yuanjm
 * @date 2020/5/27 5:21 下午
 */
public class TimeTask1 {
    /**
     * 延迟100ms后，间隔10s打印出：hello world
     *
     * @param args
     */
    public static void main(String[] args) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        }, 100, 10000);

    }

}

