package org.zgg.leetcode.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
* 637. Average of Levels in Binary Tree
*
* */
public class test16 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<Double> averageOfLevels(TreeNode root) {

        List<Double> list = new ArrayList<Double>();

        if(root==null) return list;
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while(!q.isEmpty()){

            int len = q.size();
            double sum = 0.0;
            for(int i=0;i<len;i++){
                TreeNode tmp = q.poll();
                sum+=tmp.val;
                if(tmp.left!=null){
                    q.offer(tmp.left);
                }

                if(tmp.right!=null){
                    q.offer(tmp.right);
                }
            }

            list.add(sum/len);
        }
        return list;
    }

}
