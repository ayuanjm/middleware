//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。 
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。 
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 示例： 
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
// 
// Related Topics 链表 数学 
// 👍 4732 👎 0


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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(-1);
        ListNode curr = l3;
        ListNode newL1 = l1;
        ListNode newL2 = l2;
        int sum = 0;
        while (newL1 != null || newL2 != null) {
            sum += (newL1 != null ? newL1.val : 0) + (newL2 != null ? newL2.val : 0);
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            sum = sum / 10;
            newL1 = newL1 != null ? newL1.next : null;
            newL2 = newL2 != null ? newL2.next : null;
        }
        if (sum == 1) {
            curr.next = new ListNode(1);
        }
        return l3.next;
    }

    private ListNode reverse(ListNode newL1, ListNode curr) {
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = newL1;
            newL1 = curr;
            curr = next;
        }
        return newL1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
