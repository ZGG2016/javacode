package org.zgg.leetcode.Stack;
/*
* 496. Next Greater Element I
*
* */
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class test1 {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer,Integer> m = new HashMap<Integer,Integer>();

        Stack<Integer> stack = new Stack<Integer>();
        for(int n:nums2){
            while(!stack.isEmpty() && stack.peek()<n){ m.put(stack.pop(),n); }
            stack.push(n);
        }

        for(int i=0;i<nums1.length;i++){
            nums1[i] = m.getOrDefault(nums1[i],-1);
        }
        return nums1;
    }

//    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
//        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//        for (int i = 0; i < nums2.length; ++i) {
//            for (int j = i + 1; j < nums2.length; ++j)
//                if (nums2[j] > nums2[i]) {
//                    map.put(nums2[i], nums2[j]);
//                    break; }//跳出内层循环
//            map.put(nums2[i], map.containsKey(nums2[i]) ? map.get(nums2[i]) : -1); }
//        for (int i = 0; i < nums1.length; ++i)
//            nums1[i] = map.get(nums1[i]);
//        return nums1;
}


//    // JDK8之前的实现方法
//    String capitalGeorgia = statesAndCapitals.get("Georgia");
//if (capitalGeorgia == null)
//        {
//        capitalGeorgia = "Unknown";
//        }
//
//// JDK8的实现方法
//final String capitalWisconsin = statesAndCapitals.getOrDefault("Wisconsin", "Unknown");


// =================python
//    def nextGreaterElement(self, findNums, nums):
//        st, d = [], {}
//
//        for v in nums:
//          while len(st) and st[-1] < v:
//              d[st.pop()] = v
//          st.append(v)
//
//        return map(lambda x: d.get(x, -1), findNums)