package org.zgg.leetcode.Stack;

import java.util.Stack;

/*
* 20. Valid Parentheses
*
* */
public class test5 {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for(char ch : s.toCharArray()){
            if(ch == '('){ stack.push(')'); }
            else if(ch == '['){ stack.push(']'); }
            else if(ch == '{'){ stack.push('}'); }
            else if(stack.isEmpty() || ch != stack.pop()){ return false;}
        }

        return stack.isEmpty();

    }
}


/*
*
*
* 单引号引的数据 是char类型的
* 双引号引的数据 是String类型的
*
*
* ==与equls的区别：https://blog.csdn.net/tcytcy123/article/details/50836323
*
* */