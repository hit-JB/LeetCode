package com.hit.sol;

import java.sql.Array;
import java.util.*;

public class SolVI {
    public static void main(String[] args) {
        SolVI sol = new SolVI();
        System.out.println(sol.decodeString("100[leetcode]"));
    }
    public int[][] construct2DArray(int[] original, int m, int n) {
        int[][] ret = new int[0][0];
        if(original.length != m * n){
            return  ret;
        }
        ret = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                ret[i][j] = original[i * m+j];
            }
        }
        return ret;
    }
    public int numOfPairs(String[] nums, String target) {
        int sum = 0;
        int N = nums.length;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(j==i)
                    continue;
                if((nums[i]+nums[j]).equals(target))
                    sum++;
            }
        }
        return sum;
    }
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int N = answerKey.length();
        int[] t = new int[N];
        int[] f = new int[N];
        for(int i=0;i<answerKey.length();i++){
            char c = answerKey.charAt(i);
            if(c=='T'){
                t[i] = i-1>=0?t[i-1]+1:1;
                f[i] = i-1>=0?f[i-1]:0;
            }else{
                t[i] = i-1>=0?t[i-1]:0;
                f[i] = i-1>=0?f[i-1]+1:1;
            }
        }
        int left = 1,right = N+1,mid = (left + right) / 2;
        while(right-left>1){
            if(canConsecutive(t,f,k,mid)){
                left = mid;
            }else{
                right = mid;
            }
            mid = (left + right)>>1;
        }
        return left;
    }
    public boolean canConsecutive(int[] t,int[] f,int k,int l){
        int N = t.length;
        for(int i=0;i<=N-l;i++){
            int j = i+l-1;
                int d1 = t[j] - (i-1>=0?t[i-1]:0);
                int d2 = f[j] - (i-1>=0?f[i-1]:0);
                if(d1<=k || d2<=k){
                    return true;
            }
        }
        return false;
    }
    public int waysToPartition(int[] nums, int k) {
        int[] d = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            d[i] = k - nums[i];
        }
        int[] preSum = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            preSum[i] = nums[i] + (i-1>=0?preSum[i-1]:0);
        }
        int ret = 0;
        int N = nums.length;
        for(int i=1;i<N;i++){
            if(preSum[i-1] == preSum[N-1] - preSum[i-1]){
                ret ++;
            }
        }
        for(int j=0;j<N;j++){
            int max = 0;
            for(int i=1;i<N;i++){
                int left = j<i?preSum[i-1] + d[j]:preSum[i-1];
                int delta = preSum[N - 1] - preSum[i - 1];
                int right = j>=i? delta +d[j]: delta;
                if(left==right)
                    max++;
            }
            ret = Math.max(ret,max);
        }
        return ret;
    }
    public int minimumMoves(String s) {
        int i=0;
        int count = 0;
        while(i<s.length()){
            char c = s.charAt(i);
            if(c=='O')
                i++;
            {
                i+=3;
                count++;
            }
        }
        return count;
    }
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int sum = 0;
        for(int e:rolls)
            sum +=e;
        int m=  rolls.length;
        int sum_ = (m+n) * mean - sum;
        int every = sum_ / n;
        int remain = sum_ % n;
        int[] ret = new int[n];
        if(every>=6 && remain!=0 || every>6||every<=0){
            ret = new int[0];
            return ret;
        }
        for(int i=0;i<ret.length;i++){
            ret[i] = i<remain?every+1:every;
        }
        return ret;
    }
    public boolean stoneGameIX(int[] stones) {
        //三维dp，可以进一步优化
        int remain = 0;
        int[] count = new int[3];
        for (int i = 0; i < stones.length; i++) {
            count[(stones[i] % 3)]++;
            remain = (remain+stones[i]) % 3;
        }
        boolean[][][] dp = new boolean[count[0]+1][count[1]+1][count[2]+1];
        boolean[][][] dp_ = new boolean[count[0]+1][count[1]+1][count[2]+1];
        for(int i=0;i<=count[0];i++){
            for(int j=0;j<=count[1];j++){
                for(int k=0;k<=count[2];k++){
                    int r0 = count[0] - i,r1 = count[1] - j,r2 = count[2] - k;
                    int all = r1 + r2 *2;
                    if(i==0 && j==0 && k==0){
                        if(all % 3==0) {
                            dp[i][j][k] = true;
                        }else{
                            dp[i][j][k] = false;
                        }
                        dp_[i][j][k] = true;
                    }
                    else if(all % 3==0 && (i!=count[0] || j!=count[1] || k!=count[2])){
                        dp[i][j][k] = true;
                        dp_[i][j][k] = true;
                    }else if(all % 3==1){
                        boolean d0 = false,d1 = false;
                        boolean d0_= false,d1_ = false;
                        if(i-1>=0){
                            d0 = !dp_[i-1][j][k];
                            d0_ = !dp[i-1][j][k];
                        }
                        if(j-1>=0)
                        {
                            d1 = !dp_[i][j-1][k];
                            d1_ = !dp[i][j-1][k];
                        }
                        dp[i][j][k] = d0||d1;
                        dp_[i][j][k] = d0_||d1_;
                    }else if(all % 3==2){
                        boolean d0 = false,d2 = false;
                        boolean d0_= false,d2_ = false;
                        if(i-1>=0){
                            d0 = !dp_[i-1][j][k];
                            d0_ = !dp[i-1][j][k];
                        }
                        if(k-1>=0)
                        {
                            d2 = !dp_[i][j][k-1];
                            d2_ = !dp[i][j][k-1];
                        }
                        dp[i][j][k] = d0||d2;
                        dp_[i][j][k] = d0_||d2_;
                    }else{
                        boolean d1 = false,d2 = false;
                        boolean d1_ = false,d2_ = false;
                        if(j-1>=0)
                        {
                            d1 = !dp_[i][j-1][k];
                            d1_ = !dp[i][j-1][k];
                        }
                        if(k-1>=0){
                            d2 = !dp_[i][j][k-1];
                            d2_= !dp[i][j][k-1];
                        }
                        dp[i][j][k] = d1 || d2;
                        dp_[i][j][k] = d1_ || d2_;
                    }
                }
            }
        }
        return dp[count[0]][count[1]][count[2]];
    }
    public int numIslands(char[][] grid) {
        int[] count = new int[1];
        int[][] map = new int[grid.length][grid[0].length];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]==0 && grid[i][j] == '1'){
                    count[0]++;
                    dfsNumIslands(grid,map,i,j);
                }
            }
        }
        return count[0];
    }
    public void dfsNumIslands(char[][] grid,int[][] map,int startI,int startJ){
        map[startI][startJ] = 1;
        int[][] direct = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        for(int[] e:direct){
            int nextI = startI + e[0];
            int nextJ = startJ + e[1];
            if(nextI>=0 && nextI<map.length && nextJ>=0 && nextJ < map[0].length &&
            map[nextI][nextJ]==0 && grid[nextI][nextJ]=='1'){
                dfsNumIslands(grid,map,nextI,nextJ);
            }
        }
    }
    public int leastInterval(char[] tasks, int n) {
        Map<Character,Integer> map = new HashMap<>();
        for(char c:tasks){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        List<Map.Entry<Character,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((e1,e2)->-Integer.compare(e1.getValue(),e2.getValue()));
        int sum = 0;
        while(!list.isEmpty()){
            Map.Entry<Character,Integer> entry = list.get(0);
            int size = entry.getValue() * (n+1);

            int p= 0;
            while(!list.isEmpty()){
                Map.Entry<Character,Integer> e = list.get(0);
                int d = p+e.getValue()<=size?e.getValue():size-p;
                p += d;
                map.put(e.getKey(),e.getValue()-d);
                if(e.getValue()==0)
                    list.remove(e);
                if(p==size)
                    break;
            }
            if(!list.isEmpty()){
                sum +=size;
            }else{
                sum += size - (n+1) + p / (size / (n+1));
            }
            list.sort(Comparator.comparingInt(Map.Entry::getValue));
        }
        return sum;
    }
    public String decodeString(String s) {
        StringBuilder builder = new StringBuilder();
        Stack<String> stack = new Stack<>();
        int i=0;
        while(i<s.length()){
            StringBuilder b = new StringBuilder();
            char c = 0;
            while(i<s.length() && (c = s.charAt(i))>='a' && c<='z'){
                b.append(c);
                i++;
            }
            if(b.length()>0) {
                stack.push(b.toString());
            }
            if(c>='0' && c<='9'){
                int num = 0;
                while((c=s.charAt(i))>='0' && c<='9'){
                    num = 10 * num +(c-'0');
                    i++;
                }
                stack.push(String.valueOf(num)+"[");
                i++;
            }else if(c==']'){
                String t="";
                List<String> list = new ArrayList<>();
                while (!stack.isEmpty() && ((t =  stack.pop()).charAt(0)<'0' || t.charAt(0)>'9')){
                    list.add(t);
                }
                Collections.reverse(list);
                StringBuilder manager  = new StringBuilder();
                for(String e:list)
                    manager.append(e);
                stack.push(manager.toString().repeat(Integer.parseInt(t.substring(0, t.length()-1))));
                i++;
            }
        }
        for (String s1 : stack) {
            builder.append(s1);
        }
        return builder.toString();
    }
}
