package org.zgg.algorithms.sort;

public class ShellSort {

	public static void main(String[] args) {
		int[] arr={6,2,8,4,1,7};
		shellSort(arr);
		for (int value : arr) {
			System.out.println(value);
		}
	}

	public static void shellSort(int[] arr){
		if(arr.length == 0) return;
		sort(arr);
	}

	public static void sort(int[] arr){
		int n=arr.length;
		int h=1;
		while(h<n/3){
			h=h*3+1;
		}
		while(h>=1){
			for(int i=h;i<n;i++){
			    //for(int j=i;j-h>=0 && arr[j]<arr[j-h];j-=h){
				for(int j=i;j>=h && arr[j]<arr[j-h];j-=h){
					exch(arr,j,j-h);
				}
			}
			h /= 3;
			
		}
	}

    private static void exch(int[] arr, int i, int j){
        int t=arr[j];
        arr[j]=arr[i];
        arr[i]=t;
    }

}
