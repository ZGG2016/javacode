package org.zgg.leetcode.Tree;

import java.util.Stack;

/*
*
* 404. Sum of Left Leaves
* */
public class test12 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
 //递归
    private int sumOfLeftLeaves1(TreeNode root){
        if(root==null) return 0;
        int rlt = 0;

        if(root.left!=null){
            if(root.left.left==null && root.right.right==null){
                rlt+=root.left.val;  //判断是不是叶结点
            }else{
                rlt+=sumOfLeftLeaves1(root.left);
            }
        }
        rlt+=sumOfLeftLeaves1(root.right);
       return rlt;


    }
//dfs
    public int sumOfLeftLeaves2(TreeNode root){
        if(root==null) return 0;
        int rlt = 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();

            if(tmp.left!=null){
                if(tmp.left.left==null && tmp.right.right==null){
                    rlt+=tmp.left.val;  //判断是不是叶结点
                }else{
                    stack.push(tmp.left);
                }
            }
            if(tmp.right!=null){   //对一个结点的右结点，可以理解成 递归计算
                if(tmp.right.left!=null || tmp.right.right!=null){
                    stack.push(tmp.right);
                }
            }
        }
        return rlt;
    }
}
