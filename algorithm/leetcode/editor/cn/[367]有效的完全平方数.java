//给定一个正整数 num，编写一个函数，如果 num 是一个完全平方数，则返回 True，否则返回 False。 
//
// 说明：不要使用任何内置的库函数，如 sqrt。 
//
// 示例 1： 
//
// 输入：16
//输出：True 
//
// 示例 2： 
//
// 输入：14
//输出：False
// 
// Related Topics 数学 二分查找 
// 👍 194 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isPerfectSquare(int num) {
        if (num < 2) {
            return true;
        }
        long left = 2, right = num >> 1, pivot;
        while (left <= right) {
            pivot = left + ((right - left) >> 1);
            if (pivot * pivot == num) {
                return true;
            }
            if (pivot * pivot > num) {
                right = pivot - 1;
            }
            if (pivot * pivot < num) {
                left = pivot + 1;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
