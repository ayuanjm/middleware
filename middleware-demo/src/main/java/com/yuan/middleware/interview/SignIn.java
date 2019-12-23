//package com.yuan.middleware.interview;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 计算连续签到
// *
// * @author yuan
// * @date 2019/12/23
// */
//@Slf4j
//public class SignIn {
//    private static final DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    public static void main(String[] args) {
//        List<String> userSignInDay = marketingAccountDao.getUserSignInDay(params.getUid());
//        if (userSignInDay.isEmpty()) {
//            //连续签到送积分活动结束或者之前没有进行签到
//            return;
//        }
//        log.info("用户在活动期间签到时间userId:" + params.getUid() + " ,userSignInDay:" + userSignInDay.toString());
//        List<Integer> dayList = new ArrayList<>();
//        for (String date : userSignInDay) {
//            //将数据库返回的时间转为当年的天数 比如2019-02-26 输出为57
//            dayList.add(LocalDateTime.parse(date, timeDtf).getDayOfYear());
//        }
//        //赋值连续签到天数为1，因为遍历天数时开始是从第二天开始，第二天 - 第一天
//        int count = 1;
//        for (int i = 0; i < dayList.size() - 1; i++) {
//            //签到时间之差为1说明是连续签到
//            if ((dayList.get(i + 1) - dayList.get(i)) == 1) {
//                count++;
//                if (count >= 5) {
//                    log.info(params.getUid() + "已经连续签到5天啦!");
//                    Integer userSignInDayScore = marketingGrowthDetailDao.getUserSignInDayScore(params.getUid());
//                    if (userSignInDayScore < 0) {
//                        log.info(params.getUid() + "连续签到5天发放一次积分");
//                        sendScore(params);
//                    } else {
//                        log.info(params.getUid() + "连续签到5天已经发放过积分不再发放");
//                    }
//                }
//            } else {
//                //连续签到中断，重新开始签到
//                count = 1;
//            }
//        }
//    }
//}
