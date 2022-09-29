package org.zgg.leetcode.LinkedList;
/*
 *
 * 链表：
 *       237. Delete Node in a Linked List
 *
 *       Supposed the linked list is 1 -> 2 -> 3 -> 4 and you are given the third node with value 3,
 *       the linked list should become 1 -> 2 -> 4 after calling your function.
 *
 * */
public class test2 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;  //只需指向下一个节点即可
        node.next = node.next.next;

    }

}
