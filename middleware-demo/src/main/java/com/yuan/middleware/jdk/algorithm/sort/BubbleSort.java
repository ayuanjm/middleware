package com.yuan.middleware.jdk.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序(从左到右，数组中相邻的两个元素进行比较，将较大的放到后面。),每次循环后该次循环的最后一个index必然最大，index后面的元素必然有序。
 * 优点:比较简单，空间复杂度较低，是稳定的；
 * 缺点:时间复杂度太高，效率慢，一轮比较交换次数多；时间复杂度O(n^2)
 *
 * @author yuan
 * @date 2020/02/25
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {5, 8, 5, 2, 9};
        for (int i = 1; i < arr.length; ++i) {
            for (int j = 0; j < arr.length - i; ++j) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        System.out.println(JSON.toJSONString(arr));
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }
}
