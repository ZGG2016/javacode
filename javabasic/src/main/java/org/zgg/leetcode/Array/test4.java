package org.zgg.leetcode.Array;
/*
* 27. Remove Element
*
* */
public class test4 {
    public int removeElement(int[] nums, int val) {

        int b=0;   //删除数组元素
        for(int num:nums){
            if(num!=val){
                nums[b++]=num;
            }
        }

        return b;
    }

}
