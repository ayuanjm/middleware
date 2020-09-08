//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。 
//
// 示例 1: 
//
// 输入: "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
// Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 4140 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int length = s.length();
        //窗口右指针
        int right = 0;
        int ans = 0;
        Set set = new HashSet<Character>();
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                set.remove(s.charAt(i - 1));
            }
            if (right >= length){
                return ans;
            }
            if (set.contains(s.charAt(right))) {
                //存在相等长度为：right - i
                ans = Math.max(ans, right - i);
                continue;
            }
            while (right < length && !set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;
            }
            //不存在相等或者right=length长度为：right - i，因为right多加了1，例如：pwke  right = 4；
            ans = Math.max(ans, right - i);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
