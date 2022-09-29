package org.zgg.leetcode.LinkedList;

import java.util.HashSet;

/*
*
* 817. Linked List Components
*
* 在连通图中，[1,2,3]中，1,2是一个连通分量，3与自身是一个连通分量
*
* */
public class test10 {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public int numComponents(ListNode head, int[] G) {
        HashSet gset = new HashSet();
        for(int g:G){gset.add(g);}

        int res = 0;
        while (head != null) {
            if (gset.contains(head.val) && (head.next == null || !gset.contains(head.next.val))) res++;
            head = head.next;
        }
        return res;
    }
}
