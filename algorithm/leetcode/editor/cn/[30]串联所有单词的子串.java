//给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。 
//
// 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。 
//
// 
//
// 示例 1： 
//
// 输入：
//  s = "barfoothefoobarman",
//  words = ["foo","bar"]
//输出：[0,9]
//解释：
//从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
//输出的顺序不重要, [9,0] 也是有效答案。
// 
//
// 示例 2： 
//
// 输入：
//  s = "wordgoodgoodgoodbestword",
//  words = ["word","good","best","word"]
//输出：[]
// 
// Related Topics 哈希表 双指针 字符串 
// 👍 459 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new LinkedList<>();
        int wordCount = words.length, wordLength = words[0].length();
        HashMap<String, Integer> wordMap = new HashMap<>(16);
        for (String str : words) {
            wordMap.put(str, wordMap.getOrDefault(str, 0) + 1);
        }
        for (int i = 0; i <= s.length() - wordLength * wordCount; ++i) {
            int count = 0;
            HashMap<String, Integer> sMap = new HashMap<>(16);
            while (count < wordCount) {
                String target = s.substring(i + count * wordLength, i + (count + 1) * wordLength);
                if (wordMap.containsKey(target)) {
                    sMap.put(target, sMap.getOrDefault(target, 0) + 1);
                    if (sMap.get(target) > wordMap.get(target)) {
                        break;
                    }
                    ++count;
                    continue;
                }
                break;
            }
            if (count == wordCount) {
                ans.add(i);
            }
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
