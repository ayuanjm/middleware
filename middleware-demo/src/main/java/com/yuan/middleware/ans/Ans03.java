package com.yuan.middleware.ans;

/**
 * 选择排序(从第一个位置开始比较，找出最小的，和第一个位置互换，开始下一轮。)
 * 优点：一轮比较只需要换一次位置；
 * 缺点：效率慢，不稳定（举个例子5，8，5，2，9   我们知道第一遍选择第一个元素5会和2交换，那么原序列中2个5的相对位置前后顺序就破坏了）
 *
 * @author yuanjm
 * @date 2020/8/14 5:29 下午
 */
public class Ans03 {
    public static void main(String[] args) {
        int[] a = {5, 8, 5, 2, 9};
        for (int i = 0; i < a.length; i++) {
            int min = a[i];
            int index = i;
            for (int j = i; j < a.length; j++) {
                if (a[j] < min) {
                    min = a[j];
                    index = j;
                }
            }
            swap(a, i, index);
        }
        print(a);
    }

    private static void print(int[] a) {
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
    private static void swap(int[] a, int minPos, int first) {
        int temp;
        temp = a[minPos];
        a[minPos] = a[first];
        a[first] = temp;
    }
}
