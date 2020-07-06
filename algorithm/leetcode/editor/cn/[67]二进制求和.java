//给你两个二进制字符串，返回它们的和（用二进制表示）。 
//
// 输入为 非空 字符串且只包含数字 1 和 0。 
//
// 
//
// 示例 1: 
//
// 输入: a = "11", b = "1"
//输出: "100" 
//
// 示例 2: 
//
// 输入: a = "1010", b = "1011"
//输出: "10101" 
//
// 
//
// 提示： 
//
// 
// 每个字符串仅由字符 '0' 或 '1' 组成。 
// 1 <= a.length, b.length <= 10^4 
// 字符串如果不是 "0" ，就都不含前导零。 
// 
// Related Topics 数学 字符串


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addBinary(String a, String b) {
        StringBuffer ans = new StringBuffer();
        int aa = a.length(), bb = b.length();
        int max = Integer.max(aa, bb), carry = 0;
        for (int i = 0; i < max; i++) {
            //char转int: char - '0';'0' = 48
            carry += i < aa ? (a.charAt(aa - i - 1) - '0') : 0;
            carry += i < bb ? (b.charAt(bb - i - 1) - '0') : 0;
            //ans.append(carry % 2);使用int直接拼接效率低，内部还是会转为char
            //int转char: int + '0';
            ans.append((char) (carry % 2 + '0'));
            carry = carry / 2;
        }
        //如果两个字符串的长度相等，可能出现第一位，也就是高位相加再进一位
        if (carry > 0) {
            ans.append('1');
        }
        //反转字符串
        ans.reverse();
        return ans.toString();
    }

    private String getString(String a, String b) {
        StringBuffer ans = new StringBuffer();

        int n = Math.max(a.length(), b.length()), carry = 0;
        for (int i = 0; i < n; ++i) {
            //char转int: char - '0';'0' = 48
            carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
            carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
            //int转char: int + '0';
            ans.append((char) (carry % 2 + '0'));
            carry /= 2;
        }

        if (carry > 0) {
            ans.append('1');
        }
        //反转字符串
        ans.reverse();

        return ans.toString();
    }
}

//leetcode submit region end(Prohibit modification and deletion)
