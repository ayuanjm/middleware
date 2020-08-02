package com.yuan.middleware.jdk.algorithm.sort;

/**
 * @author yuanjm
 * @date 2020/8/2 6:54 下午
 */
public class QuickSortDemo {
    public static void main(String[] args) {
        int[] arr = {10, 7, 1, 4, 6, 8, 6, 1, 9, 6};
        sort(arr, 0, arr.length - 1);
        print(arr);
    }

    static void sort(int[] arr, int left, int right) {
        //最后只剩一个元素时，会出现left=0,right=-1的情况
        if (left >= right) {
            return;
        }
        //将数组以最后一个元素为分界线排序，排序后最后一个元素，左边都比它小，右边都比它大
        //之后去除该元素后，对左边和右边的两个子数组进行排序
        int mid = partition(arr, left, right);
        sort(arr, left, mid - 1);
        sort(arr, mid + 1, right);
    }

    static int partition(int[] arr, int leftBound, int rightBound) {
        int mark = arr[rightBound];
        int left = leftBound;
        int right = rightBound - 1;
        //{4,6},没有进循环，left= 4
        while (left < right) {
            //left < right防止left下标越界，等于数组长度
            //left==right是为了防止出现rightBound左边的已经都是比它小的
            //比如{2,3,6,5,10}left=3导致和rightBound交换
            while (left <= right && arr[left] <= mark) {
                left++;
            }
            // arr[right] = mark 也需要进行和left交换否则会出现
            // 1，9，7，6，8，6 交换变成 1，6，7，6，8，9的情况
            // left < right防止right下标越界等于-1
            while (left < right && arr[right] > mark) {
                right--;
            }

            if (left < right) {
                swap(arr, left, right);
            }
        }

        if (arr[left] >= mark) {
            swap(arr, left, rightBound);
        }

        return left;
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
