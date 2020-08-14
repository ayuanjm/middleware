//给出一个区间的集合，请合并所有重叠的区间。 
//
// 示例 1: 
//
// 输入: [[1,3],[2,6],[8,10],[15,18]]
//输出: [[1,6],[8,10],[15,18]]
//解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
// 
//
// 示例 2: 
//
// 输入: [[1,4],[4,5]]
//输出: [[1,5]]
//解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。 
// Related Topics 排序 数组 
// 👍 552 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[][] merge(int[][] intervals) {
        List<int[]> ans = new LinkedList<>();
        if (intervals.length == 0 || intervals == null) return ans.toArray(new int[0][]);
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int left = intervals[0][0];
        int right = intervals[0][1];
        if (ans.size() == 0) {
            ans.add(new int[]{left, right});
        }
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i + 1][0] > right) {
                ans.add(new int[]{intervals[i + 1][0], intervals[i + 1][1]});
                left = intervals[i + 1][0];
                right = intervals[i + 1][1];
            } else if (intervals[i + 1][1] > right) {
                ans.remove(ans.size() - 1);
                left = left;
                right = intervals[i + 1][1];
                ans.add(new int[]{left, right});
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
