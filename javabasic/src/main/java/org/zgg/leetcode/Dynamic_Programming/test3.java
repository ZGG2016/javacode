package org.zgg.leetcode.Dynamic_Programming;
/*
*
* 70. Climbing Stairs
*
*  当前处在的位置n可能是从位置n-1来的，也有可能是从n-2来
*
* */
public class test3 {

    public int climbStairs(int n) {

        if(n==0) return 0;
        else if (n==1) return 1;
        else if (n==2) return 2;

        int one_step_before = 2;
        int two_step_before = 1;
        int s = 0;

        for(int i=3;i<=n;i++){
            s = one_step_before + two_step_before;
            two_step_before = one_step_before;
            one_step_before = s;
        }
        return s;
    }
}


//    public int climbStairs(int n) {
//        int a = 1, b = 1;
//        while (n-- > 0)
//            a = (b += a) - a;
//        return a;
//    }