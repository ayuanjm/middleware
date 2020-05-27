package com.yuan.middleware.algorithm.sort;

/**
 * 快速排序
 * 快速排序采用双向查找的策略，每一趟选择当前所有子序列中的一个关键字作为枢纽轴，
 * 将子序列中比枢纽轴小的前移，比枢纽轴大的后移，当本趟所有子序列都被枢轴按上述规则划分完毕后将会得到新的一组更短的子序列，他们将成为下趟划分的初始序列集。
 * 时间复杂度：最好情况（待排序列接近无序）时间复杂度为O(nlog2n)，
 * 最坏情况（待排序列接近有序）每次把数组分割为一个元素和n-1个元素，n(n-1) = n^2 时间复杂度为O(n2)，平均时间复杂度为O(nlog2n)。
 * 为了避免最坏情况(数组有序且逆序)，可以不从最后一个元素开始，而是先随机取一个数和最后一个数交换，再开始快速排序
 * <p>
 * 1、先从数列中取出一个数作为基准数
 * 2、分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边
 * 3、再对左右区间重复第二步，直到各区间只有一个数
 *
 * @author yjm
 * @version 1.0
 * @date 2020/3/2 3:55 下午
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {10, 7, 1, 4, 6, 8, 6, 1, 9, 6};
//        int[] arr = {10, 9, 6};
        print(arr);
        System.out.println();
        sort(arr, 0, arr.length - 1);
        print(arr);

    }


    private static void sort(int arr[], int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return;
        }
        int mid = partition(arr, leftBound, rightBound);
        //需要注意边界
        sort(arr, leftBound, mid - 1);
        sort(arr, mid + 1, rightBound);

    }

    private static int partition(int arr[], int leftBound, int rightBound) {
        // 从数组最后一位(枢纽)开始，在数组首查找比枢纽大的元素left，在数组尾查找比枢纽小的元素right，
        // 将他们交换，直至left>right,将left与rightBound交换，以left为中心分割数组，再次执行之前的操作，直到分割只有一个元素。
        int pivot = arr[rightBound];
        int left = leftBound;
        int right = rightBound - 1;

        // 当left>right的时候说明right左侧已经没有比枢纽大的元素了，
        while (left < right) {
            // {0,1,2,3,4,5} 时需要left=right+1这样在最外层交换时 5和5交换，而不是4和5交换
            while (left <= right && arr[left] <= pivot) {
                left++;
            }
            while (left < right && arr[right] > pivot) {
                right--;
            }

            //left小于right 说明left的值是比枢纽大的，right的值是比枢纽小的，需要进行交换，使最后比枢纽 小的值在左边，大的值在右边
            if (left < right) {
                swap(arr, left, right);
            }

            System.out.println("left:" + left + ",right:" + right);
        }
        //会出现 {10,9,6} 第二次分割后 {6,9,10};9,10 为新的小数组，left为1，right也为1，rightBound为2，不会进入上面的循环中这时left比rightBound大，不能进行交换
        if (arr[left] > arr[rightBound]) {
            swap(arr, left, rightBound);
            print(arr);
            System.out.println();
        }
        return left;
    }

    private static void print(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
    }


    private static void swap(int[] a, int left, int right) {
        int temp;
        temp = a[left];
        a[left] = a[right];
        a[right] = temp;
    }
}
