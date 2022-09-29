package org.zgg.leetcode.Divide_and_Conquer;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
*
* 215. Kth Largest Element in an Array
* */
public class test4 {
    public int findKthLargest1(int[] nums, int k) {
        final int N = nums.length;
        Arrays.sort(nums);
        return nums[N - k];
    }
    //???
    public int findKthLargest2(int[] nums, int k) {

        final PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int val : nums) {
            pq.offer(val);

            if(pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }
}
