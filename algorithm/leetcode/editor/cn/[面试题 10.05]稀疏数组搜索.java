//稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，找出给定字符串的位置。 
//
// 示例1: 
//
//  输入: words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""],
// s = "ta"
// 输出：-1
// 说明: 不存在返回-1。
// 
//
// 示例2: 
//
//  输入：words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], 
//s = "ball"
// 输出：4
// 
//
// 提示: 
//
// 
// words的长度在[1, 1000000]之间 
// 
// Related Topics 二分查找 
// 👍 40 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findString(String[] words, String s) {
        int left = 0, right = words.length - 1, pivot;
        while (left < right) {
            pivot = left + ((right - left) >> 1);
            if (words[pivot].compareTo(s) == 0) {
                return pivot;
            }
            int temp = pivot;
            if (words[pivot].equals("")) {
                while (pivot <= right && words[pivot].equals("")) {
                    pivot++;
                }
                if (pivot > right) {
                    right = temp;
                    continue;
                }
            }
            if (words[pivot].compareTo(s) > 0) {
                right = pivot - 1;
            }
            if (words[pivot].compareTo(s) < 0) {
                left = pivot + 1;
            }
            if (words[pivot].compareTo(s) == 0) {
                return pivot;
            }
        }
        return words[left].equals(s) ? left : -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
