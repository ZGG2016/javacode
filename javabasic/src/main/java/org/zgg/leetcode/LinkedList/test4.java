package org.zgg.leetcode.LinkedList;
/*
 * 21. Merge Two Sorted Lists
 *
 * */
public class test4 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        //定义一个结点，指向小的那个节点，一直后移。
//        ListNode tmp = new ListNode(0);
//        ListNode lastnode = tmp;
//        while(l1!=null && l2!=null){
//            if(l1.val<l2.val){
//                lastnode.next=l1;
//                l1=l1.next; //移动到下一个结点
//            }else{
//                lastnode.next=l2;
//                l2=l2.next;
//            }
//            lastnode=lastnode.next;
//        }
//
//        if(l1!=null){   //在while那只要有一个节点是null,就退出循环，所以在这里判断另一个是不是也为null
//            lastnode.next=l1;
//        }else{
//            lastnode.next=l2;
//        }
//        return tmp.next;

// =======================================================================================================

        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.val < l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
