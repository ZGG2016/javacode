package org.zgg.leetcode.Dynamic_Programming;
/*
* 746. Min Cost Climbing Stairs
*
*   The arr[i] is the cost we need to get the i-th stair.
*
* */
public class test2 {

    public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;
        int[] arr = new int[len];
        arr[0] = cost[0];
        arr[1] = cost[1];

        for(int i=2;i<len;i++){
            arr[i] = cost[i]+Math.min(arr[i-1],arr[i-2]);
        }

        return Math.min(arr[len-1],arr[len-2]);
    }
}
