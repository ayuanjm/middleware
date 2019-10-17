package com.yuan.middleware;

public class Test {


    public static void main(String[] args) {
        final String[] strings = {"1", "2", "3"};
        strings[0] = "9";
        for (String s : strings) {
            System.out.println(s);
        }
    }

}
