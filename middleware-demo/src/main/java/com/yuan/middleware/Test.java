package com.yuan.middleware;

import com.yuan.middleware.design.single.Singleton3;

public class Test {
    public static void main(String[] args) {
        Singleton3 singleton3 = Singleton3.INSTANCE;
        System.out.println(singleton3);
    }
}


