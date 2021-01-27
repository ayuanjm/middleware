//请判断一个链表是否为回文链表。 
//
// 示例 1: 
//
// 输入: 1->2
//输出: false 
//
// 示例 2: 
//
// 输入: 1->2->2->1
//输出: true
// 
//
// 进阶： 
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 链表 双指针 
// 👍 834 👎 0


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
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //反转后半链表
        ListNode newHead = reverseList(slow.next);
        if (newHead == null) {
            return true;
        }
        ListNode dummyHead = head;
        ListNode dummyCurr = newHead;

        boolean result = true;
        while (dummyHead != null && newHead != null) {
            if (dummyHead.val == newHead.val) {
                dummyHead = dummyHead.next;
                newHead = newHead.next;
            } else {
                result = false;
                break;
            }
        }
        //还原后半链表
//        reverseList(dummyCurr);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode newHead = null;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = newHead;
            newHead = curr;
            curr = temp;
        }
        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
