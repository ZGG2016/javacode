package org.zgg.leetcode.Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
* 226. Invert Binary Tree
*
* */
public class test4 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    private TreeNode invertTree1(TreeNode root) {

        if (root == null) return null;

        final TreeNode left = root.left,
                right = root.right;
        root.left = invertTree1(right);
        root.right = invertTree1(left);
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {

        if (root == null) {
            return null;
        }

        final Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()) {
            final TreeNode node = queue.poll();
            final TreeNode left = node.left;
            node.left = node.right;
            node.right = left;

            if(node.left != null) {
                queue.offer(node.left);
            }
            if(node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
    public TreeNode invertTree3(TreeNode root) {

        if (root == null) {
            return null;
        }

        final Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()) {
            final TreeNode node = stack.pop();
            final TreeNode left = node.left;
            node.left = node.right;
            node.right = left;

            if(node.left != null) {
                stack.push(node.left);
            }
            if(node.right != null) {
                stack.push(node.right);
            }
        }
        return root;
    }
}
