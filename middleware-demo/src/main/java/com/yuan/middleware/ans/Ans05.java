package com.yuan.middleware.ans;

/**
 * 快速排序采用双向查找的策略，每一趟选择当前所有子序列中的一个关键字作为枢纽轴，
 * 将子序列中比枢纽轴小的前移，比枢纽轴大的后移，当本趟所有子序列都被枢轴按上述规则划分完毕后将会得到新的一组更短的子序列，他们将成为下趟划分的初始序列集。
 * 时间复杂度：最好情况（待排序列接近无序）时间复杂度为O(nlog2n)，
 * 最坏情况（待排序列接近有序）每次把数组分割为一个元素和n-1个元素，n(n-1) = n^2 时间复杂度为O(n2)，平均时间复杂度为O(nlog2n)。
 * 为了避免最坏情况(数组有序且逆序)，可以不从最后一个元素开始，而是先随机取一个数和最后一个数交换，再开始快速排序
 *
 * @author yuanjm
 * @date 2020/8/14 5:48 下午
 */
public class Ans05 {
    public static void main(String[] args) {
        int[] arr = {10, 7, 1, 4, 6, 8, 6, 1, 9, 6};
        sort(arr, 0, arr.length - 1);
        Sort.print(arr);
    }

    public static void sort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = quickSort(arr, left, right);
        sort(arr, left, mid - 1);
        sort(arr, mid + 1, right);
    }

    public static int quickSort(int[] arr, int leftBound, int rightBound) {
        int left = leftBound;
        int right = rightBound - 1;
        int mark = arr[rightBound];
        while (left < right) {
            while (left <= right && arr[left] <= mark) {
                left++;
            }
            while (left < right && arr[right] > mark) {
                right--;
            }
            if (left < right) {
                Sort.swap(arr, left, right);
            }

            if (arr[left] > mark) {
                Sort.swap(arr, left, rightBound);
            }
        }
        return left;
    }
}
