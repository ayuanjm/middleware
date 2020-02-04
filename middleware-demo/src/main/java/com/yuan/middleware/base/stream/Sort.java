package com.yuan.middleware.base.stream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * stream sort map Comparator
 *
 * @author yuan
 * @date 2020/01/10
 */
public class Sort {
    private static final DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
//        sortDate();
        mapSort();
    }

    /**
     * LocalDateTime默认实现compareTo，可以直接进行时间比较。
     */
    private static void sortDate() {
        String beginTime = "2020-01-10 04:42:32";
        int i = LocalDateTime.parse(beginTime, timeDtf).compareTo(LocalDateTime.now());
        System.out.println(i);
    }

    private static void mapSort() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "ZK");
        map.put("age", 13);
        map.put("date", "2020-01-10 04:42:32");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "ZA");
        map2.put("age", 23);
        map2.put("date", "2020-01-10 05:42:32");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "CX");
        map3.put("age", 20);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "CX");
        map4.put("age", 18);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        // 排序代码如下
        List<Map<String, Object>> collect = list.stream().sorted(Comparator.comparing(Sort::comparingByDate)
                .thenComparing(Comparator.comparing(Sort::comparingByAge).reversed()))
                .collect(Collectors.toList());
        System.out.println(collect);
    }


    private static String comparingByName(Map<String, Object> map) {
        return (String) map.get("name");
    }


    private static Integer comparingByAge(Map<String, Object> map) {
        return (Integer) map.get("age");
    }

    private static LocalDateTime comparingByDate(Map<String, Object> map) {
        Object date = map.get("date");
        //date为null和数据库中字段为空时排序保持一致(字段为null时代表这个字段属于该类型的最大值)
        if (date == null) {
            date = "2025-02-10 00:00:01";
        }
        return LocalDateTime.parse((String) date, timeDtf);
    }
}
