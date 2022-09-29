package org.zgg.leetcode.Array;
/*
*
* 189. Rotate Array
*
* */
public class test2 {
    public void rotate(int[] nums, int k) {
        if(nums == null || nums.length < 2){
            return;
        }

        k %= nums.length;  //����k > nums.length���
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}


//    def rotate(self, nums, k):
//        """
//        :type nums: List[int]
//        :type k: int
//        :rtype: void Do not return anything, modify nums in-place instead.
//        """
//        if k == 0:
//        return
//        index = len(nums) - k
//        nums[:] = nums[index:] + nums[:index] #O(1)