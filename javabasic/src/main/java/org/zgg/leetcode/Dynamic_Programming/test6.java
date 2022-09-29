package org.zgg.leetcode.Dynamic_Programming;
/*
* 213. House Robber II
*
* */
public class test6 {

//    /*
//    *
//    * We can divide this problem to two sub problems:
//    * Let's take following example:
//    * Subproblem 1: rob house 1 ~ 8
//    * Subproblem 2: rob house 2 ~ 9
//    * And find the bigger one of these two sub problems.
//    *
//    * */
//    public int rob(int[] nums) {
//        if (nums.length == 1) return nums[0];
//        return Math.max(rob0(nums), rob1(nums));
//    }
//
//    public int rob0(int[] nums){
//        int preMax = 0, curMax = 0;
//        for(int i = 0; i < nums.length - 1; i++){
//            int t = curMax;
//            curMax = Math.max(preMax + nums[i], curMax);
//            preMax = t;
//        }
//        return curMax;
//    }
//
//    public int rob1(int[] nums){
//        int preMax = 0, curMax = 0;
//        for(int i = 1; i < nums.length; i++){
//            int t = curMax;
//            curMax = Math.max(preMax + nums[i], curMax);
//            preMax = t;
//        }
//        return curMax;
//    }

    public int rob(int[] nums) {
        return Math.max(rob1(nums,0,nums.length-1),rob1(nums,1,nums.length));
    }

    private int rob1(int[] nums, int lo, int hi) {
        if(lo==hi) return nums[0];
        int curNo=0,curYes=0;
        for(int i=lo;i<hi;i++){
            int temp = curNo;
            curNo = Math.max(curNo,curYes);
            curYes = nums[i]+temp;
        }
        return Math.max(curNo,curYes);
    }

}
//???
//https://leetcode.com/problems/house-robber-ii/discuss/59934/Simple-AC-solution-in-Java-in-O(n)-with-explanation