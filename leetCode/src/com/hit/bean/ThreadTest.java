package com.hit.bean;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    public static void main(String[] args){
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int i=0;
        while(true){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"is Running");
                }
            });
            i++;
        }
    }

}
