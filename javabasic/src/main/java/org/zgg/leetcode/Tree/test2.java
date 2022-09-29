package org.zgg.leetcode.Tree;
/*
* 110. Balanced Binary Tree
*
* */
public class test2 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        return getDepth(root)!=-1;
    }

    public int getDepth(TreeNode root){
        if(root==null) return 0;
        int left = getDepth(root.left);
        if(left==-1) return -1;   //一个结点只有一个子树
        int right = getDepth(root.right);
        if(right==-1) return -1;
        return Math.abs(left-right)>1?-1:Math.max(left,right)+1;
    }
}
