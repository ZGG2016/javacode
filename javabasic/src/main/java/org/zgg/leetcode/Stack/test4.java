package org.zgg.leetcode.Stack;

import java.util.Stack;

/*
* 682. Baseball Game
*
* */
public class test4 {

    public int calPoints(String[] ops) {
//        int sum = 0;
//        Example.LinkedList<Integer> list = new Example.LinkedList<>();
//        for (String op : ops) {
//            if (op.equals("C")) {
//                sum -= list.removeLast();
//            }
//            else if (op.equals("D")) {
//                list.add(list.peekLast() * 2);
//                sum += list.peekLast();
//            }
//            else if (op.equals("+")) {
//                list.add(list.peekLast() + list.get(list.size() - 2));
//                sum += list.peekLast();
//            }
//            else {
//                list.add(Integer.parseInt(op));
//                sum += list.peekLast();
//            }
//        }
//        return sum;

        int ans = 0;
        Stack<Integer> stk = new Stack<>();
        for (String op : ops) {
            if (op.equals("C")) { ans -= stk.pop (); continue; }
            else if (op.equals ("D")) stk.push (stk.peek () * 2);
            else if (op.equals ("+")) stk.push (stk.get(stk.size () - 1) + stk.get(stk.size () - 2));
            else stk.push (Integer.valueOf (op));
            ans += stk.peek();
        }
        return ans;
    }


}


//    def calPoints(self, ops):
//        # Time: O(n)
//        # Space: O(n)
//        history = []
//        for op in ops:
//        if op == 'C':
//        history.pop()
//        elif op == 'D':
//        history.append(history[-1] * 2)
//        elif op == '+':
//        history.append(history[-1] + history[-2])
//        else:
//        history.append(int(op))
//        return sum(history)
