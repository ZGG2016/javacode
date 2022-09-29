package org.zgg.leetcode.Union_Find;
/*
*
* 547. Friend Circles
*
* */
public class test1 {
    public int findCircleNum(int[][] M) {

        int n = M.length;
        UF uf = new UF(n);
        for(int i=0;i<n-1;i++){   //只判断上三角部分
            for(int j=i+1;j<n;j++){
                if(M[i][j]==1) uf.union(i,j);
            }
        }
        return uf.count();
    }
    static class UF{
        int count=0;
        int[] parent,rank;

        public UF(int n){
            count = n;
            parent = new int[n];
            rank = new int[n];
            for(int i=0;i<n;i++){
                parent[i]=i;
            }
        }

        public int find(int p){

            while(parent[p]!=p){
                parent[p]=parent[parent[p]];
                p=parent[p];
            }
            return p;
        }

        public int count(){return count;}

        public void union(int p,int q){
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            }
            else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }
    }
}
