package com.yuan.middleware.jdk.algorithm.leetcode;

import java.util.HashMap;

/**
 * 给定一个数组，给定一个数字。返回数组中可以相加得到指定数字的两个索引。
 *
 * @author yuanjm
 * @date 2020/5/4 9:03 上午
 */
public class ArrayFindIndex {
    public static void main(String[] args) {
        int[] twoSum = twoSum();
        print(twoSum);
    }

    private static int[] twoSum() {
        int[] arr = {1, 3, 5, 6, 9};
        int target = 6;
        int index = 0;
        HashMap<Integer, Integer> hashMap = new HashMap<>(16);
        for (int current : arr) {
            int offset = target - current;
            if (hashMap.containsKey(offset)) {
                return new int[]{hashMap.get(offset), index};
            }
            hashMap.put(current, index);
            index++;
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    private static void print(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
    }
}
