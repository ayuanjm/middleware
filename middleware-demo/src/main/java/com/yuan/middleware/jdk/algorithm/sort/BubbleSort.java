package com.yuan.middleware.jdk.algorithm.sort;

/**
 * 冒泡排序(从左到右，数组中相邻的两个元素进行比较，将较大的放到后面。)
 * 优点:比较简单，空间复杂度较低，是稳定的；
 * 缺点:时间复杂度太高，效率慢；
 *
 * @author yuan
 * @date 2020/02/25
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] a = {5, 8, 5, 2, 9};

        for (int j = 1; j < a.length; j++) {
            for (int i = 0; i < a.length - j; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i);
                }
            }
        }
        print(a);
    }

    private static void print(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
    }

    /**
     * 交换i和i+1的值
     *
     * @param a
     * @param i
     */
    private static void swap(int[] a, int i) {
        int temp;
        temp = a[i];
        a[i] = a[i + 1];
        a[i + 1] = temp;
    }
}
