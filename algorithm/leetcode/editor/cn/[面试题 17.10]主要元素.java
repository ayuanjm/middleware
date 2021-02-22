//数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。 
//
// 示例 1： 
//
// 输入：[1,2,5,9,5,9,5,5,5]
//输出：5 
//
// 
//
// 示例 2： 
//
// 输入：[3,2]
//输出：-1 
//
// 
//
// 示例 3： 
//
// 输入：[2,2,1,1,1,2,2]
//输出：2 
//
// 
//
// 说明： 
//你有办法在时间复杂度为 O(N)，空间复杂度为 O(1) 内完成吗？ 
// Related Topics 位运算 数组 分治算法 
// 👍 67 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>(16);
        int n = nums.length;
        int flag = n % 2 == 0 ? n / 2 : n / 2 + 1;
        for (int i = 0; i < n; i++) {
            int ans = map.getOrDefault(nums[i], 0) + 1;
            if (ans >= flag) {
                return nums[i];
            }
            map.put(nums[i], ans);
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
