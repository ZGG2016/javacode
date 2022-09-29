package org.zgg.leetcode.Tree;
/*
* 538. Convert BST to Greater Tree
*
* */
public class test13 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    private int sum = 0;
    public TreeNode convertBST(TreeNode root){
        if(root==null) return null;
        inorder(root);
        return root;
    }

    private void inorder(TreeNode node){   //利用BST的root.left.val<root.val<root.right.val性质
        if(node==null) return;
        inorder(node.right);  //右边始终是比中和左大，不用考虑会小的问题。依次从右到中到左更新
        node.val+=sum;
        sum = node.val;
        inorder(node.left);
    }
}
