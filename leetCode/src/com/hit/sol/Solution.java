package com.hit.sol;

class Solution {
    long[] sum;
    int[] w;
    public static void main(String[] args){
        Solution sol = new Solution(new int[]{1,0});
        for(int i =0;i<20;i++){
            System.out.println(sol.pickIndex());
        }

    }
    public Solution(int[] w) {
        sum = new long[w.length];
        this.w = w;
        for(int i=0;i<w.length;i++){
            sum[i] = w[i] + (i-1>=0?sum[i-1]:0);
        }
    }

    public int pickIndex() {
        double label = (Math.random() * sum[sum.length-1]);//生成(0,1)之间的随机数
        int left = -1,right = sum.length-1,mid = (left + right) / 2;
        while (right-left>1){
            if(sum[mid]>=label){
                right = mid;
            }else{
                left = mid;
            }
            mid = (left + right)  /2;
        }
        return right;
    }
}