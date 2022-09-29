package org.zgg.leetcode.Dynamic_Programming;
/*
* 264. Ugly Number II
*
* An ugly number must be multiplied by either 2, 3, or 5 from a smaller ugly number.
*
* Assume you have Uk, the kth ugly number. Then Uk+1 must be Min(L1 * 2, L2 * 3, L3 * 5).
*
* https://leetcode.com/problems/ugly-number-ii/discuss/69362/O(n)-Java-solution
* */
public class test13 {
    public int nthUglyNumber(int n){
        int[] arr = new int[n];
        arr[0] = 1;
        int f2=2,f3=3,f5=5;
        int i2=0,i3=0,i5=0;
        for(int i=1;i<n;i++){
            arr[i] = Math.min(Math.min(f2,f3),f5);

            if(arr[i]==f2) f2 = 2*arr[++i2];  //存在f2=f3的情况，不会出现重复。
            if(arr[i]==f3) f3 = 3*arr[++i3];
            if(arr[i]==f5) f5 = 5*arr[++i5];
        }
        return arr[n-1];
    }
}
