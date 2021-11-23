package com.hit.bean;

import java.util.Objects;

public class Single {
    private static Single single;

    public static Single getSingle() {
        if(single==null){
            synchronized (Single.class){
                if(single==null){
                    single = new Single();
                }
            }
        }
        return single;
    }
}
