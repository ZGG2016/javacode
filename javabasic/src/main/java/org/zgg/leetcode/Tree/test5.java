package org.zgg.leetcode.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
* 107. Binary Tree Level Order Traversal II
* */
public class test5 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> outer = new ArrayList<List<Integer>>();
        if(root==null) return outer;
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);

        while(!q.isEmpty()){
            int len = q.size();
            if(len==0) break;
            List<Integer> inner = new ArrayList<Integer>();
            while(len>0){
                TreeNode tmp = q.poll();
                inner.add(tmp.val);
                len--;
                if(tmp.left!=null){
                    q.offer(tmp.left);
                }
                if(tmp.right!=null){
                    q.offer(tmp.right);
                }

            }
            outer.add(0,inner);
        }
        return outer;
    }
}
