package com.yuan.middleware.jdk.algorithm.sort;

/**
 * 归并排序
 * 先写合并一个数组中，两个有序数组的方法；再写合并一个数组中指定范围的两个有序数组的方法；
 * 然后再去写归并，将一个数组划分为2半，之后再划分子数组，直到只有一个元素，再用之前写的合并指定范围的方法两两合并
 *
 * @author yuanjm
 * @date 2020/7/22 4:12 下午
 */
public class MergeSort {
    public static void main(String[] args) {
//        int[] arr = {1, 3, 5, 6, 9, 2, 4, 7, 8};
        int[] arr = {10, 7, 1, 4, 6, 8, 6, 1, 9, 6};
        sort(arr, 0, arr.length - 1);
        print(arr);
    }

    static void sort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        //将数组分成两半
        int mid = left + (right - left) / 2;
        //左边排序
        sort(arr, left, mid);
        //右边排序
        sort(arr, mid + 1, right);
        //合并左右数组
        merge(arr, left, mid + 1, right);
    }

    static void merge(int[] arr, int leftIndex, int rightIndex, int rightBound) {
        int mid = rightIndex - 1;
        int[] temp = new int[rightBound - leftIndex + 1];
        int i = leftIndex;
        int j = rightIndex;
        int k = 0;
        while (i <= mid && j <= rightBound) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        //当有一个数组已经比较完了大小，将另一个数组剩余的元素直接放在后面
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= rightBound) {
            temp[k++] = arr[j++];
        }
//        print(temp);
        //将arr排序好
        for (int dummy : temp) {
            arr[leftIndex++] = dummy;
        }
    }

    private static void print(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
    }


    private static void swap(int[] a, int i, int j) {
        int temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
