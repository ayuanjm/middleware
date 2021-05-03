//给你一个整数数组 nums 和一个整数 x 。每一次操作时，你应当移除数组 nums 最左边或最右边的元素，然后从 x 中减去该元素的值。请注意，需要 修改
// 数组以供接下来的操作使用。 
//
// 如果可以将 x 恰好 减到 0 ，返回 最小操作数 ；否则，返回 -1 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,4,2,3], x = 5
//输出：2
//解释：最佳解决方案是移除后两个元素，将 x 减到 0 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [5,6,7,8,9], x = 4
//输出：-1
// 
//
// 示例 3： 
//
// 
//输入：nums = [3,2,20,1,1,3], x = 10
//输出：5
//解释：最佳解决方案是移除后三个元素和前两个元素（总共 5 次操作），将 x 减到 0 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// 1 <= nums[i] <= 104 
// 1 <= x <= 109 
// 
// Related Topics 贪心算法 双指针 二分查找 Sliding Window 
// 👍 58 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length, l = 0, r = 0, sum = 0, difSum = 0, targetSum = 0, count = -1;
        for (int i : nums) {
            sum += i;
        }
        difSum = sum - x;
        if (difSum < 0) {
            return -1;
        }
        if (difSum == 0) {
            return nums.length;
        }

        while (r < n) {
            targetSum += nums[r++];
            while (targetSum > difSum) {
                targetSum -= nums[l++];
            }
            if (targetSum == difSum) {
                count = count > r - l ? count : r - l;
            }
        }
        return count == -1 ? count : n - count;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
