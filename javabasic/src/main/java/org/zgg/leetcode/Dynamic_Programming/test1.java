package org.zgg.leetcode.Dynamic_Programming;
/*
* 121. Best Time to Buy and Sell Stock
* */
public class test1 {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0 ;
        }
        int maxp=0;
        int min=prices[0];
        for(int i=0;i<prices.length;i++){
            if(prices[i]>min){
                maxp = Math.max(maxp,prices[i]-min);
            }else{
                min = prices[i];
            }
        }

        return maxp;


//        int min = Integer.MAX_VALUE, max = 0;
//        for (int i = 0; i < prices.length; i++) {
//            min = Math.min(min, prices[i]);
//            max = Math.max(max, prices[i] - min);
//        }
//        return max;
    }
}

//
//对这种情况{0, 6, -3, 7}  Kadane's Algorithm   保存历史信息
//    public int maxProfit(int[] prices) {
//        int maxCur = 0, maxSoFar = 0;
//        for(int i = 1; i < prices.length; i++) {
//            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
//            maxSoFar = Math.max(maxCur, maxSoFar);
//        }
//        return maxSoFar;
//    }
//maxCur = current maximum value
//maxSoFar = maximum value found so far