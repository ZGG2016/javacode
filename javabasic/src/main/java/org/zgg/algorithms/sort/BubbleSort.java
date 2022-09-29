package org.zgg.algorithms.sort;
/*
* 冒泡排序
* */
public class BubbleSort {

    public static void main(String[] args) {
        String[] arr={"X","A","V","L","F"};
        bubbleSort(arr);
        for (String s : arr) {
            System.out.println(s);
        }
    }

    public static void bubbleSort(String[] arr) {
        if(arr.length == 0) return;
        sort(arr);
    }

    private static void sort(Comparable[] arr){
        int n = arr.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                if(less(arr[j],arr[j+1])){
                    exch(arr,j,j+1);
                }
            }
        }
    }
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
