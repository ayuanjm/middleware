//给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。例如，如果一个字符在每个字符串中出现 3 次，但不
//是 4 次，则需要在最终答案中包含该字符 3 次。 
//
// 你可以按任意顺序返回答案。 
//
// 
//
// 示例 1： 
//
// 输入：["bella","label","roller"]
//输出：["e","l","l"]
// 
//
// 示例 2： 
//
// 输入：["cool","lock","cook"]
//输出：["c","o"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= A.length <= 100 
// 1 <= A[i].length <= 100 
// A[i][j] 是小写字母 
// 
// Related Topics 数组 哈希表 
// 👍 199 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<String> commonChars(String[] A) {
        LinkedList<String> ans = new LinkedList<>();
        int[] count = new int[26];
        for (int j = 0; j < A[0].length(); j++) {
            count[A[0].charAt(j) - 'a']++;
        }
        for (int i = 1; i < A.length; i++) {
            HashMap<Character, Integer> hashMap = new HashMap<>(32);
            String str = A[i];
            for (int j = 0; j < str.length(); j++) {
                hashMap.put(str.charAt(j), hashMap.getOrDefault(str.charAt(j), 0) + 1);
            }
            for (int k = 0; k < count.length; k++) {
                if (count[k] != 0) {
                    count[k] = count[k] > hashMap.getOrDefault((char) (k + 'a'), 0) ? hashMap.getOrDefault((char) (k + 'a'), 0) : count[k];
                }
            }
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                for (int j = 0; j < count[i]; j++) {
                    ans.add(String.valueOf((char) (i + 'a')));
                }
            }
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
