//实现一个算法，确定一个字符串 s 的所有字符是否全都不同。 
//
// 示例 1： 
//
// 输入: s = "leetcode"
//输出: false 
// 
//
// 示例 2： 
//
// 输入: s = "abc"
//输出: true
// 
//
// 限制： 
// 
// 0 <= len(s) <= 100 
// 如果你不使用额外的数据结构，会很加分。 
// 
// Related Topics 数组 
// 👍 72 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isUnique(String astr) {
        Set<Character> set = new HashSet<>();
        for (int i=0;i<astr.length();i++) {
            if (set.contains(astr.charAt(i))) {
                return false;
            }
            set.add(astr.charAt(i));
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
