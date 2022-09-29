package org.zgg.leetcode.Dynamic_Programming;
/*
* 338. Counting Bits
* */
public class test10 {

//    public int[] countBits1(int num) {
//        int[] arr = new int[num+1];
//        for(int i=0;i<=num;i++){
//            arr[i] = arr[i/2]+i%2;
//            //arr[i] = arr[i >> 1] + (i & 1);
//        }
//        return arr;
//    }

    //https://leetcode.com/problems/counting-bits/discuss/79557/How-we-handle-this-question-on-interview-Thinking-process-+-DP-solution
    public int[] countBits2(int num) {
        int result[] = new int[num + 1];
        int offset = 1;
        for (int index = 1; index < num + 1; ++index){
            if (offset * 2 == index){
                offset *= 2;
            }
            result[index] = result[index - offset] + 1;
        }
        return result;
    }
}


/*
* countBits1:
*
*  0 0     0
*  1 1     1
*  2 10    1
*  3 11    2
*  4 100   1
*  5 101   2
*
*  1->10/11  10->100/101 11->110/111
*  即循环地在每个数后面加0、1可得接下来的数字。
* */