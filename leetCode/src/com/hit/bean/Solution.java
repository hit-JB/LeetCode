package com.hit.bean;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
public class Solution {
    Map<TreeNode,Integer> map = new HashMap<>();
    public static void main(String[] args){
        Solution sol = new Solution();
        int[][] real = new int[][]{{1,5},{2,5},{3,5},{3,4},{4,5}};
        real = new int[][]{};
        System.out.println(sol.minimumTime( 2,real,new int[]{3,5}));
    }
    public int countHighestScoreNodes(int[] parents) {
        int n = parents.length;
        Map<Integer,TreeNode> relate = new HashMap<>();
        for(int i=0;i<n;i++){
            relate.put(i,new TreeNode(i));
        }
        for(TreeNode e:relate.values()){
            int p = parents[e.val];
            if(p==-1){
                continue;
            }
            TreeNode pNode = relate.get(p);
            if(pNode.left==null){
                pNode.left  = e;
            }else{
                pNode .right = e;
            }
        }
        dfsCount(relate.get(0));
        long max = Long.MIN_VALUE;
        for(TreeNode e:map.keySet()){
            if(e==null)
                continue;
            long c1 = map.get(e.left);
            long c2 = map.get(e.right);
            long c3 = n - map.get(e);
            c1 = c1==0?1:c1;
            c2 = c2==0?1:c2;
            c3 = c3==0?1:c3;
            max = Math.max(c1 * c2 *c3, max);
        }
        int ret = 0;
        for(TreeNode e:map.keySet()){
            if(e==null)
                continue;
            int c1 = map.get(e.left);
            int c2 = map.get(e.right);
            int c3 = n - map.get(e);
            c1 = c1==0?1:c1;
            c2 = c2==0?1:c2;
            c3 = c3==0?1:c3;
            if(c1 * c2 *c3 ==max){
                ret ++;
            }
        }
        return ret;

    }
    public void dfsCount(TreeNode root){
        if(root==null){
            map.put(null,0);
            return;
        }
        if(map.get(root)!=null){
            return;
        }
        dfsCount(root.left);
        dfsCount(root.right);
        map.put(root , map.get(root.left) + map.get(root.right) +1);
    }
    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val){
            this.val = val;
        }
        public TreeNode(){

        }
    }
    public int minimumTime(int n, int[][] relations, int[] time) {
       List<List<Integer>> map = new ArrayList<>();
       List<List<Integer>> parent = new ArrayList<>();
       for(int i=0;i<n;i++){
           map.add(new ArrayList<>());
           parent.add(new ArrayList<>());
       }
       for(int[] e:relations){
           int id1 = e[0]-1,id2 = e[1] -1;
           map.get(id1).add(id2);
           parent.get(id2).add(id1);
       }
       map.add(new ArrayList<>());
       for(int i=0;i<n;i++){
           map.get(n).add(i);
       }
       int[] order = new int[n+1];
       sortDfs(map,order,new int[n+1],new int[1],n);
       List<int[]> list = new ArrayList<>();
       for(int i=0;i<n;i++){
           list.add(new int[]{i,order[i]});
       }
       list.sort((e1,e2)->-Integer.compare(e1[1],e2[1]));
       int[] dp = new int[n];
       for(int i=0;i<n;i++){
           int[] e  = list.get(i);
           int min = 0;
           for(int p:parent.get(e[0])){
               min = Math.max(dp[p],min);
           }
           dp[e[0]] = min + time[e[0]];
       }
       int max = Integer.MIN_VALUE;
       for(int e:dp){
           max = Math.max(max,e);
       }
       return max;
    }
    public void  sortDfs(List<List<Integer>> map,int[] order,int[] color,
                         int[] time,int node){
        time[0]++;
        for(int adj:map.get(node)){
            if(color[adj]==0){
                sortDfs(map,order,color,time,adj);
            }
        }
        color[node] = 1;
        order[node] = time[0];
        time[0]++;
    }
    public int kthGrammar(int n, int k) {
        if(n==1)
            return 1;
        if(k<= (1<<(n-2))){
            return kthGrammar(n-1,k);
        }
        return kthGrammar(n, (k+(1<<n-2)) & ((1<<(n-2)) -1));
    }
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int[] left = new int[s.length()];
        Arrays.fill(left,-1);
        for(int i=0;i<s.length();i++){
            char c ;
            if((c = s.charAt(i))=='('){
                stack.push(i);
            }else if(!stack.isEmpty()){
                left[i] = stack.pop();
            }
        }
        int max = 0;
        for(int i=0;i<left.length;i++){
            if(left[i]==-1)
                continue;
            int l = left[i]-1;
            if(l>0 && left[l]!=-1){
                left[i] = left[l];
            }
            max = Math.max(max,i-left[i]+1);
        }
        return max;
    }
}
