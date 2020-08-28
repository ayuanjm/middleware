package com.yuan.middleware.ans;

/**
 * 冒泡排序(从左到右，数组中相邻的两个元素进行比较，将较大的放到后面。)
 * 优点:比较简单，空间复杂度较低，是稳定的；
 * 缺点:时间复杂度太高，效率慢；
 *
 * @author yuanjm
 * @date 2020/8/14 5:37 下午
 */
public class Ans04 {
    public static void main(String[] args) {
        int[] a = {5, 8, 5, 2, 9, 8, 0, 2};
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length - i; j++) {
                if (a[j] < a[j - 1]) {
                    Sort.swap(a, j, j - 1);
                }
            }
        }
        Sort.print(a);
    }
}
