package org.zgg.leetcode.LinkedList;

/*
*
*  83. Remove Duplicates from Sorted List
*
*  Input: 1->1->2->3->3
*  Output: 1->2->3
*
* */

public class test6 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode deleteDuplicates(ListNode head){
        if(head==null) {return head;}
        ListNode tmp = head;
        while(tmp!=null) {
            if (tmp.val == tmp.next.val) {
                if (tmp.next.next == null) {  //解决最后两个节点相同的情况
                    tmp.next = null;
                } else {
                    tmp.next = tmp.next.next;
                }
            } else {
                tmp = tmp.next;
            }
        }
        return head;
    }
}
