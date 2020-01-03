package com.yuan.middleware.concurrent.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 令牌桶算法限流 guava RateLimiter
 *
 * @author yuan
 * @date 2020/01/03
 */
@Slf4j
@RestController
@RequestMapping("/yuan")
public class RateLimiterDemo {
    /**
     * 1秒产生10个令牌,也就是1秒只能放行10个请求
     * 初始化10个的容量，所以前10个请求无需等待直接成功，后面的开始被1秒10次限流了，基本上每0.1秒放行一个。
     */
    private RateLimiter rateLimiter = RateLimiter.create(10);


    @RequestMapping("/acquire")
    public Object acquire() {
        /**
         * acquire会阻塞当前线程直到获取到令牌，也就是如果任务没有获取到令牌，会一直等待。
         * 那么请求就会卡在我们限定的时间内才可以继续往下走，这个方法返回的是线程具体等待的时间。
         */
        double acquire = rateLimiter.acquire();
        System.out.println("acquire等待时间:" + acquire);
        return acquire;
    }


    @RequestMapping("/tryAcquire")
    public Object tryAcquire() {
        /**
         * tryAcquire(long timeout, TimeUnit unit)
         * 从RateLimiter获取令牌，如果可以在不超过timeout的时间内获取得到的话:请求通过，
         * 如果无法在timeout内获取得到令牌的话，那么立即返回false（无需等待）:请求拒绝
         */
        boolean tryAcquire = rateLimiter.tryAcquire(1, TimeUnit.SECONDS);
        Object msg;
        if (tryAcquire != true) {
            msg = "在1秒内不能获得令牌，请求拒绝！";
        } else {
            msg = "请求通过！";
        }
        System.out.println(msg);
        return msg;
    }

    public static void main(String[] args) {
//        rateAcquire();
        rateTryAcquire();

    }

    private static void rateTryAcquire() {
        //一秒产生10个令牌
        RateLimiter rateLimiter = RateLimiter.create(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor
                (3,
                        30,
                        60,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(10),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 100; i++) {
                executor.execute(() -> {
                    boolean isValid = rateLimiter.tryAcquire(1, TimeUnit.SECONDS);
                    if (!isValid) {
                        System.out.println("在1秒内不能获得令牌，请求拒绝！");
                    } else {
                        System.out.println("请求通过！");
                    }
                });
            }
        } finally {
            executor.shutdown();
        }
    }

    private static void rateAcquire() {
        //一秒产生2个令牌,每0.5秒才能放行一个任务
        RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 10; i++) {
            //该方法会阻塞线程，直到令牌桶中能取到令牌为止才继续向下执行。
            double acquire = rateLimiter.acquire();
            System.out.println("任务执行" + i + "等待时间" + acquire);
        }
        System.out.println("执行结束");
    }

}
