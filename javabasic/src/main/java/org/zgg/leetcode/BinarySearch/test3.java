package org.zgg.leetcode.BinarySearch;
/*
* 74. Search a 2D Matrix
*
* */
public class test3 {
    public boolean searchMatrix(int[][] matrix, int target){
    /*
    * 把矩阵当做有序list
    * n * m matrix convert to an array => matrix[x][y] => a[x * m + y]
    * an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];
    *
    * */
        if (matrix.length == 0 || matrix[0].length == 0) return false;

        int m = matrix.length,n = matrix[0].length;
        int lo=0,hi=m*n-1;

        while(lo<=hi){
            int mid = lo+(hi-lo)/2;
            int mid_value = matrix[mid/n][mid%n];
            if(mid_value==target) return true;
            else if(mid_value<target) lo=mid+1;
            else hi=mid-1;
        }
        return false;
    }
}
