//给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。 
//
// 注意： 
//
// 
// num1 和num2 的长度都小于 5100. 
// num1 和num2 都只包含数字 0-9. 
// num1 和num2 都不包含任何前导零。 
// 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。 
// 
// Related Topics 字符串 
// 👍 187 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuffer ans = new StringBuffer();
        int length1 = num1.length() - 1;
        int length2 = num2.length() - 1;
        int x = 0;
        while (length1 >= 0 || length2 >= 0) {
            x += length1 >= 0 ? num1.charAt(length1--) - '0' : 0;
            x += length2 >= 0 ? num2.charAt(length2--) - '0' : 0;
            ans.append(x % 10);
            x = x / 10;
        }

        if (x == 1) {
            ans.append(1);
        }
        ans.reverse();
        return ans.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
