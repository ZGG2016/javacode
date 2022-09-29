package org.zgg.leetcode.Dynamic_Programming;
/*
*
* 304. Range Sum Query 2D - Immutable
*
* 边界情况：
* Construct a 2D array sums[row+1][col+1]
* we add additional blank row sums[0][col+1]={0} and blank column sums[row+1][0]={0} to remove the edge case checking),
*    so, we can have the following definition
* sums[i+1][j+1] represents the sum of area from matrix[0][0] to matrix[i][j]
*
* */
public class test15 {

    private int[][] mat;
    public test15(int[][] matrix){
        if(   matrix           == null
                || matrix.length    == 0
                || matrix[0].length == 0   ){
            return;
        }

        int m = matrix.length+1;
        int n = matrix[0].length+1;
        mat = new int[m][n];
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                mat[i][j] = mat[i-1][j]+mat[i][j-1]-mat[i-1][j-1]+matrix[i-1][j-1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2){
        int iMin = Math.min(row1, row2);
        int iMax = Math.max(row1, row2);

        int jMin = Math.min(col1, col2);
        int jMax = Math.max(col1, col2);

        return mat[iMax + 1][jMax + 1] - mat[iMax + 1][jMin] - mat[iMin][jMax + 1] + mat[iMin][jMin];
    }
//方法2，思想一样  i,j为0时的边界情况
//    private int[][] mat;
//    public test15(int[][] matrix) {
//        mat = matrix;
//        for(int i = 0; i < matrix.length; i++){
//            for(int j = 0; j < matrix[0].length; j++){
//                mat[i][j] += (j>0 ? mat[i][j-1] : 0) + (i>0 ? mat[i-1][j] : 0) - (i>0&&j>0?mat[i-1][j-1]:0);
//            }
//        }
//    }
//
//    public int sumRegion(int row1, int col1, int row2, int col2) {
//        return mat[row2][col2] - (col1>0 ? mat[row2][col1-1]:0) - (row1>0?mat[row1-1][col2]:0) + (col1>0 && row1>0 ? mat[row1-1][col1-1]:0);
//    }
}
