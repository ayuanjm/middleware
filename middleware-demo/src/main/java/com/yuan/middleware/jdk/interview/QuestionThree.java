package com.yuan.middleware.jdk.interview;

import java.util.TreeSet;

/**
 * 求N个有序数组的交集。
 * 比如：
 * N = 2，其中：A = {1，2，3，5}，B = {3，4，5}，那么：A & B={3，5}；
 * N = 3，其中：A = {1，2，3，5}，B = {3，4，5}，C = {1，2，4，5，7，9}，那么：A & B & C={5}；
 *
 * @author yuanjm
 * @date 2020/7/22 9:03 下午
 */

public class QuestionThree {
    public static void main(String[] args) {
        //不知道n个数组是怎么表示的
        int a[] = {1, 2, 3, 5};
        int b[] = {3, 4, 5};
        int c[] = {1, 2, 4, 5, 7, 9};
        TreeSet<Integer> TreeSetA = new TreeSet<>();
        TreeSet<Integer> TreeSetB = new TreeSet<>();

        for (int i = 0; i < a.length; i++) {
            TreeSetA.add(a[i]);
        }
        for (int i = 0; i < b.length; i++) {
            if (TreeSetA.contains(b[i])) {
                TreeSetB.add(b[i]);
            }
        }
        for (int i = 0; i < c.length; i++) {
            if (TreeSetB.contains(c[i])) {
                System.out.print(c[i]);
            }
        }
    }

}
