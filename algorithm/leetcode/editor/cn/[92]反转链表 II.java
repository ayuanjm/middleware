//反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
//
// 说明:
//1 ≤ m ≤ n ≤ 链表长度。
//
// 示例:
//
// 输入: 1->2->3->4->5->NULL, m = 2, n = 4
//输出: 1->4->3->2->5->NULL
// Related Topics 链表
// 👍 456 👎 0


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
    public ListNode reverseBetween(ListNode head, int m, int n) {
        int index = 1;
        ListNode curr = head;
        ListNode prev = null;
        //m-1节点
        ListNode dummy = null;
        //m节点
        ListNode tempM = null;
        while (curr != null && index <= n) {
            if (index < m) {
                if (index == m - 1) {
                    //记录m-1的节点，在节点m节点反转后让dummy.next指向反转后的节点
                    dummy = curr;
                }
                curr = curr.next;
            }

            if (index >= m) {
                if (index == m) {
                    //记录m节点，在节点n节点反转后让tempM.next指向n+1节点
                    tempM = curr;
                }
                //m-n链表反转
                ListNode nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }

            index++;
        }
        if (dummy != null) {
            dummy.next = prev;
        } else {
            //如果dummy为null，说明是m=1从第一个节点开始反转的，这样反转后的prev节点就是新的head节点，只需要修改原来m节点的指向就可以
            head = prev;
        }
        if (tempM != null) {
            tempM.next = curr;
        }
        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
