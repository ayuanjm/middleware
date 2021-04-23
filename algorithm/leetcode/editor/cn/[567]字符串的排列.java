//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。 
//
// 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。 
//
// 
//
// 示例 1： 
//
// 
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
// 
//
// 示例 2： 
//
// 
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
// 
//
// 
//
// 提示： 
//
// 
// 输入的字符串只包含小写字母 
// 两个字符串的长度都在 [1, 10,000] 之间 
// 
// Related Topics 双指针 Sliding Window 
// 👍 345 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int length1 = s1.length(), length2 = s2.length();
        if (length1 > length2) {
            return false;
        }
        int[] arr = new int[26];
        for (int i = 0; i < length1; ++i) {
            ++arr[s1.charAt(i) - 'a'];
        }
        int left = 0, right = 0;
        while (right < length2) {
            int x = s2.charAt(right) - 'a';
            --arr[x];
            while (arr[x] < 0) {
                ++arr[s2.charAt(left++) - 'a'];
            }
            if (right - left + 1 == length1) {
                return true;
            }
            right++;
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
