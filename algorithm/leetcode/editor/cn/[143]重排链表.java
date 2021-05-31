//给定一个单链表 L：L0→L1→…→Ln-1→Ln ， 
//将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→… 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 示例 1: 
//
// 给定链表 1->2->3->4, 重新排列为 1->4->2->3. 
//
// 示例 2: 
//
// 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3. 
// Related Topics 链表 
// 👍 588 👎 0


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
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode middleNode = getMiddleNode(head);
        ListNode l1 = head;
        ListNode l2 = middleNode.next;
        middleNode.next = null;
        l2 = reverseList(l2);
        mergeList(l1, l2);
    }

    private void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_temp;
        ListNode l2_temp;
        while (l2 != null) {
            l1_temp = l1.next;
            l2_temp = l2.next;
            l1.next = l2;
            l1 = l1_temp;
            l2.next = l1_temp;
            l2 = l2_temp;
        }
    }

    private ListNode reverseList(ListNode l2) {
        ListNode pre = null;
        ListNode cur = l2;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private ListNode getMiddleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
