package com.hit.sol;

import java.util.*;

public class SolII {
    public static void main(String[] args){
        SolII sol = new SolII();
        long a = (long) Math.pow(2,60);
        int[][] map = new int[][]{{0,1,0},{1,0,0},{0,0,1}};
        int[][] cells = {{1,2},{2,1},{3,3},{2,2},{1,1},{1,3},{2,3},{3,2},{3,1}};
        System.out.println(sol.latestDayToCross(3,3,cells));
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
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn){
        int mod = (int) (Math.pow(10,9)+7);
        int[][][] dp = new int[maxMove+1][m][n];
        int[][] direct = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
//        Arrays.fill(dp[0][0], 1);
//        Arrays.fill(dp[0][m-1],1);
//        for(int i=0;i<n;i++){
//            dp[0][i][0] = 1;dp[0][i][n-1] = 1;
//        }
//        dp[]
        for(int i=1;i<=maxMove;i++){
            for(int row = 0;row<m;row++){
                for(int column = 0;column<n;column++){
                    for (int[] ints : direct) {
                        int next_row = row + ints[0],
                                next_column = column + ints[1];
                        if (next_column < 0 || next_column >= n || next_row < 0 ||
                                next_row >= m)
                            dp[i][row][column] += 1;
                        else
                            dp[i][row][column] += dp[i - 1][next_row][next_column];
                        dp[i][row][column] = dp[i][row][column] % mod;
                    }
                }
            }
        }
        return dp[maxMove-1][startRow][startColumn];
    }
    public int numOfStrings(String[] patterns, String word) {
        Map<String,Integer> map = new HashMap<>();
        for(String e:patterns)
            map.put(e,map.getOrDefault(e,0)+1);
        int count = 0;
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            if(entry.getKey().length() > word.length())
                continue;
            if(word.contains(entry.getKey()))
                count+=entry.getValue();
        }
        return count;
    }
    public int[] rearrangeArray(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int e:nums)list.add(e);
        list.sort(Integer::compare);
        int[] ret = new int[nums.length];
        ret[0] = list.get(0);
        for(int i=1;i<list.size()-1;i++){
            if(i%2==1)
                ret[i] = list.get(i+1);
            else
                ret[i] = list.get(i-1);
        }
        assert list.size()>=3;
        if((list.size()-1) % 2 == 0)
            ret[list.size()-1] = list.get(list.size()-2);
        else
            ret[list.size()-1] = list.get(list.size()-1);
        return ret;
    }
    public int minNonZeroProduct(int p) {
        if(p==1)
            return 1;
        if(p==2)
            return 6;
        long last = (1L << p) -1;
        long mod = (long) (Math.pow(10,9)+7);
        long num = last-1;
        long size= num / 2;

        return (int) ((bMod(mod,size,num) * (last % mod)) % mod);
    }
    public long bMod(long mod,long size,long num){
       if(size==1)
           return num % mod;
       long div  = size / 2,another = size - div;
       long temp = bMod(mod,div,num);
        long ret = ((temp % mod) * (temp % mod)) % mod;
        if(div << 2==size)
       return ret;
       return (ret * (num % mod)) % mod;
    }
    public int latestDayToCross(int row, int col, int[][] cells) {

        int left = 0,right = cells.length,mid = (left + right) / 2;
        while(right-left>1){
            int[][] map = new int[row][col];
            for(int i=0;i<=mid;i++){
                map[cells[i][0]-1][cells[i][1]-1] = 1;
            }
            if(check(map)){
                left = mid;
            }else{
                right =mid;
            }
            mid = (left+right) / 2;
        }
        return left+1;
    }
    public boolean check(int[][] map){
        boolean[][] visited = new boolean[map.length][map[0].length];
        int[][] directs = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        for(int j=0;j<map[0].length;j++){
            if(map[0][j]==0 && !visited[0][j]){
                visited[0][j] = true;
                Queue<int[]> queue = new ArrayDeque<>();
                queue.add(new int[]{0,j});
                while(!queue.isEmpty()){
                    int size = queue.size();
                    for(int i=0;i<size;i++){
                        int[] top = queue.poll();
                        for(int[] direct:directs){
                            int nextRow = top[0] + direct[0];
                            int nextColumn = top[1] + direct[1];
                            if(nextRow>=0 && nextRow < map.length && nextColumn >=0 &&
                            nextColumn<map[0].length && !visited[nextRow][nextColumn] &&
                            map[nextRow][nextColumn] ==0){
                                if(nextRow==map.length-1)
                                    return true;
                                queue.add(new int[]{nextRow,nextColumn});
                                visited[nextRow][nextColumn] = true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
