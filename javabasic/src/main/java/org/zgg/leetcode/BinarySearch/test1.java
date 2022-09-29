package org.zgg.leetcode.BinarySearch;
/*
*
* 35. Search Insert Position
*
* */
public class test1 {
    public int searchInsert(int[] nums, int target) {
//        for(int i=0;i<nums.length;i++){
//            if(target<=nums[i])return i;
//        }
//        return nums.length;
// ===============================================

        int low = 0, high = nums.length-1;
        while(low<=high){
            int mid = (low+high)/2;
            if(nums[mid] == target) return mid;
            else if(nums[mid] > target) high = mid-1;
            else low = mid+1;
        }
        return low;  //二分查找后没有找到，那么返回的low值是该值插入的位置
    }
}
