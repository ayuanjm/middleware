//编写一个函数，以字符串作为输入，反转该字符串中的元音字母。 
//
// 
//
// 示例 1： 
//
// 输入："hello"
//输出："holle"
// 
//
// 示例 2： 
//
// 输入："leetcode"
//输出："leotcede" 
//
// 
//
// 提示： 
//
// 
// 元音字母不包含字母 "y" 。 
// 
// Related Topics 双指针 字符串 
// 👍 137 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String reverseVowels(String s) {
        char[] str = s.toCharArray();
        for (int i = str.length - 1, j = 0; i > j; i--) {
            if (isVowel(str[i])) {
                while (i > j) {
                    if (isVowel(str[j])) {
                        char temp = str[i];
                        str[i] = str[j];
                        str[j] = temp;
                        j++;
                        break;
                    }
                    j++;
                }
            }
        }
        return String.valueOf(str);
    }
    // 判断是不是元音
    private boolean isVowel(char ch) {
        // 这里要直接用 return 语句返回，不要返回 true 或者 false
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                ||ch=='A'|| ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U';
    }
}
//leetcode submit region end(Prohibit modification and deletion)
