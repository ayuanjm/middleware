//给定两个整数数组a和b，计算具有最小差绝对值的一对数值（每个数组中取一个值），并返回该对数值的差 
// 示例： 
// 输入：{1, 3, 15, 11, 2}, {23, 127, 235, 19, 8}
//输出： 3，即数值对(11, 8)
// 
// 提示： 
// 
// 1 <= a.length, b.length <= 100000 
// -2147483648 <= a[i], b[i] <= 2147483647 
// 正确结果在区间[-2147483648, 2147483647]内 
// 
// Related Topics 数组 双指针 
// 👍 21 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int smallestDifference(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        long min = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (Math.abs((long) a[i] - b[j]) < min) {
                min = Math.abs((long) a[i] - b[j]);
            }
            if (a[i] > b[j]) {
                j++;
            } else {
                i++;
            }
        }
        return (int) min;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
