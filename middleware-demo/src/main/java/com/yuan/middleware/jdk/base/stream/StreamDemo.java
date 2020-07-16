package com.yuan.middleware.jdk.base.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目： 按照给出数据，找出同事满足以下条件的用户
 *           1. 全部满足偶数 ID
 *           2. 年龄大于24
 *           3. 用户名转换为大写
 *           4. 用户名字母  倒排序
 *           5. 只输出一个用户名字  limit
 */
public class StreamDemo {

    public static void main(String[] args) {

        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 22);
        User u4 = new User(14, "d", 28);
        User u5 = new User(16, "e", 26);

        //数据 ===== 数据库里面查询   （SQL ，排序，分页，条件判断！）
        //集合，数据
        //流，计算！   链式编程    new StreamDemo().a().b().c();
        // 函数式接口  -- lambda  ()——>


        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        /*
        1. 将list转化为stream；  流，过滤器(过滤数据)
        2. 将用户过滤出来！interface Predicate<T> lambda；再次过滤
        SQL : 排序，分页！===> 提供数据

         */
        list.stream()
                .filter(u->{return u.getId()%2==0;})
                .filter(u->{return u.getAge()>24;})
                .map(u->{return u.getUserName().toUpperCase();})
                .sorted((o1,o2)->{return o2.compareTo(o1);})
                .limit(1).collect(Collectors.toList());     //sql , limit   亿级流量！
        /*
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        //映射
        // list2 转换为一个流， 映射，将这里面的每一个值，*2。流再次转为为集合
        list2 = list2.stream().map(x->{return x*2;}).collect(Collectors.toList());

        for (Integer element : list2) {
            System.out.println(element);
        }
        */

    }


}
