//在由若干 0 和 1 组成的数组 A 中，有多少个和为 S 的非空子数组。 
//
// 
//
// 示例： 
//
// 输入：A = [1,0,1,0,1], S = 2
//输出：4
//解释：
//如下面黑体所示，有 4 个满足题目要求的子数组：
//[1,0,1,0,1]
//[1,0,1,0,1]
//[1,0,1,0,1]
//[1,0,1,0,1]
// 
//
// 
//
// 提示： 
//
// 
// A.length <= 30000 
// 0 <= S <= A.length 
// A[i] 为 0 或 1 
// 
// Related Topics 哈希表 双指针 
// 👍 94 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int numSubarraysWithSum(int[] A, int S) {
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>(16);
        map.put(0, 1);
        for (int x : A) {
            sum += x;
            int target = sum - S;
            ans += map.getOrDefault(target, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
