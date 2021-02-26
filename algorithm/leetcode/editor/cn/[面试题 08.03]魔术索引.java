//魔术索引。 在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。给定一个有序整数数组，编写一种方法找出魔术索引，若有的话，在数组A中找
//出一个魔术索引，如果没有，则返回-1。若有多个魔术索引，返回索引值最小的一个。 
//
// 示例1: 
//
//  输入：nums = [0, 2, 3, 4, 5]
// 输出：0
// 说明: 0下标的元素为0
// 
//
// 示例2: 
//
//  输入：nums = [1, 1, 1]
// 输出：1
// 
//
// 说明: 
//
// 
// nums长度在[1, 1000000]之间 
// 此题为原书中的 Follow-up，即数组中可能包含重复元素的版本 
// 
// Related Topics 数组 二分查找 
// 👍 82 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findMagicIndex(int[] nums) {
        int left = 0, right = nums.length - 1, ans = Integer.MAX_VALUE;
        return dfs(nums, left, right, ans);
    }

    public static int dfs(int[] nums, int left, int right, int ans) {
        while (left < right) {
            int pivot = left + ((right - left) >> 1);
            if (nums[pivot] == pivot) {
                ans = ans < pivot ? ans : pivot;
                right = pivot - 1;
            }
            int ansLeft = dfs(nums, left, pivot - 1, ans);
            if (ansLeft == -1) {
                return dfs(nums, pivot + 1, right, ans);
            } else {
                return ansLeft;
            }
        }
        ans = (nums[left] == left) ? (ans > left ? left : (ans == Integer.MAX_VALUE ? -1 : ans)) : (ans == Integer.MAX_VALUE ? -1 : ans);
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
