package org.zgg.leetcode.Tree;

import java.util.Stack;
/*
* 112. Path Sum
* */
public class test3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private boolean hasPathSum(TreeNode root, int sum) {
        if(root==null) return false;

        Stack<TreeNode> stack=new Stack<TreeNode>();
        Stack<Integer> sums=new Stack<Integer>();

        stack.push(root);
        sums.push(sum);

        while(!stack.isEmpty()){
            TreeNode tmp=stack.pop();
            int c = sums.pop();
            //如果sum和父结点的差正好等于当前结点的值，那么return true;
            if(tmp.right==null && tmp.left==null && c==tmp.val){
                return true;
            }

            if(tmp.right!=null){
                stack.push(tmp.right);
                sums.push(c-tmp.val);
            }
            if(tmp.left!=null){
                stack.push(tmp.left);
                sums.push(c-tmp.val);
            }
        }
        return false;
    }

    public boolean hasPathSum2(TreeNode root, int sum) {
        if(root == null) return false;

        if(root.left == null && root.right == null && sum - root.val == 0) return true;

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
}



//private static boolean sumofK(int[] numbers, int n, int k) {
//        if (n==1) {//计算到第一个元素时
//        if (numbers[0]==k) {
//        return true;
//        } else{
//        return false;
//        }
//        }
//        boolean b=sumofK(numbers, n-1, k-numbers[n-1]);
//        if (b) {//这个解中包含a[n-1]
//        return true;
//        } else {//解中不包含a[n-1]，继续计算前面的数组中是否有解
//        return sumofK(numbers, n-1, k);
//        }
//        }