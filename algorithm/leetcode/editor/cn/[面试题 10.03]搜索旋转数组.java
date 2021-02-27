//搜索旋转数组。给定一个排序后的数组，包含n个整数，但这个数组已被旋转过很多次了，次数不详。请编写代码找出数组中的某个元素，假设数组元素原先是按升序排列的。若
//有多个相同元素，返回索引值最小的一个。 
//
// 示例1: 
//
//  输入: arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 5
// 输出: 8（元素5在该数组中的索引）
//
//
// 示例2: 
//
//  输入：arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 11
// 输出：-1 （没有找到）
// 
//
// 提示: 
//
// 
// arr 长度范围在[1, 1000000]之间 
// 
// Related Topics 数组 二分查找 
// 👍 38 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int search(int[] arr, int target) {
        int left = 0, right = arr.length - 1, ans = Integer.MAX_VALUE;
        while (left < right) {
            if (arr[left] == target) {
                return left;
            }
            int pivot = left + ((right - left) >> 1);
            if (arr[pivot] == target) {
                while (pivot >= left && arr[pivot] == target) {
                    pivot--;
                }
                return pivot + 1;
            }
            if (arr[pivot] > arr[left]) {
                if (arr[pivot] < target) {
                    left = pivot + 1;
                }
                if (arr[pivot] > target) {
                    if (arr[left] > target) {
                        left = pivot + 1;
                    } else {
                        right = pivot - 1;
                    }
                }
            } else if (arr[pivot] < arr[left]) {
                if (arr[pivot] > target) {
                    right = pivot - 1;
                }
                if (arr[pivot] < target) {
                    if (arr[left] < target) {
                        right = pivot - 1;
                    } else {
                        left = pivot + 1;
                    }
                }
            } else if (arr[pivot] == arr[left]) {
                if (arr[pivot + 1] == arr[pivot]) {
                    right = pivot - 1;
                } else {
                    left++;
                }
            }
        }
        return arr[left] == target ? left : -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
