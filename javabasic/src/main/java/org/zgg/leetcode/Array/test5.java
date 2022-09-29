package org.zgg.leetcode.Array;
/*
*
* 26. Remove Duplicates from Sorted Array
*
* */
public class test5 {
    public int removeDuplicates(int[] arr){
        int j=0;
        for(int a:arr){
            if(j==0||arr[j-1]<a){  //比较相邻元素，因为有序
                arr[j++]=a;
            }
        }
        return j;
    }
}
//？？？？？？
//    public static int removeDuplicates(int A[], int n) {
//        if(n < 2) return n;
//        int id = 1;
//        for(int i = 1; i < n; ++i)
//            if(A[i] != A[i-1]) A[id++] = A[i];
//        return id;
//    }


//if (A.length==0) return 0;
//        int j=0;
//        for (int i=0; i<A.length; i++)
//        if (A[i]!=A[j]) A[++j]=A[i];
//        return ++j;