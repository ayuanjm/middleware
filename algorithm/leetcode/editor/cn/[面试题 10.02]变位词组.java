//编写一种方法，对字符串数组进行排序，将所有变位词组合在一起。变位词是指字母相同，但排列不同的字符串。 
//
// 注意：本题相对原题稍作修改 
//
// 示例: 
//
// 输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
//输出:
//[
//  ["ate","eat","tea"],
//  ["nat","tan"],
//  ["bat"]
//] 
//
// 说明： 
//
// 
// 所有输入均为小写字母。 
// 不考虑答案输出的顺序。 
// 
// Related Topics 哈希表 字符串 
// 👍 18 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, Integer> hashMap = new HashMap<>(16);
        LinkedList<List<String>> ans = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < strs.length; i++) {
            char[] charsA = strs[i].toCharArray();
            Arrays.sort(charsA);
            String target = String.valueOf(charsA);
            if (!hashMap.containsKey(target)) {
                LinkedList<String> list = new LinkedList<>();
                list.add(strs[i]);
                hashMap.put(target, index++);
                ans.add(list);
            } else {
                ans.get(hashMap.get(target)).add(strs[i]);
            }
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
