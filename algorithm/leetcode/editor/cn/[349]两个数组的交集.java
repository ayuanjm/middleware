//给定两个数组，编写一个函数来计算它们的交集。 
//
// 
//
// 示例 1： 
//
// 输入：nums1 = [1,2,2,1], nums2 = [2,2]
//输出：[2]
// 
//
// 示例 2： 
//
// 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//输出：[9,4] 
//
// 
//
// 说明： 
//
// 
// 输出结果中的每个元素一定是唯一的。 
// 我们可以不考虑输出结果的顺序。 
// 
// Related Topics 排序 哈希表 双指针 二分查找 
// 👍 210 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Map map = new HashMap<Integer, Integer>();
        for (int a : nums1) {
            map.put(a, 1);
        }
        int[] temp = new int[nums2.length];
        int k = 0;
        for (int i = 0; i < nums2.length; i++) {
            if (map.containsKey(nums2[i])) {
                temp[k++] = nums2[i];
                map.remove(nums2[i]);
            }
        }
        return Arrays.copyOf(temp,k);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
