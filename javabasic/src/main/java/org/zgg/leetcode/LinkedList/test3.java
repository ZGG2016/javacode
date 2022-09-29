package org.zgg.leetcode.LinkedList;
/*
*
* 206. Reverse Linked List
*
* Input: 1->2->3->4->5->NULL
* Output: 5->4->3->2->1->NULL
*
*
* */
public class test3 {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode reverseList(ListNode head) {
//        ListNode prev = null;
//        ListNode cur = head;
//        while(cur!=null){
//            ListNode next = cur.next;   //当是最后一个结点时，这里的next为null,
//            cur.next = prev;   //把prev作为cur的下一个节点，建立指向
//            prev = cur;
//            cur = next;  //往后移动  //这里的cur为null，进入下一次循环时，判断后退出循环
//        }
//        return prev;

// =====================================================================================

        if(head == null ||head.next == null){   //？？？？？
            return head;
        }

        ListNode root = reverseList(head.next);

        head.next.next = head;
        head.next = null;
        return root;
    }

}
