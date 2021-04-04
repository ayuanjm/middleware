//给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。注意：n-m尽量最小，也就是说，找出符合条件的最短
//序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。 
// 示例： 
// 输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
//输出： [3,9]
// 
// 提示： 
// 
// 0 <= len(array) <= 1000000 
// 
// Related Topics 排序 数组 
// 👍 64 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] subSort(int[] array) {
        int n = array.length, first = -1, last = -1, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            if (array[i] >= max) {
                max = array[i];
            } else {
                last = i;
            }
            if (array[n - i - 1] <= min) {
                min = array[n - i - 1];
            } else {
                first = n - i - 1;
            }
        }
        return new int[]{first, last};
    }
}
//leetcode submit region end(Prohibit modification and deletion)
