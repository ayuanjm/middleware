//给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c +
// d 的值与 target 相等？找出所有满足条件且不重复的四元组。 
//
// 注意： 
//
// 答案中不可以包含重复的四元组。 
//
// 示例： 
//
// 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
//
//满足要求的四元组集合为：
//[
//  [-1,  0, 0, 1],
//  [-2, -1, 1, 2],
//  [-2,  0, 0, 2]
//]
// 
// Related Topics 数组 哈希表 双指针 
// 👍 551 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int min = nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (min > target) {
                break;
            }
            int max = nums[i] + nums[nums.length - 1] + nums[nums.length - 2] + nums[nums.length - 3];
            if (max < target) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > 0 && j != i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int minJ = nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (minJ > target) {
                    break;
                }
                int maxJ = nums[i] + nums[j] + nums[nums.length - 1] + nums[nums.length - 2];
                if (maxJ < target) {
                    continue;
                }

                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    if (left < right && (nums[i] + nums[j] + nums[left] + nums[right]) == target) {
                        LinkedList<Integer> curr = new LinkedList<Integer>();
                        curr.add(nums[i]);
                        curr.add(nums[j]);
                        curr.add(nums[left]);
                        curr.add(nums[right]);
                        ans.add(curr);
                        while (left < right && nums[left] == nums[++left]) ;
                        while (left < right && nums[right] == nums[--right]) ;
                    }
                    if (left < right && (nums[i] + nums[j] + nums[left] + nums[right]) > target) {
                        right--;
                    }
                    if (left < right && (nums[i] + nums[j] + nums[left] + nums[right]) < target) {
                        left++;
                    }
                }
            }
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
