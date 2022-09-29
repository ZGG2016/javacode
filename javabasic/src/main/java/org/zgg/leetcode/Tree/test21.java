package org.zgg.leetcode.Tree;
/*
* 563. Binary Tree Tilt
* */
public class test21 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private int rlt = 0;

    public int findTilt(TreeNode root){
        int tmp = postOrder(root);
        return rlt;
    }

    private int postOrder(TreeNode root){
        if(root==null) return 0;

        int left = postOrder(root.left);
        int right = postOrder(root.right);

        rlt += Math.abs(left-right);

        return left+right+root.val;
    }
}
