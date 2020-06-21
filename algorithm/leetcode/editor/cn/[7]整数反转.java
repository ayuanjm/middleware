//给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。 
//
// 示例 1: 
//
// 输入: 123
//输出: 321
// 
//
// 示例 2: 
//
// 输入: -123
//输出: -321
// 
//
// 示例 3: 
//
// 输入: 120
//输出: 21
// 
//
// 注意: 
//
// 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231, 231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。 
// Related Topics 数学


class Solution {
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            /**
             * 运算符 / ：除法运算符，并且运算结果遵从向下取整，123/10=12
             * 运算符 % ：模运算，123%10=3
             * 可以对x取模得到最后一位，对x除法得到去除最后一位的数字再取模得到倒数第二位，依次类推。
             */
            int pop = x % 10;
            x = x / 10;
            //Integer.MAX_VALUE%10=7;Integer.MIN_VALUE%10=-8;
            //可能存在反转后溢出整数能表示的最大最小值
            if (rev > Integer.MAX_VALUE/10 || rev == Integer.MAX_VALUE/10 && pop > 7) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE/10 || rev == Integer.MIN_VALUE/10 && pop < -8) {
                return 0;
            }
            //rev需要*10才能把后一位加上来：3*10+2=32，32*10+1=321；123/10=12 12%10=2； 12/10=1 1%10=1；
            rev = rev * 10 + pop;
        }
        return rev;
    }

}
