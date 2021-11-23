package com.hit.bean;

import java.util.concurrent.*;
import java.lang.*;
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,10,
                TimeUnit.MICROSECONDS,new LinkedBlockingDeque<>(10),new ThreadPoolExecutor.AbortPolicy());
        try {
            for(int i=0;i<40;i++){
                int finalI = i;
                threadPoolExecutor.execute(new Runnable() {
                    final int k = finalI;
                    @Override
                    public void run() {
                        System.out.println("这是第几条线程："+ finalI +"线程名字"+Thread.currentThread().getName());
                        while (true);
                    }
                });
            }
        }catch (Exception e){
            System.out.println("等待队已满，线程池溢出");
        }

    }
}
