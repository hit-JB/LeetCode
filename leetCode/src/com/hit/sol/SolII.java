package com.hit.sol;

public class SolII {
    public static void main(String[] args){
        SolII sol = new SolII();

    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0,column = matrix[0].length-1;
        while(row<matrix.length && column >=0){
            if(matrix[row][column]<target)
            {
                row++;
            }else if(matrix[row][column]==target){
                return true;
            }else{
                column--;
            }
        }
        return false;
    }
    public boolean canPartition (int[] nums) {
        int all=0;
        int[] pre = new int[nums.length];
        
        for(int e:nums)all+=e;
        if(all % 2==1)
            return false;
        boolean[] can = new boolean[]{false};
        dfsCanPartition(0,nums,0,all / 2,can);
        return can[0];
    }
    public void dfsCanPartition(int sum,int[] nums,int index,int all,boolean[] can){
        if(sum==all)
            can[0] = true;
        if(can[0])
            return;
        for(int i=index;i<nums.length;i++){
            if(sum+nums[i] <= all)
                dfsCanPartition(sum+nums[i],nums,i+1,all,can);
        }
    }
}
