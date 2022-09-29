package org.zgg.algorithms.recursion;

/*
*
* 求阶乘
*
* */
public class Factorial {
    public static void main(String[] agrs){
        System.out.println(factorial(5));
        System.out.println(factorialLoop(5));

    }

    //递归
    //f(4) = 4*3*2*1 = 4*f(3) = ... = 4*3*2*1*f(0)
    private static int factorial(int n){
        if(n == 0){
            return 1;    // 递归终止条件
        }
        return n*factorial(n-1);   //递推公式
    }

    //循环实现
    private static int factorialLoop(int n){
        int rlt = n;
        while(n>1){
            n--;
            rlt *= n;
        }
        return rlt;
    }
}
