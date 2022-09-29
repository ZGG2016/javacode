package org.zgg.leetcode.Tree;

/*
*
* 101. Symmetric Tree
* */
public class test6 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
//关键搞清谁和谁比
//    public boolean isSymmetric(TreeNode root){
//        if(root==null) return true;
//        Queue<TreeNode> q = new Example.LinkedList<TreeNode>();
//        q.offer(root.left);
//        q.offer(root.right);
//        while(!q.isEmpty()){
//            TreeNode left = q.poll();
//            TreeNode right = q.poll();
//            if(left==null && right==null) return true;
//            if(left==null || right==null) return false;
//            if(left.val!=right.val) return false;
//
//            q.offer(left.left);
//            q.offer(right.right);
//            q.offer(left.right);
//            q.offer(right.left);
//        }
//        return true;
//    }

    public boolean isSymmetric(TreeNode root) {
        return root==null || isSymmetricHelp(root.left, root.right);
    }

    private boolean isSymmetricHelp(TreeNode left, TreeNode right){
        if(left==null || right==null)
            return left==right;
        if(left.val!=right.val)
            return false;
        return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
    }


}
