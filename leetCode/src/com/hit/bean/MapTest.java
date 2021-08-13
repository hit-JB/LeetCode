package com.hit.bean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapTest{

    public static void main(String[] args){
        Map<Integer,Integer> map  = new LinkedHashMap<>(10);
        System.out.println(MapTest.tableSizeFor(123));
        System.out.println(Integer.numberOfLeadingZeros(12));

    }
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }
}
