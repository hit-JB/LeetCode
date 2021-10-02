package com.hit.bean;

import java.util.*;

public class ViolateTest {
    public static volatile int race = 0;
    public synchronized static void increase(){//同步块，原子操作
        race++;
    }
    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
//        Thread[] threads = new Thread[THREADS_COUNT];
//        for(int i=0;i<THREADS_COUNT;i++){
//            threads[i] = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("Thread Name:"+Thread.currentThread().getName());
//                    for(int i=0;i<10000;i++){
//                        increase();
//                    }
//                    System.out.println("Thread finally:"+Thread.currentThread().getName());
//                }
//            });
//            threads[i].start();
//        }
//        System.out.println("Main thread:"+Thread.currentThread().getName());
//        while (Thread.activeCount()>2) {
//            Thread.yield();
//        }
//        System.out.println("Finally count:"+race);
        System.out.println("{".compareTo("z"));
        System.out.println((char) ('z'+1));
    }
    public int findCenter(int[][] edges) {
        int N = edges.length;
        int[] loc = new int[N+1];
        for(int[] e:edges){
            loc[e[0]-1]++;
            loc[e[1]-1]++;
            if(loc[e[0]-1]==2)
                return e[0];
            if(loc[e[1]-1]==2)
                return e[1];
        }
        return -1;
    }
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        List<Set<String>> sets = new ArrayList<>();
        for(String e:synonyms){
            String[] s = e.split(",");
            s[0] = s[0].substring(1);
            s[1] = s[1].substring(0,s[1].length()-1);
            boolean is = false;
            for(Set<String> set:sets){
                if(set.contains(s[0]) || set.contains(s[1])){
                    set.add(s[0]);
                    set.add(s[1]);
                    is = true;
                    break;
                }
            }
            if(!is){
                Set<String> set = new HashSet<>();
                set.add(s[0]);
                set.add(s[1]);
                sets.add(set);
            }
        }
        String[] headers = new String[sets.size()];
        for(int i=0;i<sets.size();i++) {
            Set<String> e = sets.get(i);
                String header = "{";
                for (String s : e) {
                    if (s.compareTo(header) < 0) {
                        header = s;
                    }
                }
                headers[i] = header;
        }
        int[] nums = new int[headers.length];
        for(String e:names){
            String[] s = e.split("\\(");
            int num = Integer.parseInt(s[1].substring(0,s[1].length()-1));
            String name = s[0];
            for(int i=0;i<sets.size();i++){
                if(sets.get(i).contains(name)){
                    nums[i] += num;
                }
            }
        }
        for(int i=0;i<headers.length;i++){
            headers[i] = headers[i]+"("+nums[i]+")";
        }
        return headers;
    }
    public List<Integer> largestValues(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        List<Integer> list = new ArrayList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            for(;size>0;size--){
                TreeNode node = queue.poll();
                max = Math.max(node.val,max);
                if(node.left!=null){
                    queue.add(node.left);
                }
                if(node.right!=null)
                {
                    queue.add(node.right);
                }
            }
            list.add(max);
        }
        return list;
    }
}
