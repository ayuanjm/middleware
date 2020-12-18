package com.yuan.middleware.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2020/12/18 11:06 上午
 * Description: https://www.sunjs.com/article/detail/f6a49fa32bef4b63b26aa4a8ac551a79.html
 * 对于ipv4地址： 192.168.1.3：每段都用二进制表示：
 * 192(10) = 11000000(2) ; 168(10) = 10101000(2) ; 1(10) = 00000001(2) ; 3(10) = 00000011(2) 。
 * 所以连在一起就是：11000000101010000000000100000011，对应的int数字就是-1062731775 。
 * 具体算法分析：
 * 192左移24位： 11000000 00000000 00000000 00000000
 * 168左移16位： 00000000 10101000 00000000 00000000
 * 1左移08位：   00000000 00000000 00000001 00000000
 * 3左移00位：   00000000 00000000 00000000 00000011
 * 按位或结果：   11000000 10101000 00000001 00000011
 * 即 1062731775
 * 位或:两个位只要有一个为1，那么结果就是1，否则就为0，
 */
public class IpKit {
    /**
     * IPv4地址转换为int类型数字
     *
     * @param ipv4Addr
     * @return
     */
    public static int ipToInt(String ipv4Addr) {
        // 判断是否是ip格式的
        if (!isIPv4Address(ipv4Addr)) {
            throw new RuntimeException("Invalid ip address");
        }

        // 匹配数字
        final Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(ipv4Addr);
        int result = 0;
        int counter = 0;
        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group());
            //第一次先与0位或，不影响结果。<< 左移
            result = (value << 8 * (3 - counter++)) | result;
        }
        return result;
    }

    /**
     * 判断是否为ipv4地址
     *
     * @param ipv4Addr
     * @return
     */
    private static boolean isIPv4Address(String ipv4Addr) {
        // 0-255的数字
        String lower = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";
        String regex = lower + "(\\." + lower + "){3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipv4Addr);
        return matcher.matches();
    }

    /**
     * 将int数字转换成ipv4地址
     *
     * @param ip
     * @return
     */
    public static String intToIp(int ip) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        // 是否需要加入'.'
        boolean needPoint = false;
        for (int i = 0; i < 4; i++) {
            if (needPoint) {
                sb.append('.');
            }
            needPoint = true;
            int offset = 8 * (3 - i);
            num = (ip >> offset) & 0xff;
            sb.append(num);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String ip = "124.202.200.166";
        System.out.println(ipToInt(ip));
        System.out.println(intToIp(2093664422));
    }

}
