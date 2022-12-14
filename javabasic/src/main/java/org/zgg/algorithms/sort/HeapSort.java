package org.zgg.algorithms.sort;

/**
 *  堆排序
 *
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr={6,2,8,4,1,7};
        heapSort(arr);
        for (int s : arr) {
            System.out.println(s);
        }
    }

    public static void heapSort(int[] pq){
        if(pq.length == 0) return;
        sort(pq);
    }

    private static void sort(int[] pq) {
        int n = pq.length;
        for (int k = n/2; k >= 1; k--)
            sink(pq, k, n);
        while (n > 1) {
            exch(pq, 1, n--);
            sink(pq, 1, n);
        }
    }

    private static void sink(int[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j, j+1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(int[] pq, int i, int j) {
        return pq[i-1] - pq[j-1] < 0;
    }

    private static void exch(int[] pq, int i, int j) {
        int swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }
}


