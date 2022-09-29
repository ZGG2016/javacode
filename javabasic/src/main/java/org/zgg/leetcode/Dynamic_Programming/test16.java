package org.zgg.leetcode.Dynamic_Programming;//package LeetCode.Dynamic_Programming;
//
//import edu.princeton.cs.algs4.In;
//
///*
//*
//* 309. Best Time to Buy and Sell Stock with Cooldown
//*
//*   https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)
//*   https://www.cnblogs.com/grandyang/p/4997417.html
//*   s0、s1、s2状态的转移
//*
//* */
//public class test16 {
//    public static void main(String[] args){
//        int[] num = {1,2,3,0,2};
//        System.out.println(maxProfit(num));
//    }
//
//    public static int maxProfit(int[] prices) {
//
//        int len = prices.length;
//        if(len==0) return 0;
//        int[] s0 = new int[len];
//        int[] s1 = new int[len];
//        int[] s2 = new int[len];
//
//        s0[0] = 0 ; s1[0] = -prices[0] ; s2[0] = Integer.MIN_VALUE;
//
//        for(int i=1;i<len;i++){
//            s0[i] = Math.max(s0[i-1],s2[i-1]);
//            s1[i] = Math.max(s1[i-1],s0[i-1]-prices[i]);
//            s2[i] = s1[i-1]+prices[i];
//        }
//        return Math.max(s0[len-1],s2[len-1]);
//    }
//
//    public int maxProfit2(int[] prices) {
//        int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE, prev_buy;
//        for (int price : prices) {
//            prev_buy = buy;
//            buy = Math.max(prev_sell - price, prev_buy);
//            prev_sell = sell;
//            sell = Math.max(prev_buy + price, prev_sell);
//        }
//        return sell;
//    }
//}