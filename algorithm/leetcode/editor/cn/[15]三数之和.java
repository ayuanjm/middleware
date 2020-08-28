//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复
//的三元组。 
//
// 注意：答案中不可以包含重复的三元组。 
//
// 
//
// 示例： 
//
// 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
//
//满足要求的三元组集合为：
//[
//  [-1, 0, 1],
//  [-1, -1, 2]
//]
// 
// Related Topics 数组 双指针 
// 👍 2523 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        //排序
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return ans;
            }
            //与前一个元素相同的跳过
            if (i >= 1 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] == 0) {
                    List<Integer> curr = new LinkedList<Integer>();
                    curr.add(nums[i]);
                    curr.add(nums[left]);
                    curr.add(nums[right]);
                    ans.add(curr);
                    //排序后相同的元素跳过
                    while (left < right && nums[left] == nums[++left]) ;
                    while (left < right && nums[right] == nums[--right]) ;

                }

                while (left < right && nums[i] + nums[left] + nums[right] < 0) {
                    left++;
                }
                while (left < right && nums[i] + nums[left] + nums[right] > 0) {
                    right--;
                }
            }

        }
        return ans;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
