package org.zgg.leetcode.recursion;
/*
*
* 779. K-th Symbol in Grammar
*
* */
public class test2 {

    public int kthGrammar(int N, int K) {
        if(N==1) return 0;
        if(K%2==0) return (kthGrammar(N-1,K/2)==0)?1:0;
        else return (kthGrammar(N-1,(K+1)/2)==0)?0:1;

    }
}


//将其看作二叉树，如果是0，其左子树为0，右子树为1。如果是1，其左子树为1，右子树为0。
//根据索引的奇偶，判断左右子树。