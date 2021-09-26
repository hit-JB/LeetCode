package com.hit.sol;


import com.hit.bean.Node;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

public class Sort {
    public static void main(String[] args){
        Sort sort = new Sort();
        int[] nums = new int[]{4,1,3,2,16,9,10,14,8,7};
        System.out.println(sort.makesquare(new int[]{1,1,2,2,2}));
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
        Map<Integer,Integer> map = new HashMap<>();
        for(int e:answers)
            map.put(e,map.getOrDefault(e,0)+1);
        int sum = 0;
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            int key = e.getKey();
            int value = e.getValue();
            if(key>=value){
                sum = sum+key+1;
            }else{
                int group = value / (key+1);
                int remain = value % (key+1);
                sum += group * (key+1);
                if(remain!=0)
                    sum += (key+1);
            }
        }
        return sum;
    }
    public boolean makesquare(int[] matchsticks) {
        long sum = 0;
        for(int e:matchsticks){
            sum +=e ;
        }
        if(sum % 4!=0)
            return false;
        long a = sum / 4;
        Map<Integer,Boolean> map = new HashMap<>();
        Map<Integer,Boolean> can = new HashMap<>(1<<matchsticks.length);
        preManager(map,matchsticks,a,0,0);
        manager(can,map,(1<<matchsticks.length)-1);
        return can.get((1<<matchsticks.length)-1);
    }
    public void manager(Map<Integer,Boolean> can, Map<Integer,Boolean> map,int code){
        if(can.containsKey(code) || map.containsKey(code)) {
            if(map.containsKey(code))
                can.put(code,true);
            return;
        }
        boolean is;
        for(Map.Entry<Integer,Boolean> e:map.entrySet()){
            int num = e.getKey();
            if((num | code) == code){
                manager(can,map,code ^ num);
                is = can.get(code ^ num);
                if(is) {
                    can.put(code,true);
                    return;
                }
            }
        }
        can.put(code,false);
    }
    public void preManager(Map<Integer,Boolean> map,int[] matchsticks,
                           long a,int code,int index){
        if(a==0)
            map.put(code,true);
        for(int i=index;i<matchsticks.length;i++){
            if(a - matchsticks[i]>=0){
                preManager(map,matchsticks,a-matchsticks[i],code | (1<<i),i+1);
            }
        }
    }
}
