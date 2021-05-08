//给你一个整数 n ，请你找出并返回第 n 个 丑数 。 
//
// 丑数 就是只包含质因数 2、3 和/或 5 的正整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 10
//输出：12
//解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：1
//解释：1 通常被视为丑数。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 1690 
// 
// Related Topics 堆 数学 动态规划 
// 👍 657 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int nthUglyNumber(int n) {
        int ugly[] = new int[]{2, 3, 5};
        HashSet<Long> hashSet = new HashSet<>(16);
        hashSet.add(1L);
        PriorityQueue<Long> priorityQueue = new PriorityQueue<Long>();
        priorityQueue.offer(1L);
        for (int i = 0; i < n - 1; ++i) {
            long curr = priorityQueue.poll();
            for (int x : ugly) {
                long next = curr * x;
                if (hashSet.add(next)) {
                    priorityQueue.offer(next);
                }
            }
        }
        return priorityQueue.poll().intValue();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
