package org.zgg.leetcode.Dynamic_Programming;
/*
*
* 152. Maximum Product Subarray
*
* 1.可能存在两个负数相乘。本来是最小值，成一个负数后，可能成为最大值
* 2.乘完一个数后，max有可能减小
*
* */
public class test12 {
    public int maxProduct(int[] nums) {
        if(nums.length == 1) return nums[0];

        int max=nums[0],min=nums[0],rlt=nums[0];
        for(int i=1;i<nums.length;i++){
            int tmp = max;
            max = Math.max(Math.max(nums[i]*max,nums[i]*min),nums[i]);
            min = Math.min(Math.min(nums[i]*tmp,nums[i]*min),nums[i]);
            if(max>rlt){
                rlt=max;
            }
        }
        return rlt;
    }
}
