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
        //创建一个虚拟节点，该节点的next指向头节点也就是第一个节点，类似aqs的头节点
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        //创建一个哨兵节点cur用来遍历,cur.next总是指向第一个元素
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            //如果连续元素相等
            if (cur.next.val == cur.next.next.val) {
                //11123：最终跳出循环时cur.next必定是连续相等元素的最后一个值(1)
                while (cur.next != null && cur.next.next != null && cur.next.next.val == cur.next.val) {
                    cur.next = cur.next.next;
                }
                //cur.next = cur.next.next 连续相等元素的最后一个值的next，也就是去除相等后的第一个值
                cur.next = cur.next.next;
            } else {
                //哨兵cur引用指向最新不等元素(2)
                cur = cur.next;
                //对于引用指向问题总感觉不太好理解，比如tail指向最后一个元素，新插入一个元素，tail.next = new; tail = new;
                //tail相当于一个哨兵，总是指向最后一个元素，tail只是一个引用，指向堆内存的实例数据，tail.next改变会改变链表的最后一个元素的指向
                //tail = new 不会改变链表的元素，只是tail指向了新的堆内存，但是不影响原来的tail指向的堆内存。
            }
        }
        return dummy.next;
    }

    private ListNode getListNode(ListNode head) {
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
