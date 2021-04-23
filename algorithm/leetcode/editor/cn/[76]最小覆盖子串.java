//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。 
//
// 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
// 
//
// 示例 2： 
//
// 
//输入：s = "a", t = "a"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length, t.length <= 105 
// s 和 t 由英文字母组成 
// 
//
// 
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 1125 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public static String minWindow(String s, String t) {
        int left = 0, right = 0, start = -1, end = -1, ansLen = Integer.MAX_VALUE,
                count = 0, tLen = t.length(), sLen = s.length();
        HashMap<Character, Integer> tMap = new HashMap(16);
        HashMap<Character, Integer> sMap = new HashMap(16);
        for (int i = 0; i < tLen; ++i) {
            char c = t.charAt(i);
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }
        while (right < sLen) {
            char target = s.charAt(right);
            if (tMap.containsKey(target)) {
                sMap.put(target, sMap.getOrDefault(target, 0) + 1);
                if (sMap.get(target).equals(tMap.get(target))) {
                    ++count;
                }
                while (count == tMap.size() && left <= right) {
                    if (right - left + 1 < ansLen) {
                        ansLen = right - left + 1;
                        start = left;
                        end = right;
                    }
                    char temp = s.charAt(left);
                    if (sMap.containsKey(temp)) {
                        sMap.put(temp, sMap.get(temp) - 1);
                        if (sMap.get(temp) < tMap.get(temp)) {
                            count--;
                        }
                    }
                    ++left;
                }
            }
            ++right;
        }
        return start == -1 ? "" : s.substring(start, end + 1);
    }
}
