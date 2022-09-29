package org.zgg.leetcode.Sort;
/*
*
* 349. Intersection of Two Arrays
*
* */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class test1 {

    public int[] intersection(int[] nums1, int[] nums2) {
//        Set<Integer> set = new HashSet<Integer>();
//
//        for(int n1:nums1){
//            for(int n2:nums2){
//                if(n1==n2){
//                    set.add(n1);
//                }
//            }
//        }
//
//        int[] arr = new int[set.size()];  //���ܷ��ڿ�ͷ
//        int i=0;
//        for(Integer s : set){
//            arr[i++]=s;
//        }
//
//        return arr;
// ==============================================================

//        Set<Integer> set = new HashSet<>();
//        Arrays.someExample.sort(nums1);
//        Arrays.someExample.sort(nums2);
//        int i = 0;
//        int j = 0;
//        while (i < nums1.length && j < nums2.length) {
//            if (nums1[i] < nums2[j]) {
//                i++;
//            } else if (nums1[i] > nums2[j]) {
//                j++;
//            } else {
//                set.add(nums1[i]);
//                i++;
//                j++;
//            }
//        }
//        int[] result = new int[set.size()];
//        int k = 0;
//        for (Integer num : set) {
//            result[k++] = num;
//        }
//        return result;
//    }
// ==============================================================
        Set<Integer> set = new HashSet<>();
        Arrays.sort(nums2);
        for (Integer num : nums1) {
            if (binarySearch(nums2, num)) {
                set.add(num);
            }
        }
        int i = 0;
        int[] result = new int[set.size()];
        for (Integer num : set) {
            result[i++] = num;
        }
        return result;

    }

    public boolean binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
    }
}
