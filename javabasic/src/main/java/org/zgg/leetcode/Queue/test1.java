package org.zgg.leetcode.Queue;
/*
*
* 232. Implement Queue using Stacks
* */

import java.util.Stack;

public class test1 {
    class MyQueue {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();


        /** Initialize your data structure here. */
        public MyQueue() {

        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            s1.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            peek();
            return s2.pop();
        }

        /** Get the front element. */
        public int peek() {
            if(s2.empty()){
                while(!s1.empty()){
                    s2.push(s1.pop());
                }
            }
            return s2.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return s1.empty()&&s2.empty();
        }

    }
}

//或用链表