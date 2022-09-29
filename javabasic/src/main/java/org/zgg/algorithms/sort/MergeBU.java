package org.zgg.algorithms.sort;

public class MergeBU {

	public static void main(String[] args) {
		int[] arr = { 2, 7, 8, 3, 1, 6, 9, 0, 5, 4 };
        mergeSort(arr);
        for (int s : arr) {
            System.out.println(s);
        }
    }

    public static void mergeSort(int[] arr){
        if(arr.length == 0) return;

        sort(arr);
    }

    private static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n-len; lo += len+len) {
                int mid  = lo+len-1;
                int hi = Math.min(lo+len+len-1, n-1);
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if(aux[j]<aux[i])         a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }

    }

}
