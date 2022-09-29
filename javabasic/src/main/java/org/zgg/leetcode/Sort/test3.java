package org.zgg.leetcode.Sort;

public class test3 {

    public boolean isAnagram(String s, String t) {

        if(s.length()!=t.length()) return false;

        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        int[] arr = new int[26];

        for(int i=0;i<ch1.length-1;i++){

            arr[ch1[i]-'a']++;  //ȷ��λ��
            arr[ch2[i]-'a']--;

        }
        for(int n:arr){
            if(n!=0) return false;
        }
        return true;
    }
// =========================================================

//    if (s.length() != t.length()) {
//        return false;
//    }
//    int[] counter = new int[26];
//    for (int i = 0; i < s.length(); i++) {
//        counter[s.charAt(i) - 'a']++;
//        counter[t.charAt(i) - 'a']--;
//    }
//    for (int count : counter) {
//        if (count != 0) {
//            return false;
//        }
//    }
//    return true;

}
