//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例： 
//
// 输入：n = 3
//输出：[
//       "((()))",
//       "(()())",
//       "(())()",
//       "()(())",
//       "()()()"
//     ]
// 
// Related Topics 字符串 回溯算法


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<String> generateParenthesis(int n) {
        LinkedList<String> result = new LinkedList();
        generate("", n, n, result);
        return result;
    }

    public void generate(String item, int left, int right, List<String> result) {
        if (left == 0 && right == 0) {
            result.add(item);
            return;
        }
        if (left > 0) {
            generate(item + "(", left - 1, right, result);
        }
        if (left < right) {
            generate(item + ")", left, right - 1, result);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
