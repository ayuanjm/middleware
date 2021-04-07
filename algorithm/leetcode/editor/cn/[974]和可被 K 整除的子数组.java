//给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。 
//
// 
//
// 示例： 
//
// 输入：A = [4,5,0,-2,-3,1], K = 5
//输出：7
//解释：
//有 7 个子数组满足其元素之和可被 K = 5 整除：
//[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= A.length <= 30000 
// -10000 <= A[i] <= 10000 
// 2 <= K <= 10000 
// 
// Related Topics 数组 哈希表 
// 👍 252 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        HashMap<Integer, Integer> hashMap = new HashMap<>(16);
        int ans = 0, sum = 0, modulus;
        for (int x : A) {
            sum += x;
            modulus = (sum % K + K) % K;
            if (modulus == 0) {
                ans += 1;
            }
            ans += hashMap.getOrDefault(modulus, 0);
            hashMap.put(modulus, hashMap.getOrDefault(modulus, 0) + 1);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
