//给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。 
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 进阶： 
//
// 
// 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 105 
// -109 <= nums[i] <= 109 
// nums 是一个非递减数组 
// -109 <= target <= 109 
// 
// Related Topics 数组 二分查找 
// 👍 909 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = 0, right = nums.length - 1, middle, fastPosition = -1, lastPosition = -1;
        fastPosition = getFastPosition(nums, target, left, right, fastPosition);
        if (fastPosition == -1) {
            return new int[]{-1, -1};
        }
        lastPosition = getLastPosition(nums, target, lastPosition);
        return new int[]{fastPosition, lastPosition};
    }

    private int getFastPosition(int[] nums, int target, int left, int right, int fastPosition) {
        int middle;
        while (left < right) {
            middle = left + ((right - left) >> 1);
            if (nums[middle] > target) {
                right = middle;
            } else if (nums[middle] == target) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        if (nums[left] == target) {
            fastPosition = left;
        }
        return fastPosition;
    }

    private int getLastPosition(int[] nums, int target, int lastPosition) {
        int left;
        int right;
        int middle;
        left = 0;
        right = nums.length - 1;
        while (left < right) {
            //right - left + 1 防止left==middle时死循环
            middle = left + ((right - left + 1) >> 1);
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] == target) {
                left = middle;
            } else {
                left = middle + 1;
            }
        }
        if (nums[left] == target) {
            lastPosition = left;
        }
        return lastPosition;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
