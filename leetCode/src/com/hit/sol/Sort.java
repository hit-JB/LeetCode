package com.hit.sol;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args){
        Sort sort = new Sort();
        int[] nums = new int[]{4,1,3,2,16,9,10,14,8,7};
        sort.quickSort(nums,0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
    public void heapSort(int[] nums){//原地操作heap-size = nums-size,注意在建堆的时候下标是从1开始，
        //而数组下标从0开始
        for(int i = nums.length / 2;i>=1;i--){
            maxHeapFy(nums,i,nums.length);
        }
        System.out.println(Arrays.toString(nums));

        for(int i=nums.length;i>=1;i--){
            int temp = nums[0];
            nums[0] = nums[i-1];
            nums[i-1] = temp;
            maxHeapFy(nums,1,i-1);
        }
        System.out.println(Arrays.toString(nums));
    }
    public void maxHeapFy(int[] nums,int i,int heapSize){
        int left = 2 * i,right = 2*i+1;
        if(left>heapSize)
            return;
        int largest = i;
        if(nums[left-1] > nums[i-1])
            largest = left;
        if(right<=heapSize && nums[right-1] > nums[largest-1])
            largest =right;
        int temp = nums[i-1];
        if(i!=largest){
            nums[i-1] = nums[largest-1];
            nums[largest-1] = temp;
            maxHeapFy(nums,largest,heapSize);
        }
    }
    /*
    快速排序，重点是要找到分治点，然后分而治之
     */
    public void quickSort(int[] nums,int p,int r){
        if(p<r){
            int q = partition(nums,p,r);
            quickSort(nums,p,q-1);
            quickSort(nums,q+1,r);
        }
    }
    public int partition(int[] nums,int p,int r){
        int par = nums[r];
        int i = p-1;
        for(int j=p;j<=r-1;j++){
            if(nums[j]<=par){
                i=i+1;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[i+1];
        nums[i+1] = par;
        nums[r] = temp;
        return i+1;
    }
}
