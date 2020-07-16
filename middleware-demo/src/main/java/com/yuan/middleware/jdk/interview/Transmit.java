package com.yuan.middleware.jdk.interview;

import java.util.Arrays;

/**
 * 方法的参数传递机制
 * 在方法的参数传递中，基本数据类型按值传递，即拷贝了一个副本(副本不影响原来的值)！引用数据类型是按引用传递，即把栈中的地址传入！
 * String和包装类型是引用传递，但是是直接改变引用的指向，指向新的对象，所以不影响原来的值。
 * 因为String和包装类型和其他的引用类型不一样，它改变只能改变自己的引用，不像其他的引用类型可以改变属性值也可以改变引用。
 * 所以在传参时是不会改变原来的引用的值，看起来就像是值传递。
 *
 * @author yuan
 * @date 2019/11/24
 */
public class Transmit {
    public static void main(String[] args) {
        int j = 1;
        String str = "hello";
        Integer num = 200;
        int[] arr = {1, 2, 3, 4, 5};
        Mydata my = new Mydata();
        change(j, str, num, arr, my);
        System.out.println("j=" + j);
        System.out.println("str=" + str);
        System.out.println("num=" + num);
        System.out.println("arr=" + Arrays.toString(arr));
        System.out.println("my.a=" + my.a);

    }

    public static void change(int j, String s, Integer n, int[] a, Mydata m) {
        j += 1;
        s += "world";
        n += 1;
        a[0] += 1;
        m.a += 1;
    }
}

class Mydata {
    int a = 10;
}

