package org.zgg.leetcode.Dynamic_Programming;
/*
* 62. Unique Paths
*
* */
public class test7 {

    public int uniquePaths(int m, int n) {
        Integer[][] map = new Integer[m][n];
        for(int i = 0; i<m;i++){
            map[i][0] = 1;
        }
        for(int j= 0;j<n;j++){
            map[0][j]=1;
        }
        for(int i = 1;i<m;i++){
            for(int j = 1;j<n;j++){
                map[i][j] = map[i-1][j]+map[i][j-1];
            }
        }
        return map[m-1][n-1];
    }
//简练写
//    int[][] grid = new int[m][n];
//    for(int i = 0; i<m; i++){
//        for(int j = 0; j<n; j++){
//            if(i==0||j==0)
//                grid[i][j] = 1;
//            else
//                grid[i][j] = grid[i][j-1] + grid[i-1][j];
//        }
//    }
//    return grid[m-1][n-1];

//    public int uniquePaths(int m, int n) {   //????
//        if(m<=0 || n<=0)
//            return 0;
//        int[] res = new int[n];
//        res[0] = 1;
//        for(int i=0;i<m;i++)
//        {
//            for(int j=1;j<n;j++)
//            {
//                res[j] += res[j-1];
//            }
//        }
//        return res[n-1];
//    }

}
