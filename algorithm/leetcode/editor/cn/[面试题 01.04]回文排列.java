//给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。 
//
// 回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。 
//
// 回文串不一定是字典当中的单词。 
//
// 
//
// 示例1： 
//
// 输入："tactcoa"
//输出：true（排列有"tacocat"、"atcocta"，等等）
// 
//
// 
// Related Topics 哈希表 字符串 
// 👍 42 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean canPermutePalindrome(String s) {
        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>(16);
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int size = map.entrySet().stream().filter(o -> o.getValue() % 2 == 1).collect(Collectors.toList()).size();
        return size > 1 ? false : true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
