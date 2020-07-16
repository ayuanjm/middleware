package com.yuan.middleware.jdk.algorithm.sort;

/**
 * 插入排序
 * 将一个记录插入到已排好序的序列中，从而得到一个新的有序序列
 * 将序列的第一个数据看成是一个有序的子序列，然后从第二个记录逐个向该有序的子序列进行有序的插入，直至整个序列有序
 * 最好时间复杂度为n，比选择，冒泡好
 *
 * @author yuan
 * @date 2020/02/25
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] a = {5, 8, 5, 2, 9};
//        int[] a = {1, 2, 3, 4, 5};
        //整个排序比较次数，用来查看最好效率
        int count = 0;
        for (int j = 1; j < a.length; j++) {
            for (int i = j; i > 0; i--) {
                count++;
                if (a[i] < a[i - 1]) {
                    swap(a, i - 1);
                } else {
                    //说明a[i] > a[i - 1],由于a[i-1]之前的都是有序的，所以a[i]大于之前的所有值，可以结束本次循环，
                    //这样最好的时间复杂度为n，已经排序好的数组再排序，每次内循环只会经过一次比较
                    break;
                }
            }
            print(a);
            System.out.println();
        }
        System.out.println("整个排序比较次数:" + count + "数组长度:" + a.length);
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
