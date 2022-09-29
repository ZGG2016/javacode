package org.zgg.leetcode.Stack;

import java.util.Arrays;
import java.util.Stack;

/*
* 503. Next Greater Element II
*
* */
public class test2 {

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<Integer>();
        int[] arr = new int[n];
        Arrays.fill(arr,-1);

        for(int i=0;i<n*2;i++){
            int num = nums[i%n]; //让一个数组重复出现两次
            while(!stack.isEmpty() && nums[stack.peek()]<num){ arr[stack.pop()]=num; }
            if(i<n) stack.push(i);
        }
        return arr;
    }
}
