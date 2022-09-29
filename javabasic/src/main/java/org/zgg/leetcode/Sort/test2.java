package org.zgg.leetcode.Sort;

import java.util.ArrayList;
import java.util.HashMap;

/*
*
* 350. Intersection of Two Arrays II
* */
public class test2 {

    public int[] intersect(int[] nums1, int[] nums2) {
//        List<Integer> list1 = new ArrayList<Integer>();
//        List<Integer> list2 = new ArrayList<Integer>();
//
//        for(int n1 : nums1){list1.add(n1);}
//
//        for(int n2:nums2){
//            if(list1.contains(n2)){
//                list2.add(n2);
//                list1.remove(list1.indexOf(n2));
//            }
//        }
//
//        int[] arr = new int[list2.size()];
//        int i=0;
//        for(Integer s : list2){
//            arr[i++]=s;
//        }
//
//        return arr;
//=================================================================

//        ArrayList<Integer> arraylist = new ArrayList<Integer>();
//        Arrays.someExample.sort(nums1);
//        Arrays.someExample.sort(nums2);
//        int index1 = 0;
//        int index2 = 0;
//        while (index1 < nums1.length && index2 < nums2.length) {
//            if (nums1[index1] == nums2[index2]) {
//                arraylist.add(nums1[index1]);
//                index1++;
//                index2++;
//            } else if (nums1[index1] < nums2[index2]) {
//                index1++;
//            } else {
//                index2++;
//            }
//        }
//        int answer[] = new int[arraylist.size()];
//        for (int i = 0; i < arraylist.size(); i++) {
//            answer[i] = arraylist.get(i);
//        }
//        return answer;
//=================================================================

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < nums1.length; i++)
        {
            if(map.containsKey(nums1[i])) map.put(nums1[i], map.get(nums1[i])+1);
            else map.put(nums1[i], 1);
        }

        for(int i = 0; i < nums2.length; i++)
        {
            if(map.containsKey(nums2[i]) && map.get(nums2[i]) > 0)
            {
                result.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i])-1);
            }
        }

        int[] r = new int[result.size()];
        for(int i = 0; i < result.size(); i++)
        {
            r[i] = result.get(i);
        }

        return r;
    }
}
