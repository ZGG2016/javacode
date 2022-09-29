package org.zgg.leetcode.LinkedList;
/*
*  141.Linked List Cycle
*
* */
public class test5 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public boolean hasCycle(ListNode head) {

        //如果有环，slow\fast会相遇
//        if(head==null || head.next==null){
//            return false;
//        }
//
//        ListNode slow = head;
//        ListNode fast = head.next;
//        while (fast!=slow){
//            if(fast==null || fast.next==null){
//                return false;
//            }
//            fast = fast.next.next;  //如果有环，那么fast和slow是同一个结点。。关键步
//            slow = slow.next;
//        }
//        return true;

// ===============================================================================

        if(head==null){return false;}
        ListNode a = head;
        ListNode b = head;
        while(a.next!=null && a.next.next!=null){
            a = a.next.next;
            b = b.next;
            if(a==b){return true;}
        }
        return false;
    }

}
