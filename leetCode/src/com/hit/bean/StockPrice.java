package com.hit.bean;

import java.util.*;

class StockPrice {
    Map<Integer,Integer> prices = new HashMap<>();
    int time = 0;
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    Queue<Map.Entry<Integer,Integer>> minQueue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
    Queue<Map.Entry<Integer,Integer>> maxQueue = new PriorityQueue<>((e1,e2)->-Integer.compare(e1.getValue(),e2.getValue()));
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,2);
        map.put(2,3);
        map.put(3,4);
        Queue<Map.Entry<Integer,Integer>> minQueue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        Queue<Map.Entry<Integer,Integer>> maxQueue = new PriorityQueue<>((e1,e2)->-Integer.compare(e1.getValue(),e2.getValue()));
        minQueue.add(new AbstractMap.SimpleEntry<>(1, 1));
        AbstractMap.SimpleEntry<Integer,Integer> entry = new AbstractMap.SimpleEntry<>(1,1);
        AbstractMap.SimpleEntry<Integer,Integer> entry1 = new AbstractMap.SimpleEntry<>(1,1);
        System.out.println(entry.equals(entry1));
        while(!minQueue.isEmpty()){
            System.out.println(minQueue.remove());
        }
    }
    public StockPrice() {

    }

    public void update(int timestamp, int price) {
        int oldValue;
        if(prices.get(timestamp)!=null){
            oldValue = prices.get(timestamp);
            AbstractMap.SimpleEntry<Integer,Integer> oldEntry = new AbstractMap.SimpleEntry<>(timestamp,oldValue);
            minQueue.remove(oldEntry);
            maxQueue.remove(oldEntry);
        }else{
            oldValue = price;
        }

        AbstractMap.SimpleEntry<Integer,Integer> newEntry = new AbstractMap.SimpleEntry<>(timestamp,price);

        prices.put(timestamp,price);
       time = Math.max(timestamp,time);

    }

    public int current() {
        return prices.get(time);
    }

    public int maximum() {
        return max;
    }

    public int minimum() {
        return min;
    }
}