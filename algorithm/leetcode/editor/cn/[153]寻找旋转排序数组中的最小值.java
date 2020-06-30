//假设按照升序排序的数组在预先未知的某个点上进行了旋转。 
//
// ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。 
//
// 请找出其中最小的元素。 
//
// 你可以假设数组中不存在重复元素。 
//
// 示例 1: 
//
// 输入: [3,4,5,1,2]
//输出: 1 
//
// 示例 2: 
//
// 输入: [4,5,6,7,0,1,2]
//输出: 0 
// Related Topics 数组 二分查找

/**
 * 变化点(最小元素)的特点：
 * 所有变化点左侧元素 > 数组第一个元素
 * 所有变化点右侧元素 < 数组第一个元素
 */
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (nums[middle] < nums[right]) {
                // middle在最小值的右边或者是最小值
                right = middle;
                //判断middle是不是最小值，避免已经找到最小值了，还在二分查找。注意middle - 1下标越界。
                if (middle - 1 > 0 && nums[middle] < nums[middle - 1] && nums[middle] < nums[middle + 1]) {
                    //只有最小值会比左边的小,同时比右边的大
                    return nums[middle];
                }
            } else {
                // middle在最小值的左边，肯定不是最小值
                left = middle + 1;
            }
        }
        return nums[left];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
