package com.yuan.middleware.ans;

/**
 * @author yuanjm
 * @date 2020/8/14 5:37 下午
 */
public class Sort {
    public static void print(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
    }

    /**
     * 最小位置和本次循环第一位交换位置
     *
     * @param a
     * @param minPos
     * @param first
     */
    public static void swap(int[] a, int minPos, int first) {
        int temp;
        temp = a[minPos];
        a[minPos] = a[first];
        a[first] = temp;
    }
}
