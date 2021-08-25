package com.hit.sol;


import java.util.*;

public class SolIII {
    public static void main(String[] args){
        SolIII sol = new SolIII();
        System.out.println(sol.hasAllCodes("00000000001011100",
                3

        ));
//        List<String> input = new ArrayList<>();
//        Scanner in = new Scanner(System.in);
//        int num =Integer.parseInt(in.nextLine());
//        String regex ="^[a-zA-Z]([0-9a-zA-Z]*)[0-9]+([0-9a-zA-Z]*)";
//        for(int i=0;i<num;i++){
//            input.add(in.nextLine());
//        }
//        for(String e:input){
//            System.out.println(e.matches(regex)?"Accept":"Wrong");
//        }
//        String s = (new StringBuilder()).append("ja").append("va").toString();
//        System.out.println(s.intern() == (s));
    }
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> ret = new ArrayList<>();
        dfsPAth(graph,0,temp,ret);
        return ret;
    }
    public void dfsPAth(int[][] graph,int index,List<Integer> temp,List<List<Integer>> ret){
        if(index==graph.length-1)
        {
            List<Integer> t = new ArrayList<>();
            t.add(0);
            t.addAll(temp);
            ret.add(t);
            return;
        }

        for(int i:graph[index]){
            temp.add(index);
            dfsPAth(graph,i,temp,ret);
            temp.remove(temp.size()-1);
        }

    }
    public int findLength(int[] nums1, int[] nums2) {
        int max = 0;
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                dp[i][j] = nums1[i-1]==nums2[j-1]?1+dp[i-1][j-1]:0;
                max = Math.max(dp[i][j],max);
            }
        }
        return max;
    }
    public boolean hasAllCodes(String s, int k) {
        int length = 1<<k;
        if(s.length()-k+1<length)
            return false;
        int num = 0;
        for(int i=0;i<k;i++){
            num = (num<<1) + s.charAt(i)-'0';
        }
        Set<Integer> set = new HashSet<>(1<<k);
        set.add(num);
        for(int i=0;i<s.length()-k;i++){
            num = (num - (num & 1<<k-1)) * 2 + s.charAt(i+k)-'0';
            set.add(num);
        }
        return set.size() == length;
    }
    public int respace(String[] dictionary, String sentence) {

    }
}
