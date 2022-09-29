package org.zgg.leetcode.Tree;
/*
* 617. Merge Two Binary Trees
*
* */
public class test14 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1==null && t2!=null) return t2;
        else if(t1!=null && t2==null) return t1;
        else if(t1==null && t2==null) return null;

        t1.left = mergeTrees(t1.left,t2.left);
        t1.val = t1.val+t2.val;
        t1.right = mergeTrees(t1.right,t2.right);

        return t1;
    }
/*
*
* 为什么要创建新结点
*
* This way the answer will refer to the original trees from the arguments,
* which may be used/modified at other parts of a program.
* Eventually, cause issues that are very hard to detect.
*
* */
    public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;

        TreeNode node = new TreeNode(t1.val + t2.val);
        node.left = mergeTrees(t1.left, t2.left);
        node.right = mergeTrees(t1.right, t2.right);
        return node;
    }
}
