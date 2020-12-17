//输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，
//它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。 
//
// 
//
// 示例： 
//
// 给定一个链表: 1->2->3->4->5, 和 k = 2.
//
//返回链表 4->5. 
// Related Topics 链表 双指针 
// 👍 114 👎 0


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
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode former = head, latter = head;
        //不需要考虑k越界
        //当k为2时，former到达最后1个节点时，latter为第倒数2个节点，former比latter多走了k-1=1步
        //当k为4时，former到达最后1个节点时，latter为第倒数4个节点，former比latter多走了k-1=3步
        for (int i = 1; i <= k - 1; i++) {
            //让former比latter先走了k-1步
            former = former.next;
        }
        while (former.next != null) {
            //former和latter一起向后相同步子走，当former到达最后一个节点时，latter到达倒数k个节点
            former = former.next;
            latter = latter.next;
        }
        return latter;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
