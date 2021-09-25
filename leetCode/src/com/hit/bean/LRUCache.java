package com.hit.bean;


import java.util.HashMap;

import java.util.Map;

class LRUCache {
    int size;
    int maxSize;
    Map<Integer,DynamicList> map = new HashMap<>();
    DynamicList head,tail;
    public static void main(String[] args){
        LRUCache cache = new LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        System.out.println(cache.get(1));
        cache.put(3,3);
        System.out.println(cache.get(2));
        cache.put(4,4);
        System.out.println(cache.get(3));
    }
    public LRUCache(int capacity) {
        maxSize = capacity;
        head = new DynamicList();
        tail = new DynamicList();
        tail.next = head;
        head.prev = tail;
    }
    public int get(int key) {
        DynamicList node = map.get(key);
        if(node==null){
            return -1;
        }
        node.remove();
        node.addToHead(head);
        return node.value;
    }

    public void put(int key, int value) {
        DynamicList node = map.get(key);
        if(node==null){
            node = new DynamicList(key,value);
            if(size==maxSize)
            {
                DynamicList temp = tail.next;
                map.remove(temp.key);
                tail.next = temp.next;
                temp.next.prev = tail;
                size--;
            }
            node.addToHead(head);
            map.put(key,node);
            size++;
        }else{
            node.value = value;
            node.remove();
            node.addToHead(head);
        }
    }
    static class DynamicList{
        public int key;
        public int value;
        DynamicList prev;
        DynamicList next;
        public DynamicList(int key,int value){
            this.key = key;
            this.value = value;
        }
        public DynamicList(){}
        public void remove(){
            prev.next = next;
            next.prev = prev;
        }
        public void addToHead(DynamicList head){
            this.prev = head.prev;
            head.prev.next = this;
            this.next = head;
            head.prev = this;
        }
        public void addToTail(DynamicList tail){
            this.next = tail.next;
            tail.next.prev = this;
            tail.next = this;
            this.prev = tail;
        }
    }
}