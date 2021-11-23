package com.hit.bean;

import javax.swing.plaf.TableHeaderUI;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ViolateTest {
    public static volatile int race = 0;
    public synchronized static void increase(){//同步块，原子操作
        race++;
    }
    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is a  thread");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while(Thread.activeCount()>2)
            Thread.yield();

        ReentrantLock reentrantLock = new ReentrantLock();
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
    static class  CustomStack {
        private final Stack<Integer> stack = new Stack<>();
        private final int maxSize;
        public CustomStack(int maxSize) {
            this.maxSize = maxSize;
        }

        public void push(int x) {
            if(stack.size()<=this.maxSize){
                this.stack.push(x);
            }
        }

        public int pop() {
            return stack.isEmpty()?-1:stack.pop();
        }

        public void increment(int k, int val) {
            for(int i=0;i<Math.min(k,stack.size());i++){
                stack.set(i,stack.get(i)+val);
            }
        }
    }
    static class MyCalendarTwo {
        private TreeNode root;
        private final TreeNode nil = new TreeNode();
        public MyCalendarTwo() {
            root = null;
        }

        public boolean book(int start, int end) {
            if(root==null){
                root = new TreeNode();
                root.value[0] = start;
                root.value[1] = end;
                root.max = end;
                root.left = nil;
                root.right = nil;
                root.parent = null;
                return true;
            }
            int count = 0;
            int[] d = new int[]{start,end};
            TreeNode node = root;
            TreeNode prev;
            while(node!=nil){
                prev = node;
                if(isInsert(node.value,d)){
                    count++;
                }
                if(node.left!=nil && node.left.max > start){
                    node = node.left;
                }else{
                    node = node.right;
                }
            }
            if(count>=2){
                return false;
            }
            insertNode(d);
            return true;
        }
        public void insertNode(int[] val){
            TreeNode node = root;
            TreeNode prev = null;
            while(node!=nil){
                prev = node;
                if(val[0]<=node.value[0]){
                    node = node.left;
                }else{
                    node = node.right;
                }
            }
            TreeNode add = new TreeNode();
            add.value = val;
            add.left = nil;
            add.right = nil;
            add.parent = prev;
            add.max = val[1];
            if(val[0]<=prev.value[0]){
                prev.left = add;
            }else{
                prev.right = add;
            }
            node = prev;
            while(node!=root){
                int l = node.left==null?Integer.MIN_VALUE:node.left.max;
                int r = node.right==null?Integer.MIN_VALUE:node.right.max;
                node.max=Math.max(node.max,Math.max(l,r));
                node = node.parent;
            }
        }
        public boolean isInsert(int[] val1,int[] val2){
            return val2[0]>=val1[0] && val2[0]<val1[1] ||
                    val1[0]>=val2[0] && val1[0]<val2[1];
        }
        static class TreeNode{
            public int[] value;
            public int max;
            public TreeNode left;
            public TreeNode right;
            public TreeNode parent;
            public TreeNode(){
            }
        }
    }

}
