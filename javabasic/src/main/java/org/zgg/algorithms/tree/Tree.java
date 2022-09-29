package org.zgg.algorithms.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree {
     static class Node {
        Node left;
        Node right;
        int val;

        Node(int x) {
            this.val = x;
        }
    }

    // 递归前序遍历
    private void preTraversal(Node root){
        if(root == null) return;

        System.out.println(root.val);
        preTraversal(root.left);
        preTraversal(root.right);
    }
    //非递归前序遍历
    private void preTraversalNotRecursion(Node root) {
        /*
         *
         * 对于根结点，访问这个节点并压入栈中，然后访问该节点的左孩子，
         * 如果到某节点A的左孩子为null，那么就表示遍历完了左孩子。
         * 就把该节点A弹出，取它的右孩子，按规则一直遍历。
         *
         * */
        if(root==null) return;

        Node p = root;
        Stack<Node> stack = new Stack<Node>();

        while (stack.size() > 0 || p != null) {
            if(p != null){
                stack.push(p);
                System.out.println(p.val);
                p=p.left;
            }else{
                p=stack.pop();
                p=p.right;
            }
        }
    }

    //递归后序遍历
    private void backTraversal(Node root){
        if(root==null) return;

        backTraversal(root.left);
        backTraversal(root.right);
        System.out.println(root.val);
    }
    //非递归后序遍历
    private void backTraversalNotRecursion(Node root){
        /*
         *
         * 构造一个中间栈来存储后序遍历的结果
         * */

        if(root==null) return;

        Node p = root;
        Stack<Node> stack = new Stack<Node>();
        Stack<Node> output = new Stack<Node>();
        while (stack.size() > 0 || p != null) {
            if(p != null){
                stack.push(p);
                output.push(p);
                p=p.right;   // 先压进来右孩子，才能先弹出左孩子
            }else{
                p=stack.pop();
                p=p.left;
            }
        }
        while (output.size() > 0) {
            System.out.println(output.pop().val);
        }
    }
    //递归中序遍历
    private void midTraversal(Node root){
        if(root==null) return;

        midTraversal(root.left);
        System.out.println(root.val);
        midTraversal(root.right);
    }
    //非递归中序遍历
    private void midTraversalNotRecursion(Node root){
        /*
         *
         * 先将T入栈，遍历左子树；遍历完左子树返回时，栈顶元素应为T，
         *       出栈，访问T->data，再中序遍历T的右子树。
         *
         * */
        Node p = root;
        Stack<Node> stack = new Stack<Node>();

        while (stack.size() > 0 || p != null) {
            if(p != null){
                stack.push(p);
                p=p.left;
            }else{
                p=stack.pop();
                System.out.println(p.val);
                p=p.right;
            }
        }
    }

    //求二叉树的最大深度
    private int getMaxDepth(Node root){
        if(root==null) return 0;

        int left = getMaxDepth(root.left);
        int right = getMaxDepth(root.right);
        return Math.max(left,right)+1;
    }
    private int getMaxDepthNotRecursion(Node root){
        if(root == null) return 0;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        int depth = 0;

        // 定义两个变量，用来标记一层有没有处理完。cur < width 表示没处理完。
        // cur记录访问到当前层的第几个,widtd为当前层的宽度
        int cur = 0,width = 0;
        Node p = root;
        while(!queue.isEmpty()){
            cur = 0;
            width = queue.size();
            depth++;
            while(cur<width){  //一次处理一层的数据
                p = queue.poll();
                if(p.left != null){
                    queue.offer(p.left);
                }
                if(p.right != null){
                    queue.offer(p.right);
                }
                cur++;
            }
        }
        return depth;
    }


    //求二叉树的最小深度
    private int getMinDepth(Node root) {
        if(root == null) return 0;

        int left = getMinDepth(root.left);
        int right = getMinDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1: Math.min(left,right) + 1;
    }
    //求二叉树的宽度
    private int getMaxWidth(Node root){
        /*
        * 使用队列，层次遍历二叉树。在上一层遍历完成后，下一层的所有节点已经放到队列中，
        * 此时队列中的元素个数就是下一层的宽度。以此类推，依次遍历下一层即可求出二叉树的最大宽度。
        * */
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        int maxwidth = 1;
        while(!q.isEmpty()){
            int len = q.size();
            if (len == 0) break;
            while(len>0){ // 如果当前层，还有节点
                Node tmp = q.poll();
                len--;
                if(tmp.left!=null){
                    q.offer(tmp.left);
                }
                if(tmp.right!=null){
                    q.offer(tmp.right);
                }
            }
            maxwidth = Math.max(maxwidth,q.size());
        }
        return maxwidth;
    }
    //广度优先遍历
    private void bfs(Node root){
        if(root==null) return;

        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()){
            Node tmp = q.poll();
            System.out.println(tmp.val);
            if(tmp.left!=null){
                q.offer(tmp.left);
            }
            if(tmp.right!=null){
                q.offer(tmp.right);
            }
        }
    }

    //深度优先遍历
    private void dfs(Node root){
        if(root==null) return;

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            Node tmp = stack.pop();
            if(tmp.right!=null){
                stack.push(tmp.right);
            }
            if(tmp.left!=null){
                stack.push(tmp.left);
            }
            System.out.println(tmp.val);
        }
    }

    public static void main(String[] args){
        Tree aa = new Tree();
        Node treeNode1 = new Node(1);
        Node treeNode2 = new Node(2);
        Node treeNode3 = new Node(3);
        Node treeNode4 = new Node(4);
        Node treeNode5 = new Node(5);
        Node treeNode6 = new Node(6);
        Node treeNode7 = new Node(7);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;

        //aa.preTraversal(treeNode1);
        //aa.preTraversalNotRecursion(treeNode1);
        //aa.backTraversal(treeNode1);
        //aa.backTraversalNotRecursion(treeNode1);
        //aa.midTraversal(treeNode1);
        //aa.midTraversalNotRecursion(treeNode1);

        int width = aa.getMaxWidth(treeNode1);
        System.out.println(width);

    }
}
