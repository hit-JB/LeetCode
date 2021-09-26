package com.hit.bean;

import java.util.ArrayDeque;
import java.util.Queue;

class MaxQueue {
    private Queue<Integer> queue;
    private Queue<Integer> max;
    public MaxQueue() {
        queue = new ArrayDeque<>();
        max = new ArrayDeque<>();
    }

    public int max_value() {
        if(max.isEmpty())
            return -1;
        return max.peek();
    }

    public void push_back(int value) {
        queue.add(value);
    }

    public int pop_front() {
        if(queue.isEmpty())
            return -1;
        return queue.remove();
    }
}