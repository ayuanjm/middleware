package com.yuan.middleware;

import org.springframework.boot.jdbc.DataSourceBuilder;

/**
 * @author yjm
 * @date 2020/3/26 6:31 下午
 */
public class Demo {
    public static void main(String[] args) {
        DataSourceBuilder.create().build();
    }
}
