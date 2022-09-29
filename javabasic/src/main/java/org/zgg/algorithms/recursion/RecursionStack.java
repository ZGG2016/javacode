package org.zgg.algorithms.recursion;

/*
* 递归中 栈特性理解
*
* */
public class RecursionStack {

    public static void main(String[] agrs){
        recursion_display(3);
    }

    private static void recursion_display(int n) {
        int temp=n;//保证前后打印的值一样
        System.out.println("递进:" + temp);
        if (n > 0) {
            recursion_display(--n);
        }
        System.out.println("回归:" + temp);
    }
}
//递进:3
//递进:2
//递进:1
//递进:0
//回归:0
//回归:1
//回归:2
//回归:3
