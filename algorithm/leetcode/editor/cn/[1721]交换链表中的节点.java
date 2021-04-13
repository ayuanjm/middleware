//给你链表的头节点 head 和一个整数 k 。 
//
// 交换 链表正数第 k 个节点和倒数第 k 个节点的值后，返回链表的头节点（链表 从 1 开始索引）。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], k = 2
//输出：[1,4,3,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [7,9,6,6,7,8,3,0,9,5], k = 5
//输出：[7,9,6,6,8,7,3,0,9,5]
// 
//
// 示例 3： 
//
// 
//输入：head = [1], k = 1
//输出：[1]
// 
//
// 示例 4： 
//
// 
//输入：head = [1,2], k = 1
//输出：[2,1]
// 
//
// 示例 5： 
//
// 
//输入：head = [1,2,3], k = 2
//输出：[1,2,3]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目是 n 
// 1 <= k <= n <= 105 
// 0 <= Node.val <= 100 
// 
// Related Topics 链表 
// 👍 18 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy, pre1 = dummy, pre2 = dummy, curr1 = dummy, curr2 = dummy;
        for (int i = 0; i < k - 1; ++i) {
            fast = fast.next;
            pre1 = pre1.next;
        }
        fast = fast.next;
        curr1 = fast;
        fast = fast.next;
        curr2 = curr2.next;
        while (fast != null) {
            fast = fast.next;
            pre2 = pre2.next;
            curr2 = curr2.next;
        }
        ListNode last1 = curr1.next;
        ListNode last2 = curr2.next;
        if (curr1.next == curr2) {
            pre1.next = curr2;
            curr2.next = curr1;
            if (last2 == curr1) {
                curr1.next = null;
            } else {
                curr1.next = last2;
            }
            return dummy.next;
        }
        if (curr2.next == curr1) {
            pre2.next = curr1;
            curr1.next = curr2;
            if (last1 == curr2) {
                curr2.next = null;
            } else {
                curr2.next = last1;
            }
            return dummy.next;
        }
        pre1.next = curr2;
        curr2.next = last1;
        pre2.next = curr1;
        curr1.next = last2;
        return dummy.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
