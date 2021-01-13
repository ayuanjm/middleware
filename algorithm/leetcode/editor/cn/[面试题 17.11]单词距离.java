//有个内含单词的超大文本文件，给定任意两个单词，找出在这个文件中这两个单词的最短距离(相隔单词数)。如果寻找过程在这个文件中会重复多次，而每次寻找的单词不同，
//你能对此优化吗? 
//
// 示例： 
//
// 输入：words = ["I","am","a","student","from","a","university","in","a","city"], 
//word1 = "a", word2 = "student"
//输出：1 
//
// 提示： 
//
// 
// words.length <= 100000 
// 
// Related Topics 双指针 字符串 
// 👍 17 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findClosest(String[] words, String word1, String word2) {
        int min = words.length;
        int i = 0;
        int j = 0;
        while (i < words.length - 1 && j < words.length) {
            j = i + 1;
            if (words[i].equals(word1) || words[i].equals(word2)) {
                String mark = words[i].equals(word1) ? word2 : word1;
                while (j < words.length) {
                    if (words[j].equals(words[i])) {
                        i = j;
                        break;
                    }
                    if (words[j].equals(mark)) {
                        min = min < j - i ? min : j - i;
                        i = j;
                        break;
                    }
                    j++;
                }
            } else {
                i++;
            }
        }
        return min;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
