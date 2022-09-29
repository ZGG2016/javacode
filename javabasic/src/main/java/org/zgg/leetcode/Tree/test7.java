package org.zgg.leetcode.Tree;

import java.util.*;

/*
*
* 257. Binary Tree Paths
* */
public class test7 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<String> binaryTreePaths(TreeNode root){
        List<String> list = new ArrayList<String>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Stack<String> str = new Stack<String>();

        if(root==null) return list;
        stack.push(root);
        str.push("");
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            String s = str.pop();

            if(tmp.left==null && tmp.right==null) list.add(s+tmp.val);
            if(tmp.right!=null){
                stack.push(tmp.right);
                str.push(s+tmp.val);
            }
            if(tmp.left!=null){
                stack.push(tmp.left);
                str.push(s+tmp.val);
            }
        }
        return list;
    }

    public List<String> binaryTreePaths2(TreeNode root) {
        List<String> list=new ArrayList<String>();
        Queue<TreeNode> qNode=new LinkedList<TreeNode>();
        Queue<String> qStr=new LinkedList<String>();

        if (root==null) return list;
        qNode.add(root);
        qStr.add("");
        while(!qNode.isEmpty()) {
            TreeNode curNode=qNode.remove();
            String curStr=qStr.remove();

            if (curNode.left==null && curNode.right==null) list.add(curStr+curNode.val);
            if (curNode.left!=null) {
                qNode.add(curNode.left);
                qStr.add(curStr+curNode.val+"->");
            }
            if (curNode.right!=null) {
                qNode.add(curNode.right);
                qStr.add(curStr+curNode.val+"->");
            }
        }
        return list;
}

    public List<String> binaryTreePaths3(TreeNode root) {

        List<String> paths = new LinkedList<>();

        if (root == null) return paths;

        if (root.left == null && root.right == null) {
            paths.add(root.val + "");
            return paths;
        }

        for (String path : binaryTreePaths(root.left)) {
            paths.add(root.val + "->" + path);
        }

        for (String path : binaryTreePaths(root.right)) {
            paths.add(root.val + "->" + path);
        }

        return paths;
    }
}
