//给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。在执行上述操作后，找到包含重复字母的最长子串的长度。
// 
//
// 注意：字符串长度 和 k 不会超过 104。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ABAB", k = 2
//输出：4
//解释：用两个'A'替换为两个'B',反之亦然。
// 
//
// 示例 2： 
//
// 
//输入：s = "AABABBA", k = 1
//输出：4
//解释：
//将中间的一个'A'替换为'B',字符串变为 "AABBBBA"。
//子串 "BBBB" 有最长重复字母, 答案为 4。
// 
// Related Topics 双指针 Sliding Window 
// 👍 437 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int ans = 0, l = 0, r = 0, length = s.length(), max = 0;
        while (r < length) {
            int charR = s.charAt(r) - 'A';
            ++count[charR];
            max = getMax(count);
            if (r - l + 1 <= max + k) {
                ans = ans > r - l + 1 ? ans : r - l + 1;
            } else if (r - l + 1 > max + k) {
                while (r - l + 1 > getMax(count) + k) {
                    int charL = s.charAt(l++) - 'A';
                    --count[charL];
                }
                if (r - l + 1 == getMax(count) + k) {
                    ans = ans > r - l + 1 ? ans : r - l + 1;
                }
            }
            ++r;
        }
        return ans;
    }

    public int getMax(int[] count) {
        int max = -1;
        for (int x : count) {
            max = max > x ? max : x;
        }
        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

