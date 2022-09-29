package org.zgg.leetcode.Dynamic_Programming;
/*
*
* 198. House Robber
* */
public class test4 {

    public int rob(int[] nums) {
        int len = nums.length;
        if(len==0) return 0;
        else if(len==1) return nums[0];

        int curNo=0; //不抢当前这家
        int curYes=0; //抢当前这家
        for(int n:nums){
            int temp = curNo;
            curNo = Math.max(curNo,curYes);  //如果不抢当前家，max 依赖于前一家
            curYes = n+temp;
        }
        return Math.max(curNo,curYes);
    }
}
