package org.zgg.leetcode.Dynamic_Programming;
/*
* 63. Unique Paths II
*
* */
public class test8 {
    //for every row in obstacleGrid , every element(except the 1st one) of dp inherits the a value from "old" dp,
    // in the same location. It means that new point will inherits the paths passing the above point.
    //then the new point will take in the paths come from its left.
    //if the value of the point is 1. no path can pass it so new dp will be 0.
    //the dp[0] should be always 1 indeed, because there is only one way going straight down.
//    public int uniquePathsWithObstacles(int[][] obstacleGrid){  //????
//        int len = obstacleGrid[0].length; //列数
//        int[] dp = new int[len];
//        dp[0]=1;
//
//        for(int[] ob : obstacleGrid){
//            for(int i=0;i<len;i++){
//                if(ob[i]==1){
//                    dp[i]=0;
//                }else if(i>0){
//                    dp[i]+=dp[i-1];
//                }
//            }
//        }
//        return dp[len-1];
//    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        //增加了是不是1的判断
        //obstacleGrid[0][0]^=1;
        obstacleGrid[0][0] = obstacleGrid[0][0]==0 ? 1:0;
        if(obstacleGrid[0][0]==0) return 0;
        for(int i = 1;i<m;i++){
            obstacleGrid[i][0]=(obstacleGrid[i][0]==1)? 0:obstacleGrid[i-1][0];
        }

        for(int j = 1;j<n;j++){
            obstacleGrid[0][j] =(obstacleGrid[0][j]==1)? 0: obstacleGrid[0][j-1];
        }
        for(int i = 1;i<m;i++){
            for(int j =1;j<n;j++){
                obstacleGrid[i][j] =(obstacleGrid[i][j]==1)? 0: obstacleGrid[i-1][j]+obstacleGrid[i][j-1];
            }
        }
        return obstacleGrid[m-1][n-1];

//        int m = obstacleGrid.length;
//        int n = obstacleGrid[0].length;
//        int[][] s = new int[m][n];
//        s[0][0] = obstacleGrid[0][0]==0 ? 1:0;
//        if(s[0][0] == 0) return 0;
//        for(int i=0;i<m;i++){
//            for(int j=0;j<n;j++){
//                if(obstacleGrid[i][j] == 1) s[i][j] = 0;
//                else if(i==0){
//                    if(j>0) s[i][j] = s[i][j-1];
//                }
//                else if(j==0){
//                    if(i>0) s[i][j] = s[i-1][j];
//                }
//                else s[i][j] = s[i-1][j] + s[i][j-1];
//            }
//        }
//        return s[m-1][n-1];
    }
}
