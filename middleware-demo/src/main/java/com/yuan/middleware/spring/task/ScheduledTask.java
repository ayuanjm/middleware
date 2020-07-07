package com.yuan.middleware.spring.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * /：表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着每小时第5分钟开始触发一次，
 * 加20分钟后触发一次，所以每小时的25，45等分别触发一次.
 * 10/10，从10开始，每隔10执行一次。
 * https://www.cnblogs.com/xiang--liu/p/11378860.html
 *
 * @author yuanjm
 * @date 2020/7/7 12:20 下午
 */
@Component
@Slf4j
@EnableScheduling
public class ScheduledTask {

    //    @Scheduled(cron = "0 0 10 * * ?")//每天十点
//    @Scheduled(cron = "0 55 23 * * ?")//每天23点55分
//    @Scheduled(cron = "0/30 * * * * ?")//从0秒开始每30秒，也就是30，60执行
    @Scheduled(cron = "0 0/1  * * * ?")//从0分开始每1分，也就是整数分值执行
    public void task() {
        log.info("定时任务执行");
    }
}
