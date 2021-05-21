//给定两个整数数组，请交换一对数值（每个数组中取一个数值），使得两个数组所有元素的和相等。 
//
// 返回一个数组，第一个元素是第一个数组中要交换的元素，第二个元素是第二个数组中要交换的元素。若有多个答案，返回任意一个均可。若无满足条件的数值，返回空数组。
// 
//
// 示例: 
//
// 输入: array1 = [4, 1, 2, 1, 1, 2], array2 = [3, 6, 3, 3]
//输出: [1, 3]
// 
//
// 示例: 
//
// 输入: array1 = [1, 2, 3], array2 = [4, 5, 6]
//输出: [] 
//
// 提示： 
//
// 
// 1 <= array1.length, array2.length <= 100000 
// 
// Related Topics 排序 数组 
// 👍 24 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] findSwapValues(int[] array1, int[] array2) {
        int sum1, sum2, diff;
        sum1 = Arrays.stream(array1).sum();
        sum2 = Arrays.stream(array2).sum();
        diff = sum1 - sum2;
        if ((diff & 1) == 1) {
            return new int[]{};
        }
        diff = diff >> 1;
        HashSet<Integer> set = new HashSet<>();
        for (int x : array1) {
            set.add(x);
        }

        for (int x : array2) {
            if (set.contains(x + diff)) {
                return new int[]{x + diff, x};
            }
        }
        return new int[]{};
    }
}
//leetcode submit region end(Prohibit modification and deletion)
