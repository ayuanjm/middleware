//给定一个非负整数数组 A，返回一个数组，在该数组中， A 的所有偶数元素之后跟着所有奇数元素。 
//
// 你可以返回满足此条件的任何数组作为答案。 
//
// 
//
// 示例： 
//
// 输入：[3,1,2,4]
//输出：[2,4,3,1]
//输出 [4,2,3,1]，[2,4,1,3] 和 [4,2,1,3] 也会被接受。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= A.length <= 5000 
// 0 <= A[i] <= 5000 
// 
// Related Topics 数组 
// 👍 194 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] sortArrayByParity(int[] A) {
        int head = 0;
        int tail = A.length - 1;
        while (head < tail) {
            if ((A[head] & 1) > (A[tail] & 1)) {
                A[head] ^= A[tail];
                A[tail] ^= A[head];
                A[head] ^= A[tail];
            }
            if ((A[head] & 1) == 0) {
                head++;
            }
            if ((A[tail] & 1) == 1) {
                tail--;
            }
        }
        return A;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
