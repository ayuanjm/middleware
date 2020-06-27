//编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。 
//
// 示例1: 
//
// 
// 输入：[1, 2, 3, 3, 2, 1]
// 输出：[1, 2, 3]
// 
//
// 示例2: 
//
// 
// 输入：[1, 1, 1, 1, 2]
// 输出：[1, 2]
// 
//
// 提示： 
//
// 
// 链表长度在[0, 20000]范围内。 
// 链表元素在[0, 20000]范围内。 
// 
//
// 进阶： 
//
// 如果不得使用临时缓冲区，该怎么解决？ 
// Related Topics 链表


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
    public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null) {
            return head;
        }
        Set<Integer> set = new HashSet();
        set.add(head.val);
        ListNode dummy = head;
        while (dummy.next != null) {
            ListNode cur = dummy.next;
            if (set.add(cur.val)) {
                dummy = dummy.next;
            } else {
                dummy.next = dummy.next.next;
            }
        }
        return head;
    }

    private ListNode getListNodeByMap(ListNode head) {
        LinkedHashMap<Integer, ListNode> map = new LinkedHashMap<>();
        while (head != null) {
            map.put(head.val, head);
            head = head.next;
        }
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (ListNode listNode : map.values()) {
            cur.next = listNode;
            listNode.next = null;
            cur = cur.next;
        }
        return dummy.next;
    }

    //时间换空间，不使用临时缓冲区
    private ListNode getListNode(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = null;
        //当前节点
        ListNode cur = head;
        //新的链表尾部节点，cur tail 双指针
        ListNode tail = dummy;
        ListNode temp = dummy.next;
        int count = 0;
        while (cur != null) {
            while (temp != null) {
                if (cur.val == temp.val) {
                    count++;
                    break;
                } else {
                    temp = temp.next;
                }
            }
            if (count == 0) {
                tail.next = new ListNode(cur.val);
                tail = tail.next;
            }
            cur = cur.next;
            count = 0;
            temp = dummy.next;
        }
        return dummy.next;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
