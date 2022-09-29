package org.zgg.leetcode.BinarySearch;
/*
*167. Two Sum II - Input array is sorted
*
* */
public class test2 {
    public int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (numbers[l] + numbers[r] != target) {
            if (numbers[l] + numbers[r] > target) r--;
            else l++;
        }
        return new int[]{l + 1, r + 1};
    }

}

// my way
//    int len = numbers.length;
//    int i=0;int j=len-1;
//    int[] arr = new int[2];
//        while(i<=j){
//                int s = numbers[i]+numbers[j];
//                if(s==target){
//                arr[0]=i+1;
//                arr[1]=j+1;
//                break;
//                }else if(s>target){
//                j--;
//                }else{
//                i++;
//                }
//                }
//                return arr;