//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。 
//
// 示例 1： 
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
// 
//
// 示例 2： 
//
// 输入: "cbbd"
//输出: "bb"
// 
// Related Topics 字符串 动态规划 
// 👍 2546 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
        String ans = "";
        int length = s.length();
        int max = 0;
        for (int i = 0; i < length; i++) {
            //需要j==length，因为substring截取的是i,j-1下标
            for (int j = i; j <= length; j++) {
                String str = s.substring(i, j);
                //长度判断需要放在前面，否则会超时，当长度小于max时不用判断回文
                if (max < str.length() && isPaPalindrome(str)) {
                    ans = str;
                    max = str.length();
                }
            }
        }
        return ans;
    }

    public boolean isPaPalindrome(String s) {
        int length = s.length();
        for (int i = 0; i < length / 2; i++) {
            if (s.charAt(i) != s.charAt(length - 1 - i)) {
                return false;
            }
        }
        //包含length==1的情况
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
