package org.zgg.leetcode.Tree;
/*
*
* 606. Construct String from Binary Tree
* */
public class test20 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public String tree2str(TreeNode t){
        if(t==null) return "";

        return helper(t);
    }
    public String helper(TreeNode t){
        if(t==null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(t.val));

        if(t.right!=null){
            sb.append("("+helper(t.left)+")"+"("+helper(t.right)+")");
        }else if(t.left!=null && t.right==null){
            sb.append("("+helper(t.left)+")");
        }
        return sb.toString();
    }

    public String tree2str2(TreeNode t) {
        if (t == null) return "";

        String result = t.val + "";

        String left = tree2str(t.left);
        String right = tree2str(t.right);

        if (left == "" && right == "") return result;
        if (left == "") return result + "()" + "(" + right + ")";
        if (right == "") return result + "(" + left + ")";
        return result + "(" + left + ")" + "(" + right + ")";
    }
}
