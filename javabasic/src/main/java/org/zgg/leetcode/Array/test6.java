package org.zgg.leetcode.Array;
/*
* 88. Merge Sorted Array
*
* */
public class test6 {
    public void merge(int nums1[], int m, int nums2[], int n){
        //出错，会重复使用值，只能重新定义一个数组
//        int i=0,j=0,k=0;
//        while(i<m && j<n){
//            if(A[i]>B[j]) A[k++]=A[i++];
//            else A[k++]=B[j++];
//        }
//
//        while(j<n){
//            A[k++]=B[j++];
//        }
// =============================================
        int i=m-1,j=n-1,k=m+n-1;

        while(i>=0 && j>=0){
            if(nums1[i]>nums2[j]) nums1[k--]=nums1[i--];
            else nums1[k--]=nums2[j--];
        }

        while(j>=0){
            nums1[k--]=nums2[j--];
        }
    }
}
