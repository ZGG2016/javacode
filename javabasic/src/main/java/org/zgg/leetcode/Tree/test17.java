package org.zgg.leetcode.Tree;

import java.util.HashSet;

/*
* 653. Two Sum IV - Input is a BST
*
* */
public class test17 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean findTarget(TreeNode root, int k) {
        HashSet<Integer> set = new HashSet<>();
        return dfs(root, set, k);
    }

    public boolean dfs(TreeNode root, HashSet<Integer> set, int k){
        if(root == null)return false;
        if(set.contains(k - root.val))return true;
        set.add(root.val);
        return dfs(root.left, set, k) || dfs(root.right, set, k);
    }

//    public boolean findTarget(TreeNode root, int k) {
//        List<Integer> nums = new ArrayList<>();
//        inorder(root, nums);
//        for(int i = 0, j = nums.size()-1; i<j;){
//            if(nums.get(i) + nums.get(j) == k)return true;
//            if(nums.get(i) + nums.get(j) < k)i++;
//            else j--;
//        }
//        return false;
//    }
//
//    public void inorder(TreeNode root, List<Integer> nums){
//        if(root == null)return;
//        inorder(root.left, nums);
//        nums.add(root.val);
//        inorder(root.right, nums);
//    }

}
