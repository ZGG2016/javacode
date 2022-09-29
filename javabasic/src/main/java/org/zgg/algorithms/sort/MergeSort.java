package org.zgg.algorithms.sort;

public class MergeSort {
	public static void main(String[] args) {
		int[] arr = { 2, 7, 8, 3, 1, 6, 9, 0, 5, 4 };
        mergeSort(arr);
        for (int s : arr) {
            System.out.println(s);
        }
	}

    public static void mergeSort(int[] arr){
	    if(arr.length == 0) return;

        int[] aux = new int[arr.length];
        sort(arr,aux,0,arr.length-1);
    }

    private static void sort(int[] arr, int[] aux, int lo, int hi) {

        if (hi <= lo) return;

        int mid = lo + (hi - lo) / 2;
        sort(arr, aux,lo, mid);
        sort(arr, aux,mid + 1, hi);
        merge(arr, aux,lo, mid, hi);
    }

    private static void merge(int[] arr, int[] aux, int lo, int mid, int hi){


		for (int k = lo; k <= hi; k++) {
            aux[k] = arr[k];
        }

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              arr[k] = aux[j++];
            else if (j > hi)               arr[k] = aux[i++];
            else if (aux[j]<aux[i])        arr[k] = aux[j++];
            else                           arr[k] = aux[i++];
        }
	}
}

//    public static void merge(int[] nums, int low, int mid, int high) {
//        int[] temp = new int[high - low + 1];
//
//        int i = low;
//        int j = mid + 1;
//        int k = 0;
//
//        while (i <= mid && j <= high) {
//            if (nums[i] < nums[j]) {
//                temp[k++] = nums[i++];
//            } else {
//                temp[k++] = nums[j++];
//            }
//        }
//
//        while (i <= mid) {
//            temp[k++] = nums[i++];
//        }
//
//        while (j <= high) {
//            temp[k++] = nums[j++];
//        }
//
//        for (int k2 = 0; k2 < temp.length; k2++) {
//            nums[k2 + low] = temp[k2];
//        }