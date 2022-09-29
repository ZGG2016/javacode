package org.zgg.leetcode.Tree;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*
* 671. Second Minimum Node In a Binary Tree
* */
public class test19 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }
        Set<Integer> set = new TreeSet<>();  //先序遍历+treeset有序
        dfs(root, set);
        Iterator<Integer> iterator = set.iterator();
        int count = 0;
                while (iterator.hasNext()) {
            count++;
            int result = iterator.next();
            if (count == 2) {
                return result;
            }
        }
                return -1;
    }

        private void dfs(TreeNode root, Set<Integer> set) {
            if (root == null) {
                return;
            }
            set.add(root.val);
            dfs(root.left, set);
            dfs(root.right, set);
            return;
        }

    public int findSecondMinimumValue2(TreeNode root) {
        if (root == null) {
            return -1;
        }
        if (root.left == null && root.right == null) {
            return -1;
        }
        //根节点可能等于或小于左右子节点的
        int left = root.left.val;
        int right = root.right.val;

        // if value same as root value, need to find the next candidate
        if (root.left.val == root.val) {
            left = findSecondMinimumValue(root.left);
        }
        if (root.right.val == root.val) {
            right = findSecondMinimumValue(root.right);
        }

        if (left != -1 && right != -1) {
            return Math.min(left, right);
        } else if (left != -1) {
            return left;
        } else {
            return right;
        }
    }


//    public int findSecondMinimumValue(TreeNode root) {   //????
//        if(root.left == null) return -1;
//
//        int l = root.left.val == root.val ? findSecondMinimumValue(root.left) : root.left.val;
//        int r = root.right.val == root.val ? findSecondMinimumValue(root.right) : root.right.val;
//
//        return l == -1 || r == -1 ? Math.max(l, r) : Math.min(l, r);
//    }
}
