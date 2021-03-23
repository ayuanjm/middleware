package com.yuan.middleware.jdk.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 选择排序(从第一个位置开始比较，找出最小的，和第一个位置互换，开始下一轮。) 每一次循环都找到最小的值和开始循环的index交换，这样每次循环结束index前面的必然有序
 * 优点：一轮比较只需要换一次位置；
 * 缺点：效率慢，不稳定（举个例子5，8，5，2，9   我们知道第一遍选择第一个元素5会和2交换，那么原序列中2个5的相对位置前后顺序就破坏了）时间复杂度O(n^2)
 *
 * @author yuan
 * @date 2020/02/25
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {5, 1, 8, 5, 2, 9};
        for (int i = 0; i < arr.length - 1; ++i) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; ++j) {
                minIndex = arr[minIndex] > arr[j] ? j : minIndex;
            }
            if (i != minIndex) {
                //i==minIndex时候用^运算会无法替换，因为i==minIndex指向同一块内存，arr[i]^arr[minIndex]后i和index处的内存值都为0
                swap(arr, i, minIndex);
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
