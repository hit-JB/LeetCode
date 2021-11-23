package com.hit.bean;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    public static void main(String[] args){
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int i=0;
        while(true){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"is Running");
                }
            });
            i++;
        }
    }
    public int[] dailyTemperatures(int[] temperatures) {
        int N= temperatures.length;
        int[] ret = new int[N];
        for(int i=N-1;i>=0;i--){
            int j = i+1;
            ret[i] = 0;
            while(j<N){
                if(temperatures[j]>temperatures[i])
                {
                    ret[i] = j - i;
                    break;
                }
                if(ret[j]==0)
                    break;
                j = j+ret[j];
            }
        }
        return ret;
    }
    public int findLatestStep(int[] arr, int m) {
        if(m==arr.length)
            return arr.length;
        TreeMap<Integer,Integer> map = new TreeMap<>();
        int size = arr.length;
        map.put(0,size-1);
        for(int i=size-1;i>=0;i--){
            int index = arr[i]-1;
            int key  =  map.floorKey(index);
            int right = map.get(key);
            if(key==index){
                map.remove(key);
                if(key+1<=right)
                    map.put(key+1,right);
                if(right-key==m)
                    return i;
            } else if(right==index){
                map.put(key,right-1);
                if(right-key==m)
                    return i;
            }else{
                map.put(key,index-1);
                map.put(index+1,right);
                if(index-key==m || right-index==m)
                    return i;
            }
        }
        return -1;
    }
}
