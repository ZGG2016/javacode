package org.zgg.leetcode.Divide_and_Conquer;
/*
* 240. Search a 2D Matrix II
* */
public class test5 {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length < 1 || matrix[0].length <1) {
            return false;
        }
        int m = matrix.length, n = matrix[0].length, i = 0, j = n - 1;

        while (i < m && j >= 0) {
            if (matrix[i][j] == target) return true;
            else if (matrix[i][j] < target) i++;
            else j--;
        }
        return false;
    }

}
