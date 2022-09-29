package org.zgg.leetcode.Array;
/*
* 268. Missing Number
*
* */
public class test7 {
    public int missingNumber(int[] nums) {
        int n = nums.length ;
        for(int i=0;i<nums.length;i++){
            n = n^i^nums[i];
        }
        return n;
    }
}

//a^b^b=a