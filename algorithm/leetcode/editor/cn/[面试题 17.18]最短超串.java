//假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。 
//
// 返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组。 
//
// 示例 1: 
//
// 输入:
//big = [7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7]
//small = [1,5,9]
//输出: [7,10] 
//
// 示例 2: 
//
// 输入:
//big = [1,2,3]
//small = [4]
//输出: [] 
//
// 提示： 
//
// 
// big.length <= 100000 
// 1 <= small.length <= 100000 
// 
// Related Topics Sliding Window 
// 👍 27 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] shortestSeq(int[] big, int[] small) {
        if (big.length < small.length) {
            return new int[0];
        }
        HashMap<Integer, Integer> map = new HashMap<>(16);
        for (int x : small) {
            map.put(x, 1);
        }
        int left = 0, right = 0, bigLen = big.length, count = small.length;
        int[] ans = {-1, bigLen};
        while (right < bigLen) {
            int rightVal = big[right];
            if (map.containsKey(rightVal)) {
                map.put(rightVal, map.get(rightVal) - 1);
                if (map.get(rightVal) == 0) {
                    --count;
                }
            }
            while (count == 0) {
                if ((ans[1] - ans[0]) > (right - left)) {
                    ans[0] = left;
                    ans[1] = right;
                }
                int leftVal = big[left];
                if (map.containsKey(leftVal)) {
                    map.put(leftVal, map.get(leftVal) + 1);
                    if (map.get(leftVal) == 1) {
                        ++count;
                    }
                }
                ++left;
            }
            ++right;
        }
        return ans[0] == -1 ? new int[0] : ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
