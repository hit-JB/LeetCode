package com.hit.bean;

import java.lang.reflect.Array;
import java.util.*;

public class DataStructure {
    public static void main(String[] args) {
        String[] strings = new String[]{"1","2","3"};
        Object[] objects = new Object[]{"1","2","3"};
        Object[] s = (Object[]) Array.newInstance(String.class,10);
        Map<Integer,Integer> set = new Hashtable<>();
    }
}
