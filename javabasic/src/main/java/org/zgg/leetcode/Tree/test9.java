package org.zgg.leetcode.Tree;
/*
* 236. Lowest Common Ancestor of a Binary Tree
*
* */
public class test9 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || root==p || root==q) return root;  //找终止情况

        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        return (left!=null && right!=null)?root:(left==null?right:left);
    }
}
