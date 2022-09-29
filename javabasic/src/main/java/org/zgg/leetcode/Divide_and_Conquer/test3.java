package org.zgg.leetcode.Divide_and_Conquer;
/*
* 241. Different Ways to Add Parentheses
*
* */
import java.util.LinkedList;
import java.util.List;

public class test3 {

    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> ret = new LinkedList<Integer>();
        for (int i=0; i<input.length(); i++) {
            if (input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '+' ) {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i+1);
                List<Integer> part1Ret = diffWaysToCompute(part1);
                List<Integer> part2Ret = diffWaysToCompute(part2);
                for (Integer p1 :   part1Ret) {
                    for (Integer p2 :   part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+': c = p1+p2;
                                break;
                            case '-': c = p1-p2;
                                break;
                            case '*': c = p1*p2;
                                break;
                        }
                        ret.add(c);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        return ret;
    }
}

//    private Map<String,List<Integer>> memo = new HashMap<>();
//
//    public List<Integer> diffWaysToCompute(String input) {
//        int len = input.length();
//        // check history
//        List<Integer> result = memo.get(input);
//        if (result != null) { return result; }
//        result = new ArrayList<>();
//        // base case
//        if (isDigit(input)) {
//            result.add(Integer.parseInt(input));
//            memo.put(input,result);
//            return result;
//        }
//        // recursion (divid & conquer)
//        for (int i = 0; i < len; i++) {
//            char c = input.charAt(i);
//            if (c == '+' || c == '-' || c == '*') {
//                List<Integer> left = diffWaysToCompute(input.substring(0,i));
//                List<Integer> right = diffWaysToCompute(input.substring(i+1,len));
//                for (Integer il : left) {
//                    for (Integer ir : right) {
//                        switch (c) {
//                            case '+': result.add(il + ir); break;
//                            case '-': result.add(il - ir); break;
//                            case '*': result.add(il * ir); break;
//                        }
//                    }
//                }
//            }
//        }
//        memo.put(input,result);
//        return result;
//    }
//    private boolean isDigit(String s) {
//        for (Character c : s.toCharArray()) {
//            if (!Character.isDigit(c)) { return false; }
//        }
//        return true;
//    }