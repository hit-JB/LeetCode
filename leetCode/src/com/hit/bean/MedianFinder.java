package com.hit.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class MedianFinder {

    /** initialize your data structure here. */
    PriorityQueue<Integer> maxQueue = new PriorityQueue<>((a,b)->(a-b));
    PriorityQueue<Integer> minQueue = new PriorityQueue<>((a,b)->(b-a));
    private double mid;
    public MedianFinder() {
        List<Integer> list = new ArrayList<>();
        list.sort(Integer::compare);

    }

    public void addNum(int num) {
        if(maxQueue.size()==0 && minQueue.size()==0) {
            minQueue.add(num);
            mid = num;
        }else if(num>mid){
            maxQueue.add(num);
        }else {
            minQueue.add(num);
        }
        if(maxQueue.size()-minQueue.size() > 1){
            while(maxQueue.size()-minQueue.size()>1)
                minQueue.add(maxQueue.poll());
        }
        if(minQueue.size()-maxQueue.size()>1){
            while (minQueue.size()-maxQueue.size()>1)
                maxQueue.add(minQueue.poll());
        }
        mid = maxQueue.size()==minQueue.size()?(maxQueue.peek()+minQueue.peek()) / 2.:
                maxQueue.size()>minQueue.size()?maxQueue.peek():minQueue.peek();
    }

    public double findMedian() {
        return mid;
    }
}