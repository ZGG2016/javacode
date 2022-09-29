package org.zgg.leetcode.Dynamic_Programming;
/*
*
* 303. Range Sum Query - Immutable
* */
public class test5 {
    public class NumArray {

        int[] sum;

        public NumArray(int[] nums) {
            for (int i = 1; i < nums.length; i++) {

                nums[i] += nums[i - 1];
            }

            this.sum = nums;
        }

        public int sumRange(int i, int j) {
            if (i == 0) return sum[j];

            return sum[j] - sum[i - 1];
        }
    }
}
