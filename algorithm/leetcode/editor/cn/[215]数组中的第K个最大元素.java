//在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。 
//
// 示例 1: 
//
// 输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
// 
//
// 示例 2: 
//
// 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4 
//
// 说明: 
//
// 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。 
// Related Topics 堆 分治算法 
// 👍 1060 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        initHeap(nums, heapSize);
        for (int i = 0; i < k - 1; ++i) {
            poll(nums, heapSize--);
        }
        return poll(nums, heapSize--);
    }

    /**
     * 移除堆顶
     *
     * @param queue
     * @param heapSize
     * @return
     */
    private int poll(int[] queue, int heapSize) {
        int temp = queue[0];
        queue[0] = queue[--heapSize];
        siftDown(0, queue, heapSize);
        return temp;
    }

    /**
     * 堆化
     *
     * @param queue
     */
    private void initHeap(int[] queue, int heapSize) {
        for (int i = (heapSize << 1) - 1; i >= 0; --i) {
            siftDown(i, queue, heapSize);
        }
    }

    /**
     * 上浮
     *
     * @param k     加入的下标
     * @param queue
     */
    private void siftUp(int k, int[] queue) {
        while (k > 0) {
            int parent = (k - 1) >> 1;
            if (queue[k] > queue[parent]) {
                swap(k, queue, parent);
            } else {
                break;
            }
            k = parent;
        }
    }

    /**
     * 下沉
     *
     * @param k     移除的下标/非叶子节点
     * @param queue 数组
     */
    private void siftDown(int k, int[] queue, int heapSize) {
        int half = heapSize >> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            int right = child + 1;
            if (right < heapSize) {
                child = queue[child] > queue[right] ? child : right;
            }
            if (queue[k] < queue[child]) {
                swap(k, queue, child);
            } else {
                break;
            }
            k = child;
        }
    }

    private void swap(int k, int[] queue, int child) {
        queue[k] ^= queue[child];
        queue[child] ^= queue[k];
        queue[k] ^= queue[child];
    }
}
//leetcode submit region end(Prohibit modification and deletion)