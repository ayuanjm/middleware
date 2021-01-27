//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。 
//
// 说明：本题中，我们将空字符串定义为有效的回文串。 
//
// 示例 1: 
//
// 输入: "A man, a plan, a canal: Panama"
//输出: true
// 
//
// 示例 2: 
//
// 输入: "race a car"
//输出: false
// 
// Related Topics 双指针 字符串 
// 👍 318 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        s = s.toLowerCase();
        while (i < j) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9') && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                i++;
                continue;
            }
            if (!(s.charAt(j) >= '0' && s.charAt(j) <= '9') && !(s.charAt(j) >= 'a' && s.charAt(j) <= 'z')) {
                j--;
                continue;
            }

            if (s.charAt(i++) != (s.charAt(j--))) {
                return false;
            }
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
