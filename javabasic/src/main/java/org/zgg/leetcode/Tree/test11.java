package org.zgg.leetcode.Tree;
/*
* 109. Convert Sorted List to Binary Search Tree
*
* */
public class test11 {

    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }

      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }

    public TreeNode sortedListToBST(ListNode head) {
        //找mid
        if (head == null) return null;

        if(null == head.next){   //只有一个结点时
            return new TreeNode(head.val);
        }


        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        while(fast!=null && fast.next!=null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode node = new TreeNode(slow.val);
        prev.next = null;
        node.left = sortedListToBST(head);
        node.right = sortedListToBST(slow.next);

        return node;
    }
}
