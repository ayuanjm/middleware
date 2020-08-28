package com.yuan.middleware.ans;

/**
 * 将一个记录插入到已排好序的序列中，从而得到一个新的有序序列
 * 将序列的第一个数据看成是一个有序的子序列，然后从第二个记录逐个向该有序的子序列进行有序的插入，直至整个序列有序
 * 最好时间复杂度为n，比选择，冒泡好
 *
 * @author yuanjm
 * @date 2020/8/14 4:23 下午
 */
public class Ans02 {
    public static void main(String[] args) {
        int[] a = {5, 8, 5, 2, 9};
        for (int j = 1; j < a.length; j++) {
            for (int i = j; i > 0; i--) {
                if (a[i] < a[i - 1]) {
                    swap(a, i - 1);
                } else {
                    break;
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
