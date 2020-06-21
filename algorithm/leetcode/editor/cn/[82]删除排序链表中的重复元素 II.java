//给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。 
//
// 示例 1: 
//
// 输入: 1->2->3->3->4->4->5
//输出: 1->2->5
// 
//
// 示例 2: 
//
// 输入: 1->1->1->2->3
//输出: 2->3 
// Related Topics 链表


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        HashMap<Integer, Integer> map = new HashMap<>(16);
        ListNode node = head;
        while (node != null) {
            if (map.containsKey(node.val)) {
                map.put(node.val, map.get(node.val + 1));
            } else {
                map.put(node.val, 1);
            }
            node = node.next;
        }
        ArrayList<Integer> arr = new ArrayList<Integer>();
        Set<Integer> keySet = map.keySet();
        for (Integer key : keySet) {
            if (map.get(key) != null && map.get(key) == 1) {
                arr.add(key);
            }
        }

        Collections.sort(arr);
        ListNode tail, newHead;
        tail = new ListNode(-1);
        newHead = tail;
        for (Integer val : arr) {
            ListNode temp = new ListNode(val);
            //尾节点的下一个节点指向新节点
            tail.next = temp;
            //尾节点指向新的尾节点
            tail = temp;
        }
        return newHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
