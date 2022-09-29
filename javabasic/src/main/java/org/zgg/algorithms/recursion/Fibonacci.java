package org.zgg.algorithms.recursion;

/*
* 斐波那契
*
* */

public class Fibonacci {
    public static void main(String[] agrs){
        System.out.println(fibonacci(5));
        System.out.println(fibonacci(1,1,5));
        System.out.println(fibonacciLoop(5));
        System.out.println(fibonacciArray(5));

    }

    // 1、1、2、3、5、8、13、21、34
    //递归
    private static int fibonacci(int n){
        if(n == 1 || n == 2){
            return 1;
        }

        return fibonacci(n-1) + fibonacci(n-2);
    }
    //递归
    private static int fibonacci(int first, int second, int n) {
        if(n==1) return first;
        else if(n==2) return second;
        else if(n==3) return first+second;

        return fibonacci(second,first+second,n-1);
    }
    // 1、1、2、3、5、8、13、21、34
    //循环
    private static int fibonacciLoop(int n){
        int first = 1;
        int second = 1;
        int rlt = 1;
        int i = 3;
        while(i<=n){
            rlt = first + second;
            first = second;
            second = rlt;
            i++;
        }
        return rlt;
    }
    //递归，数组
    private static int fibonacciArray(int n){
        if(n>0){
            int[] tmp = new int[n];
            tmp[0]=1;tmp[1]=1;

            for(int i=2;i<n;i++){
                tmp[i]=tmp[i-1]+tmp[i-2];
            }

            return tmp[n-1];
        }
        return -1;
    }
}
