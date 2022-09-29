package org.zgg.leetcode.Divide_and_Conquer;
/*
*
* 169. Majority Element
* */
import java.util.Arrays;

public class test2 {

//    public int majorityElement1(int[] nums) {
//        Arrays.someExample.sort(nums);
//        return nums[nums.length/2];  //the majority element is the element that appears more than ⌊n/2⌋ times
//    }
//
//    public int majorityElement2(int[] nums) {
//        Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
//        int ret=0;
//        for (int num: nums) {
//            if (!myMap.containsKey(num))
//                myMap.put(num, 1);
//            else
//                myMap.put(num, myMap.get(num)+1);
//            if (myMap.get(num)>nums.length/2) {
//                ret = num;
//                break;
//            }
//        }
//        return ret;
//    }

    public int majorityElement3(int[] nums) {   //分治
        if (nums.length == 1) return nums[0];
        int leftM = majorityElement3(Arrays.copyOfRange(nums, 0, nums.length/2));
        int rightM = majorityElement3(Arrays.copyOfRange(nums, nums.length/2, nums.length));
        int counterLeft = 0;
        for (int num : nums) {  //找左右两边出现次数最多的数后，遍历数组，找全局的最多。
            if (num == leftM) counterLeft++;
            if (counterLeft > nums.length/2) return leftM;
        }
        return rightM;
    }


}
