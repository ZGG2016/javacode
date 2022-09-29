package org.zgg.algorithms.recursion;

/*
* 二分查找：
*       原理：中间位置的数来与待查找的关键字进行比较，每次缩小一半的查找范围，直到匹配成功。
*       适用场景：顺序存储结构且按有序排列
* */

public class BinarySearch {
    public static void main(String[] args){
        //int[] arr = {3};
        int[] arr = {1,2,3,4,5,6,7};
        System.out.println(bsLoop(3,arr));
        System.out.println(bsRecursion(3,0,arr.length-1,arr));
    }

    //循环
    private static int bsLoop(int target,int[] arr){
        int hi = arr.length-1;
        int lo = 0;

        //防止越界
        if (target < arr[lo] || target > arr[hi] || lo>hi) {
            return -1;
        }

        while(lo<=hi){
            int mid = lo+(hi-lo)/2;

            if(arr[mid]>target){
                hi = mid-1;
            }else if(arr[mid]<target){
                lo = mid+1;
            }else{
                return arr[mid];
            }
        }
        return -1;
    }

    //递归
    private static int bsRecursion(int target,int lo,int hi,int[] arr){
        //防止越界
        if (target < arr[lo] || target > arr[hi] || lo>hi) {
            return -1;
        }

        int mid = lo+(hi-lo)/2;
        if(arr[mid]>target){
            return bsRecursion(target,lo,mid-1,arr);
        }else if(arr[mid]<target){
            return bsRecursion(target,mid+1,hi,arr);
        }else{
            return arr[mid];
        }
    }
}
