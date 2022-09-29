package org.zgg.leetcode.Math;
/*
* 263. Ugly Number
*
* 因子只能包含2,3,5中的一个或多个
* */

public class test1 {
    public boolean isUgly(int num) {
        for(int i=2;i<6&&num>0;i++){
            while(num%i==0){
                num/=i;
            }
        }
        return num==1;
    }
}
