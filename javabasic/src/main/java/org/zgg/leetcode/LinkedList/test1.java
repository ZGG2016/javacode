package org.zgg.leetcode.LinkedList;

/*
*
* 链表：
*       203. Remove Linked List Elements
*
*       Input:  1->2->6->3->4->5->6, val = 6
*       Output: 1->2->3->4->5
*
* */
public class test1 {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
     }

    public ListNode removeElements(ListNode head, int val) {

//        Hashtable<Integer,Integer> table = new Hashtable<Integer,Integer>();
//        ListNode tmp = head;
//        if(tmp!=null){
//            while(tmp!=null){   //遍历链表，判断给定的值是不是在table里，在就跳过。
//                if(!table.containsKey(val)){
//                    table.put(val,1);
//                }
//                tmp = tmp.next;
//            }
//        }
//        return head;

// ===================================================================================
//        ListNode tmp = new ListNode(0);
//        tmp.next = head;  //把head作为tmp指向的节点..而head=tmp.next仅仅是指向head
//        head = tmp;  //
//
//        while(head.next != null){
//            if(head.next.val ==val){
//                head.next = head.next.next;
//            }else{
//                head = head.next;
//            }
//        }
//        return tmp.next;

// ===================================================================================

//        ListNode tmp = new ListNode(0);
//        tmp.next = head;
//        ListNode cur = head;
//        ListNode pre = tmp;
//        while(cur!=null){
//            if(cur.val==val){
//                pre.next = cur.next;
//            }else{
//                pre = pre.next;
//            }
//            cur = cur.next;
//        }
//        return tmp.next;   //  ?????

// ===================================================================================

//        if(head==null) {return head;}  //递归
//        head.next = removeElements(head.next,val);
//        return head.val==val ? head.next : head;

// ===================================================================================

        if(head==null){return head;}  //my way
        ListNode cur = head;
        ListNode pre = null;
        while(cur!=null){
            if(cur.val==val){
                cur = cur.next;
                if(pre==null){  head = cur;}            //对是不是头节点的处理
                else{pre.next = cur;}
            }else{
                pre = cur;
                cur = cur.next;
            }

        }
        return head;
    }
}
