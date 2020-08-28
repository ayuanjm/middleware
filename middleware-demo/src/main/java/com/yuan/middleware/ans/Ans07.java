package com.yuan.middleware.ans;

/**
 * @author yuanjm
 * @date 2020/8/15 4:16 下午
 */
public class Ans07 {
    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        a.next = b;

        ListNode curr = a;
        ListNode dummy = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = dummy;
            dummy = curr;
            curr = next;
        }
        System.out.println(dummy.val + "->" + dummy.next.val);
    }
}
