package com.hit.bean;

import javax.swing.*;
import java.util.*;

class Solution {

    private long size;
    private ListNode head;

    public static void main(String[] args) {
        Scanner input =  new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        System.out.println(n+" "+m);
    }
    public Solution(ListNode head) {
        size = 0;
        this.head = head;
        while(head!=null){
            size++;
            head = head.next;
        }
    }
    public Solution(){

    }
    public int getRandom() {
        long index = (long) (Math.random() * size);
        int i=0;
        ListNode node = this.head;
        while(i<index){
            node = node.next;
            i++;
        }
        return node.val;
    }
    public int minDays(int[] bloomDay, int m, int k) {
        if(bloomDay.length < m * k)
            return -1;
        int N = bloomDay.length;
        List<int[]> list = new ArrayList<>();
        for(int i=0;i<N;i++){
            list.add(new int[]{bloomDay[i],i+1});
        }
        list.sort(Comparator.comparingInt(e->e[0]));
        int left = 0,right = list.get(list.size()-1)[0],mid = left + (right-left) / 2;
        while(right -left>1){
            if(canFinish(list,mid,m,k)){
                right = mid;
            }else{
                left = mid;
            }
            mid = left + (right -left) / 2;
        }
        return right;
    }
    public boolean canFinish(List<int[]> list,int day,int m,int k){
        int[] used = new int[list.size()];
        int[] e;
        int i=0;
        int N = list.size();
        while(i<N && (e = list.get(i++))[0]<=day){
            used[e[1]] = 1;
        }
        int temp = k;
        for(i=0;i<N;i++){
            if(used[i]==1)
                temp--;
            if(used[i]==0)
                temp = k;
            if(temp==0){
                m--;
                temp = k;
            }
            if(m==0)
                return true;
        }
        return false;
    }
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        List<int[]> cp = new ArrayList<>();
        int N = capital.length;
        for(int i=0;i<profits.length;i++){
            cp.add(new int[]{capital[i],profits[i]});
        }
        cp.sort(Comparator.comparingInt(e -> e[0]));
        int i=0;
        int j=0;
        Queue<Integer> queue = new PriorityQueue<>((e1,e2)->-Integer.compare(e1,e2));
        while(i<k){
            int[] e;
            while(j<cp.size() && (e = cp.get(j))[0]<=w){
                j++;
                queue.add(e[1]);
            }
            if(queue.isEmpty())
                return w;
            w += queue.poll();
            i++;
        }
        return w;
    }

}