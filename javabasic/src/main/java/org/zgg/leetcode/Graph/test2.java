package org.zgg.leetcode.Graph;
/*
* 684. Redundant Connection
* （并查集）
* */
public class test2 {

    public int[] findRedundantConnection(int[][] edges) {

        int n = edges.length;
        DisjointSet ds = new DisjointSet(n);

        for (int[] edge : edges) {
            int u = edge[0] - 1;  //会超出索引
            int v = edge[1] - 1;
            if (ds.find(u) == ds.find(v)) {
                return edge;
            } else {
                ds.union(u, v);
            }
        }
        return null;

    }

    class DisjointSet {

        int[] parent;

        public DisjointSet(int N) {
            parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            parent[rootA] = rootB;
        }

        public int find(int x) {
            while (parent[x] != x) {
                x = parent[x];
            }
            return x;
        }

    }
}
