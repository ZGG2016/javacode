package org.zgg.leetcode.Tree;
/*
* 108. Convert Sorted Array to Binary Search Tree
*
* */
public class test10 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        if(len==0) return null;
        TreeNode tmp = helper(nums,0,len-1);
        return tmp;
    }

    public TreeNode helper(int[] nums,int lo,int hi){
        //分治
        if(lo>hi) return null;

        int mid = lo+(hi-lo)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helper(nums,lo,mid-1);
        node.right = helper(nums,mid+1,hi);

        return node;
    }
}
