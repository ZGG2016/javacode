package org.zgg.algorithms.sort;

public class InsertionSort {

	public static void main(String[] args) {
		int[] arr={6,2,8,4,1,7};
        insertionSort(arr);
        for (int s : arr) {
            System.out.println(s);
        }
	}

    public static void insertionSort(int[] arr) {
        if(arr.length == 0) return;
        sort(arr);
    }

    private static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
                exch(arr,j,j-1);
            }
        }
    }

    private static void exch(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;

    }
}