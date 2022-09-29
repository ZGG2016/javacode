package org.zgg.leetcode.Tree;
/*
* 572. Subtree of Another Tree
* */
public class test18 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {

        if(s==null) return false;
        if(helper(s,t)) return true;   //如果两树一致，返回，否则，递归左右子树判断

        return isSubtree(s.left,t) || isSubtree(s.right,t);
    }

    public boolean helper(TreeNode s, TreeNode t){  //判断两树是否一致
        if(s==null && t==null) return true;
        if(s==null || t==null) return false;

        if(s.val!=t.val) return false;

        return helper(s.left,t.left) && helper(s.right,t.right);
    }


}
