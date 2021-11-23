package com.hit.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class PeekingIterator implements Iterator<Integer> {

    List<Integer> list;
    Iterator<Integer> it;

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer next = iterator.next();
            System.out.println(next);
        }
    }
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        list = (ArrayList<Integer>) iterator;
        it = list.iterator();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return 0;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
