//给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。 
//
// 示例 1: 
//
// 输入: num1 = "2", num2 = "3"
//输出: "6" 
//
// 示例 2: 
//
// 输入: num1 = "123", num2 = "456"
//输出: "56088" 
//
// 说明： 
//
// 
// num1 和 num2 的长度小于110。 
// num1 和 num2 只包含数字 0-9。 
// num1 和 num2 均不以零开头，除非是数字 0 本身。 
// 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。 
// 
// Related Topics 数学 字符串 
// 👍 564 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length() - 1, n = num2.length() - 1;
        String sum = "0";
        StringBuilder zero1 = new StringBuilder();
        for (int i = m; i >= 0; i--) {
            StringBuilder zero2 = new StringBuilder();
            for (int j = n; j >= 0; j--) {
                StringBuilder builder = new StringBuilder();
                builder.append((num1.charAt(i) - '0') * (num2.charAt(j) - '0')).append(zero1).append(zero2);
                sum = addStrings(sum, builder.toString());
                zero2.append('0');
            }
            zero1.append('0');
        }
        return sum;
    }

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
