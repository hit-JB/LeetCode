package com.hit.sol;


import com.hit.bean.Node;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class Sort {
    public static interface count{
        int getNum();
    }
    public static class countImpl implements count{

        @Override
        public int getNum() {
            return 0;
        }
    }
    public static void main(String[] args){
        Sort sort = new Sort();
        int[] ints = sort.nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4});
        System.out.println(Arrays.toString(ints));
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
    public String shortestPalindrome(String s) {
        long base = 31,mod = (long) (Math.pow(10,9)+7);
        long prefix = 0,suffix = 0;
        int mul = 1;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            char cl = s.charAt(s.length()-i-1);
            prefix = (c-'a' + base * prefix) % mod;
            suffix = (suffix  + mul * cl) % mod;
        }
        return s;
    }
    public int findKNums(int[] nums,int p,int r,int k){
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
        if(i+2-p==k)
            return par;
        else if(i+2-p<k)
            return findKNums(nums,i+2,r,k-(i+2-p));
        else
            return findKNums(nums,p,i,k-1);
    }
    public Node flatten(Node head) {
        return null;
    }
    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        if(nums.length % k !=0)
            return false;
        for(int e:nums)
        {
            map.put(e,map.getOrDefault(e,0)+1);
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByKey());
        while(!map.isEmpty()){
            Map.Entry<Integer,Integer> first = list.get(0);
            int key = first.getKey();
            int value = first.getValue();
            if(value==0)
            {
                list.remove(0);
                continue;
            }
            try {
                for(int i=0;i<k;i++){
                    map.put(key+i,map.get(key+i)-1);
                    if(map.get(key+i)==0)
                        map.remove(key+i);
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
    public int numRabbits(int[] answers) {
        return 0;
    }
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] ret = new int [n];
        Stack<Integer> stack = new Stack<>();
        Map<Integer,Integer> map  =new HashMap<>();
        for(int i=n-1;i>=0;i--){
            int num = nums2[i];
            while(!stack.isEmpty() && num >=stack.peek() ){
                stack.pop();
            }
            map.put(num,stack.isEmpty()?-1:stack.peek());
            stack.push(num);
        }
        for(int i=0;i<n;i++){
            ret[i] = map.getOrDefault(nums1[i],-1);
        }
        return ret;
    }
}
