package org.zgg.leetcode.LinkedList;

public class test9 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] parts = new ListNode[k];

        int l = 0;
//        ListNode tmp = root;  //要使用单独一个新节点，
//        while(tmp!=null){ l++; tmp = tmp.next; }

        for (ListNode node = root; node != null; node = node.next)
            l++;

        int n = l/k;int r = l%k;  //n表示一个部分包括几个节点，r表示特殊的部分的个数

        ListNode cur = root;
        ListNode prev = null;

        for(int i=0;cur!=null&&i<k;i++,r--){
            parts[i]=cur;
            for(int j=0;j<n+(r>0?1:0);j++){
                prev = cur;
                cur = cur.next;
            }
            prev.next = null;
        }
        return parts;
    }
}
