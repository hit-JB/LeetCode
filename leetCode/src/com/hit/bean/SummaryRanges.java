package com.hit.bean;


import java.util.ArrayList;
import java.util.List;

class SummaryRanges {

    int[] range = new int[10000+1];
    public SummaryRanges() {

    }

    public void addNum(int val) {
        range[val] = 1;
    }

    public int[][] getIntervals() {
        List<int[]> list = new ArrayList<>();
        int i=0;
        while(i<range.length){
            int left = i;
            while(range[i]==1){
                i++;
            }
            if(left!=i)
                list.add(new int[]{left,i-1});
            i++;
        }
        return list.toArray(new int[][]{});
    }
}