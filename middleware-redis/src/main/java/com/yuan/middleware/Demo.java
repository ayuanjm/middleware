package com.yuan.middleware;

public class Demo {

    public static void main(String[] args) {
        try {
            Double.parseDouble(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
