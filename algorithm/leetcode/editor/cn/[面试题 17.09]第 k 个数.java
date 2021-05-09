//有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，
//5，7，9，15，21。 
//
// 示例 1: 
//
// 输入: k = 5
//
//输出: 9
// 
// Related Topics 堆 队列 数学 
// 👍 58 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int getKthMagicNumber(int k) {
        int ugly[] = new int[]{3, 5, 7};
        HashSet<Long> hashSet = new HashSet<>(16);
        hashSet.add(1L);
        PriorityQueue<Long> priorityQueue = new PriorityQueue<Long>();
        priorityQueue.offer(1L);
        for (int i = 0; i < k - 1; ++i) {
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
