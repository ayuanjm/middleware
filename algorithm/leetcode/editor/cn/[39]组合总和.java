//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的数字可以无限制重复被选取。 
//
// 说明： 
//
// 
// 所有数字（包括 target）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1： 
//
// 输入：candidates = [2,3,6,7], target = 7,
//所求解集为：
//[
//  [7],
//  [2,2,3]
//]
// 
//
// 示例 2： 
//
// 输入：candidates = [2,3,5], target = 8,
//所求解集为：
//[
//  [2,2,2,2],
//  [2,3,3],
//  [3,5]
//] 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯算法 
// 👍 802 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, ans, new LinkedList<>(), 0);
        return ans;
    }

    private void dfs(int[] candidates, int target, List ans, List<Integer> curr, int start) {
        if (target == 0) {
            ans.add(curr);
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (target < candidates[i]) {
                break;
            }
            //新创建一个list，每个分支用自己的list，如果不创建那么大家共用一个，添加的元素都到一起去了
            List<Integer> list = new LinkedList<>(curr);
            list.add(candidates[i]);
            //start必须等于i，因为下一次的循环需要以上一次的i开始，不然会出现重复的
            dfs(candidates, target - candidates[i], ans, list, i);
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)
