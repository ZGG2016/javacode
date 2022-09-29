package org.zgg.leetcode.recursion;
/*
* 687. Longest Univalue Path
*
* */
public class test1 {

     public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public int longestUnivaluePath(TreeNode root) {
        int[] res = new int[1];
        if (root != null) dfs(root, res);
        return res[0];
    }

    private int dfs(TreeNode node, int[] res) {  //深度搜索
        int l = node.left != null ? dfs(node.left, res) : 0;
        int r = node.right != null ? dfs(node.right, res) : 0;
        int resl = node.left != null && node.left.val == node.val ? l + 1 : 0;
        int resr = node.right != null && node.right.val == node.val ? r + 1 : 0;
        res[0] = Math.max(res[0], resl + resr);
        return Math.max(resl, resr);
    }
}
//
//    int ans;
//    public int longestUnivaluePath(TreeNode root) {
//        if(root==null) return 0;
//        ans=0;
//        dfs(root);
//        return ans;
//
//    }
//    public int dfs(TreeNode node){
//        if(node==null) return 0;
//        int left = dfs(node.left);
//        int right = dfs(node.right);
//        int lefco=0,rigco=0;
//        if(node.left!=null && node.val==node.left.val){
//            lefco+=left+1;
//        }
//        if(node.right!=null && node.val==node.right.val){
//            rigco+=right+1;
//        }
//
//        ans = Math.max(ans,lefco+rigco);
//        return Math.max(lefco,rigco);
//    }