package com.yuan.middleware.jdk.algorithm;

/**
 * 单链表反转
 *
 * @author yjm
 * @date 2020/3/24 6:15 下午
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }

    /**
     * 头插法:和1.7HashMap扩容一样
     *
     * @return
     */
    public static ListNode headInsertion(ListNode head) {
        ListNode newHead = null;
        ListNode e = head;
        while (e != null) {
            //暂存下一个节点引用
            ListNode next = e.next;
            //第一次赋值newHead时需要next节点为null，之后每次遍历都是当前节点的next节点为原来的头节点
            e.next = newHead;
            //头节点赋值为当前节点
            newHead = e;
            e = next;
        }
        return newHead;
    }

    /**
     * 递归法是从最后一个Node开始，在弹栈的过程中将指针顺序置换的
     * 其实就是最后两个节点交换next指针，然后依次类推倒数第二个变成最后一个，和前面的交换指针
     *
     * @return
     */
    public static ListNode reversal(ListNode head) {
        //先找到最后一个节点
        if (head == null || head.next == null) {
            return head;
        }
        //不是最后一个节点，进行递归查找
        ListNode next = head.next;
        //再次判断是否是最后一个节点,递归方法结束返回的一定是最后一个节点也就是新的头节点,说明next是相对意义上的最后一个节点，将它前一个节点交换指针
        ListNode newHead = reversal(next);
        //将相对意义的最后一个节点和前节点交换指针
        next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        a.next = b;
        b.next = c;
        print(a);
        System.out.println("------");
//        print(headInsertion(a));
        print(reversal(a));

    }

    private static void print(ListNode a) {
        ListNode temp = a;
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }


}
