package com.hit.bean;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
//        final ConditionTest conditionTest = new ConditionTest();
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int r = conditionTest.getNum();
//                System.out.println("返回结果为"+r);
//            }
//        });
//        Thread thread2  = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                conditionTest.setNum(6666);
//                System.out.println("线程["+Thread.currentThread()+"]已经设置完成");
//            }
//        });
//        thread1.start();
//        Thread.sleep(4000);
//        thread2.start();
        final ArrayTest arrayTest = new ArrayTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayTest.add("12321");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                arrayTest.add("123123");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayTest.get(0);
            }
        });
        t1.start();
        t2.start();
    }
    public static class ConditionTest{
        private int num = 0;
        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        public void inc(){
            num++;
        }
        public Integer getNum()  {
            lock.lock();
            System.out.println("线程["+Thread.currentThread()+"]已经拿到读锁");
            try {
                System.out.println("线程["+Thread.currentThread()+"]等待await");
                condition.await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
                System.out.println("线程["+Thread.currentThread()+"]已经释放读锁");
            }
            return num;
        }

        public void setNum(Integer num) {
            try {
                lock.lock();
                System.out.println("线程["+Thread.currentThread()+"]已经拿到写锁");
                this.num = num;
                System.out.println("线程["+Thread.currentThread()+"]通过信号通知其它线程");
                condition.signal();
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("线程["+Thread.currentThread()+"]已经释放写锁");
                lock.unlock();
            }

        }

        public ReentrantLock getLock() {
            return lock;
        }

        public void setLock(ReentrantLock lock) {
            this.lock = lock;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }
    }
    public static  class ArrayTest{
        private List<String> array = new ArrayList<>();
        Lock lock  =new ReentrantLock();
        public void add(String s){
            try {
                lock.lock();
                System.out.println("线程["+Thread.currentThread()+"]获取到写锁");
                array.add(s);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public String get(int index){
            try {
                //lock.lock();
                System.out.println("线程["+Thread.currentThread()+"]获取到读锁");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //lock.unlock();
            }
            return array.get(index);
        }
    }

}
