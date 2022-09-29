package org.zgg.leetcode.Dynamic_Programming;

import java.util.Arrays;

/*
* 300. Longest Increasing Subsequence   (******)
*
* */
public class test14 {
//二分查找

//    public static int findPositionToReplace(int[] a, int low, int high, int x) {
//        int mid;
//        while (low <= high) {
//            mid = low + (high - low) / 2;
//            if (a[mid] == x)
//                return mid;
//            else if (a[mid] > x)
//                high = mid - 1;
//            else
//                low = mid + 1;
//        }
//        return low;
//    }
//
//    public static int lengthOfLIS1(int[] nums) {
//        if (nums == null | nums.length == 0)
//            return 0;
//        int n = nums.length, len = 0;
//        int[] increasingSequence = new int[n];
//        increasingSequence[len++] = nums[0];
//        for (int i = 1; i < n; i++) {
//            if (nums[i] > increasingSequence[len - 1])
//                increasingSequence[len++] = nums[i];
//            else {
//                int position = findPositionToReplace(increasingSequence, 0, len - 1, nums[i]);
//                increasingSequence[position] = nums[i];
//            }
//        }
//        return len;
//    }

    //动态规划
    //dp[i] represents the length of the longest increasing subsequence possible considering
    // the array elements upto the i-​th index only，by necessarily including the i-​th element.
    //在索引为i的元素前的最长公共子序列的长度，包含i
    //https://leetcode.com/problems/longest-increasing-subsequence/solution/
    public int lengthOfLIS(int[] nums){
        if(nums.length==0) return 0;
        int[] dp = new int[nums.length];
        dp[0]=1;
        int maxrlt = 1;
        for(int i=1;i<nums.length;i++){
            int maxcur = 0;
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]) maxcur = Math.max(maxcur,dp[j]); //选哪个路径
            }
            dp[i] = maxcur+1; //本身
            maxrlt = Math.max(maxrlt,dp[i]);
        }
        return maxrlt;
    }
    //动态规划+二分查找
    //https://leetcode.com/problems/longest-increasing-subsequence/solution/
    public int lengthOfLIS2(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;  //目前为止形成的LIS的长度
        for (int num : nums) {
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;  //达到这个长度时，扩充
            }
        }
        return len;
    }
}
