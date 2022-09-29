package org.zgg.algorithms.sort;

public class SelectionSort {

	public static void main(String[] args) {
		int[] arr = {6,2,8,4,1,7};
		selectionSort(arr);
		for (int s : arr) {
			System.out.println(s);
		}
	}

	public static void selectionSort(int[] arr){
		if(arr.length == 0) return;
		sort(arr);
	}

	private static void sort(int[] a){

		int n =a.length;
		for(int i=0;i<n;i++){
			int min=i;
			for(int j=i+1;j<n;j++){
				if(a[j]<a[min]) min=j;
			}
			exch(a,i,min);
		}
	}

	private static void exch(int[] arr, int i, int j) {
       	int swap = arr[i];
		arr[i] = arr[j];
		arr[j] = swap;

	}
}
