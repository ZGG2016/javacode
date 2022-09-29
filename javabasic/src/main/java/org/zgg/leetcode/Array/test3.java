package org.zgg.leetcode.Array;
/*
* 283. Move Zeroes
*
* */
public class test3 {

    public void moveZeroes(int[] nums) {
        int ind = 0;
        for(int num:nums){
            if(num!=0){ nums[ind++]=num;} //先把非零值放进去
        }
        while(ind<nums.length){
            nums[ind++]=0;
        }

//        int j = 0;    //相邻比较，把0往后移。。。。两个指针
//        for(int i = 0; i < nums.length; i++) {
//            if(nums[i] != 0) {
//                int temp = nums[j];
//                nums[j] = nums[i];
//                nums[i] = temp;
//                j++;
//            }
//        }

    }
}

//    def moveZeroes(self, nums):
//        zero = 0  # records the position of "0"
//        for i in xrange(len(nums)):
//        if nums[i] != 0:
//        nums[i], nums[zero] = nums[zero], nums[i]
//        zero += 1