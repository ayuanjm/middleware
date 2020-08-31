//给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和
//。假定每组输入只存在唯一答案。 
//
// 
//
// 示例： 
//
// 输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
// 
//
// 
//
// 提示： 
//
// 
// 3 <= nums.length <= 10^3 
// -10^3 <= nums[i] <= 10^3 
// -10^4 <= target <= 10^4 
// 
// Related Topics 数组 双指针 
// 👍 556 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        int best = 100000;
        //排序
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            //与前一个元素相同的跳过
            if (i >= 1 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] == target) {
                    return target;
                }

                if (left < right && nums[i] + nums[left] + nums[right] < target) {
                    while (left < right && nums[i] + nums[left] + nums[right] < target) {
                        left++;
                    }
                    int temp = nums[i] + nums[left - 1] + nums[right];
                    if (Math.abs(target - temp) < Math.abs(target - best)) {
                        best = temp;
                    }
                }

                if (left < right && nums[i] + nums[left] + nums[right] > target) {
                    while (left < right && nums[i] + nums[left] + nums[right] > target) {
                        right--;
                    }
                    int temp = nums[i] + nums[left] + nums[right + 1];
                    if (Math.abs(target - temp) < Math.abs(target - best)) {
                        best = temp;
                    }
                }

            }

        }
        return best;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
