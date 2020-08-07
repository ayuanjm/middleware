//给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。 
//
// 示例 1: 
//
// 输入: 1->2->3->4->5->NULL, k = 2
//输出: 4->5->1->2->3->NULL
//解释:
//向右旋转 1 步: 5->1->2->3->4->NULL
//向右旋转 2 步: 4->5->1->2->3->NULL
// 
//
// 示例 2: 
//
// 输入: 0->1->2->NULL, k = 4
//输出: 2->0->1->NULL
//解释:
//向右旋转 1 步: 2->0->1->NULL
//向右旋转 2 步: 1->2->0->NULL
//向右旋转 3 步: 0->1->2->NULL
//向右旋转 4 步: 2->0->1->NULL 
// Related Topics 链表 双指针 
// 👍 310 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        int n = 1;
        ListNode curr = head;
        while (curr.next != null) {
            curr = curr.next;
            n++;
        }
        ListNode oldTail = curr;
        ListNode newTail = head;
        if (k % n == 0) {
            return head;
        }
        for (int i = n - (k % n) - 1; i > 0; i--) {
            newTail = newTail.next;
        }
        ListNode newHead = newTail.next;
        newTail.next = null;
        oldTail.next = head;
        return newHead;
    }

    private ListNode getListNode(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int i = 0;
        ListNode curr = head;
        while (curr != null) {
            curr = curr.next;
            i++;
        }
        k = k % i;

        if (k == 0 || i <= 1) {
            return head;
        }

        ListNode dummy = head;
        int j = i - k;
        ListNode tempJ = null;
        ListNode temp = null;
        ListNode tempI = null;
        while (dummy != null) {
            j--;
            if (j == 0) {
                tempJ = dummy;
                temp = dummy.next;
            }
            i--;
            if (i == 0) {
                tempI = dummy;
            }

            dummy = dummy.next;
        }
        tempJ.next = null;
        tempI.next = head;
        return temp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
