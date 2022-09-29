package org.zgg.leetcode.Tree;
/*
*
* 235. Lowest Common Ancestor of a Binary Search Tree
* */
public class test8 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    //因为是BST,root.left始终小于root,root.right始终大于root
        if(root.val>p.val && root.val>q.val){
            return lowestCommonAncestor(root.left,p,q);
        }else if(root.val<p.val && root.val<q.val){
            return lowestCommonAncestor(root.right,p,q);
        }else{
            return root;
        }
    }
    public TreeNode lowestCommonAncesto2r(TreeNode root, TreeNode p, TreeNode q) {
        while ((root.val - p.val) * (root.val - q.val) > 0)
            root = p.val < root.val ? root.left : root.right;
        return root;
    }
}
