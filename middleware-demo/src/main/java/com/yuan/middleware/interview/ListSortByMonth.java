package com.yuan.middleware.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据月份排序输出
 *
 * @author yjm
 * @date 2020/4/22 7:50 下午
 */
public class ListSortByMonth {
    public static void main(String[] args) {
        //最初始的list，模拟数据库查询出来的数据(按照时间倒叙排序)
        List<Map<String, Object>> originalList = new ArrayList<>();
        //map模拟数据库中的一行记录
        java.util.Map<String, Object> map = new HashMap<>(16);
        map.put("createTime", "2020-03-06 00:00:00");
        map.put("month", 3);
        originalList.add(map);
        //由于是引用传递，map引用指向新的堆内存不影响原来originalList中的map
        map = new HashMap<>(16);
        map.put("createTime", "2020-03-03 00:00:00");
        map.put("month", 2);
        originalList.add(map);
        map = new HashMap<>(16);
        map.put("createTime", "2020-02-06 00:00:00");
        map.put("month", 2);
        originalList.add(map);
        map = new HashMap<>(16);
        map.put("createTime", "2020-01-06 00:00:00");
        map.put("month", 1);
        originalList.add(map);
        map = new HashMap<>(16);
        map.put("createTime", "2020-01-05 00:00:00");
        map.put("month", 1);
        originalList.add(map);
        // 输出[{month=3, createTime=2020-03-06 00:00:00}, {month=2, createTime=2020-03-03 00:00:00},
        // {month=2, createTime=2020-02-06 00:00:00}, {month=1, createTime=2020-01-06 00:00:00},
        // {month=1, createTime=2020-01-05 00:00:00}]
        System.out.println(originalList);
        //用来保存每个月份的记录
        List<Map<String, Object>> monthList = new ArrayList<>();
        //用来保存最后的输出结果，按照每个月的记录为一个子List输出
        List<List<Map<String, Object>>> resultList = new ArrayList<>();
        //由于初始记录是按照创建时间倒叙排序的，因此取第一条记录的月份作为标志月份
        int month = (int) originalList.get(0).get("month");
        //遍历初始数据
        for (int i = 0; i < originalList.size(); i++) {
            //如果数据的月份和标志月份不相等，则把之前月份的monthList加入resultList，表示这个月份的数据遍历完成，当前这条数据为下个月的第一条数据
            if ((int) originalList.get(i).get("month") != month) {
                //把之前月份的monthList加入resultList
                resultList.add(monthList);
                //monthList指向新的堆内存，用来存放下一个月份的数据
                monthList = new ArrayList<>();
                //将标志月份改为下个月份
                month = (int) originalList.get(i).get("month");
            }
            //将当前数据添加到当前月份List中
            monthList.add(originalList.get(i));
        }
        //把最后一个月份的数据添加到resultList中
        resultList.add(monthList);
        // 输出[[{month=3, createTime=2020-03-06 00:00:00}],
        // [{month=2, createTime=2020-03-03 00:00:00}, {month=2, createTime=2020-02-06 00:00:00}],
        // [{month=1, createTime=2020-01-06 00:00:00}, {month=1, createTime=2020-01-05 00:00:00}]]
        System.out.println(resultList);
    }
}
