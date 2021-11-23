package com.hit.bean;

public class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                ", prev=" + prev +
                ", next=" + next +
                ", child=" + child +
                '}';
    }
};