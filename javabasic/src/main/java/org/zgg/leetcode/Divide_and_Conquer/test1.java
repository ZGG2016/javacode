package org.zgg.leetcode.Divide_and_Conquer;
/*
* 53. Maximum Subarray
* https://www.cnblogs.com/lixing-nlp/p/7629403.html
* */
public class test1 {
//    public int maxSubArray(int[] A){
//        int max = Integer.MIN_VALUE,sum=0;
//        for (int aA : A) {
//            if (sum < 0)
//                sum = aA;
//            else
//                sum += aA;
//            if (max < sum) {
//                max = sum;
//            }
//        }
//        return max;
//
//    }

        public int maxSubArray(int[] nums) {
            return Subarray(nums, 0 ,nums.length -1 );
        }

        private int Subarray(int[] A, int left, int right){
            if(left == right){return A[left];}
            int mid = left + (right - left) / 2;
            int leftSum = Subarray(A,left,mid);// left part
            int rightSum = Subarray(A,mid+1,right);//right part
            int crossSum = crossSubarray(A,left,right);// cross part
            if(leftSum >= rightSum && leftSum >= crossSum){// left part is max
                return leftSum;
            }
            if(rightSum >= leftSum && rightSum >= crossSum){// right part is max
                return rightSum;
            }
            return crossSum; // cross part is max
        }

        private int crossSubarray(int[] A,int left,int right){
            int leftSum = Integer.MIN_VALUE;
            int rightSum = Integer.MIN_VALUE;
            int sum = 0;
            int mid = left + (right - left) / 2;
            for(int i = mid; i >= left ; i--){
                sum = sum + A[i];
                if(leftSum < sum){
                    leftSum = sum;
                }
            }
            sum = 0;
            for(int j = mid + 1; j <= right; j++){
                sum = sum + A[j];
                if(rightSum < sum){
                    rightSum = sum;
                }
            }
            return leftSum + rightSum;
        }


}
