package com.yuan.middleware.jdk.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 插入排序
 * 将一个记录插入到已排好序的序列中，从而得到一个新的有序序列
 * 将数组的第一个数据看成是一个有序的子序列，然后从第二个元素开始向该有序的子序列进行插入，直至整个数组有序
 * 最好时间复杂度为O(n)原来就是有序的，最坏时间复杂度O(n^2)，比选择，冒泡好
 *
 * @author yuan
 * @date 2020/02/25
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {5, 1, 8, 5, 2, 9};
        for (int i = 1; i < arr.length - 1; ++i) {
            for (int j = i; j > 0; --j) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
        System.out.println(JSON.toJSONString(arr));
    }

    /**
     * @description: 只有i和j使用两块内存时才能完成替换，如果是同一快内存那么替换失败，该内存最终的值为0
     * @author yuanjiamin
     * @date 2021/3/23 2:42 下午
     */
    private static void swap(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }
}
