package com.yuan.middleware.interview;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 30天内连续签到10天
 *
 * @author yjm
 * @date 2020/3/6 2:29 下午
 */
@Slf4j
public class SignInDemo {
    private static final DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        //获取活动期间投资超过10天的用户
        List<String> userList = new ArrayList<>();
        //遍历用户查找符合连续10天的用户
        for (String userId : userList) {
            //根据用户id获取投资时间
            List<String> list = new ArrayList<>();
            list.add("2020-03-06 00:00:00");
            list.add("2020-03-07 00:00:00");
            list.add("2020-03-08 00:00:00");
            list.add("2020-03-09 00:00:00");
            list.add("2020-03-01 00:00:00");
            list.add("2020-03-02 00:00:00");
            list.add("2020-03-12 00:00:00");
            list.add("2020-03-13 00:00:00");
            list.add("2020-03-14 00:00:00");
            list.add("2020-03-15 00:00:00");
            list.add("2020-03-16 00:00:00");

            //时间转换为天数，便于计算时间差
            List<Integer> dayList = new ArrayList<>();
            for (String date : list) {
                int dayOfYear = LocalDateTime.parse(date, timeDtf).getDayOfYear();
                dayList.add(dayOfYear);
            }
            //计算连续签到
            signIn(dayList, userId);
        }


    }

    /**
     * 需要加redis防止多台服务器同时执行，这个方法发放奖励不需要事物，防止发放成功后，设置redis，但是后面数据库回滚，因为数据库事物和redis事物不一致
     *
     * @param dayList
     */
    private static void signIn(List<Integer> dayList, String userId) {
        //count=1因为连续签到for循环是从第二天开始
        int count = 1;

        for (int i = 0; i < dayList.size() - 1; i++) {
            if (dayList.get(i + 1) - dayList.get(i) == 1) {
                //后一天减前一天等于1，则是连续签到 +1
                count++;
                if (count >= 5) {
                    log.info("连续签到5天以上");
                    //根据userId+20200321signIn,rdeis判断之前有没有发放过奖励
                    /**
                     *
                     */
                    //发放奖励逻辑代码
                    /**
                     * 发放成功设置redis值
                     */
                    //设置userId+20200321signIn,redis值用户已经领取过奖励
                    return;
                }
            } else {
                //连续签到中断
                count = 1;
            }
        }

        System.out.println(count);

    }
}
