//输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。 
//
// 
//
// 示例 1： 
//
// 输入：arr = [3,2,1], k = 2
//输出：[1,2] 或者 [2,1]
// 
//
// 示例 2： 
//
// 输入：arr = [0,1,2,1], k = 1
//输出：[0] 
//
// 
//
// 限制： 
//
// 
// 0 <= k <= arr.length <= 10000 
// 0 <= arr[i] <= 10000 
// 
// Related Topics 堆 分治算法 
// 👍 116 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
//        sort(arr, 0, arr.length - 1);
        if (k == 0) {
            return new int[0];
        }
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < arr.length; i++) {
            if (queue.size() < k) {
                queue.offer(arr[i]);
            } else {
                if (arr[i] < queue.peek()) {
                    queue.poll();
                    queue.offer(arr[i]);
                }
            }
        }
        int[] temp = new int[k];
        int i = 0;
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            temp[i++] = iterator.next();
        }
//        for (int i = 0; i < k; i++) {
//            temp[i] = arr[i];
//        }
        return temp;
    }

    public void sort(int[] arr, int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return;
        }
        int partition = partition(arr, leftBound, rightBound);
        sort(arr, leftBound, partition - 1);
        sort(arr, partition + 1, rightBound);
    }

    public int partition(int[] arr, int leftBound, int rightBound) {
        int left = leftBound;
        int right = rightBound - 1;
        int mark = arr[rightBound];
        while (left < right) {
            while (left <= right && arr[left] <= mark) {
                left++;
            }
            while (left < right && arr[right] > mark) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
            }
        }
        if (arr[left] >= mark) {
            swap(arr, left, rightBound);
        }
        return left;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
