package com.yuan.middleware;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author yjm
 * @date 2020/3/11 8:53 下午
 */
public class Demo implements InitializingBean {
    public static void main(String[] args) {
        byte[] a = new byte[1024];
        for (;;){
            a = new byte[1024];
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
