package org.zgg.leetcode.Tree;
/*
* 669. Trim a Binary Search Tree
*
* */
public class test15 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private TreeNode trimBST(TreeNode root, int L, int R) {

        if(root==null) return null;
        //当前结点不在范围内
        if(root.val<L) return trimBST(root.right,L,R);
        if(root.val>R) return trimBST(root.left,L,R);

        root.left = trimBST(root.left,L,R);
        root.right = trimBST(root.right,L,R);
        return root;
    }
}
