package com.hit.sol;


import com.hit.bean.DictTree;

import java.util.*;

public class SolIII {
    public static void main(String[] args){
        SolIII sol = new SolIII();
        int[][] roads = new int[][]{{0,6,7},{0,1,2},{1,2,3},{1,3,3},
                {6,3,3},{3,5,1},{6,5,1},{2,5,1},{0,4,5}};
        System.out.println(sol.countPaths(7,roads));

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
        DictTree root = new DictTree();
        for(String s:dictionary){
            root.addWord(s);
        }
        int[] dp = new int[sentence.length()];
        dp[0] = 1;
        for(int i=1;i<dp.length;i++){
            dp[i] = dp[i-1] + 1;
            for(int j=i;j>=0;j--){
                if(root.containWord(sentence.substring(j,i+1)))
                    dp[i] = Math.min(dp[i],j-1>=0?dp[j-1]:1);
            }
        }
        return dp[dp.length-1];
    }
    public String shortestPalindrome(String s) {
        //等价于寻找s的最长前缀字串使它成为
        //一个回文串
        int[] prefix = computePrefix(s);
        String s_ = (new StringBuilder(s)).reverse().toString();
        int k=-1;
        for(int i=0;i<s_.length();i++){
            while(k>-1 && s.charAt(k+1)!=s_.charAt(i))
                k = prefix[k];
            if(s.charAt(k+1)==s_.charAt(i))
                k++;
        }
        return s_.substring(0,s_.length()-k-1) + s;
    }
    public int[] computePrefix(String p){
        int m = p.length();
        int[] ret = new int[m];
        ret[0]  = -1;
        int k = -1;
        for(int q = 1;q<m;q++){
            while(k>-1 && p.charAt(k+1)!=p.charAt(q))
                k = ret[k];
            if(p.charAt(k+1)==p.charAt(q))
                k+=1;
            ret[q] = k;
        }
        return ret;
    }
    public int numRescueBoats(int[] people, int limit) {
        List<Integer> sort = new ArrayList<>();
        for(int e:people)
            sort.add(e);
        sort.sort(Integer::compare);
        int start = 0,end = people.length-1;
        int count = 0;
        while(start<end){
            if(sort.get(start) + sort.get(end) <= limit){
                start++;
                end--;
                count++;
            }else{
                end--;
                count++;
            }
        }
        if(start==end)
            count++;
        return count;
    }
    public String longestDupSubstring(String s) {
        int left = -1,right = s.length(),mid = (left + right) / 2;
        while(right-left>1){
            if(checkIsDul(s,mid)!=null){
                left = mid;
            }else{
                right = mid;
            }
            mid = (left + right) / 2;
        }
        if(left!=-1)
            return checkIsDul(s,left);
        return "";
    }
    public String checkIsDul(String s,int k){
//        int mod = (int) (Math.pow(10,9)+7),base = 31;
//        int code = 0;
        Set<Integer> set = new HashSet<>();
       // int mul = 1;
        for(int i=0;i<=s.length()-k;i++){
            String substring = s.substring(i, i + k);
            if(set.contains(substring.hashCode())) {
                if(s.lastIndexOf(substring) != s.indexOf(substring))
                    return substring;
            }
            set.add(substring.hashCode());
        }
        return null;
    }
    public int minTimeToType(String word) {
        int sum = Math.min('a'+26-word.charAt(0),word.charAt(0)-'a') +1;
        for(int i=1;i<word.length();i++){
            char c = word.charAt(i);
            char c_prev = word.charAt(i-1);
            sum += Math.min(Math.abs(c-c_prev),Math.min(c-'a',c_prev-'a') + 26-Math.max(c-'a',c_prev-'a')) +1;
        }
        return sum;
    }
    public long maxMatrixSum(int[][] matrix) {
        //置换不变性质，注意条件
        int min_negative = Integer.MIN_VALUE,count = 0;
        int min_max = Integer.MAX_VALUE;
        long sum = 0;
        boolean zero = false;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++) {
                if (matrix[i][j] == 0)
                    zero = true;
                else if(matrix[i][j]<0)
                {
                    min_negative =  Math.max(matrix[i][j],min_negative);
                    count ++;
                }else{
                    min_max = Math.min(min_max,matrix[i][j]);
                }
                sum +=Math.abs(matrix[i][j]);
            }
        }
        //[[2,9,3],[5,4,-4],[1,7,1]]
        if(count % 2==0 || zero)
            return sum;
        return sum - 2L * Math.min(-min_negative,min_max);
    }
    public int countPaths(int n, int[][] roads) {
        int[][] map = new int[n][n];
        for(int[] e:roads){
            map[e[0]][e[1]] = map[e[1]][e[0]] = e[2];
        }
        int mod = (int) (Math.pow(10,9)+7);
        int[] min_dist = new int[n];
        Arrays.fill(min_dist,Integer.MAX_VALUE);
        min_dist[0] = 0;
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        while(visited.size()!=n){
            for(int e:visited) {
                for (int i = 0; i < n; i++)
                    if(!visited.contains(i) && map[e][i]!=0)
                        min_dist[i] = Math.min(map[e][i] + min_dist[e], min_dist[i]);
            }
            Integer min_index = null;
            for(int i=0;i<n;i++){
                if(!visited.contains(i))
                {
                    min_index = min_index==null?i:(min_dist[i]<min_dist[min_index]?i:min_index);
                }
            }
            visited.add(min_index);
        }
        int[] ways = new int[n];
        ways[0] = 1;
        int[][] map_ = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++)
                if(min_dist[j] == min_dist[i] + map[i][j])
                    map_[i][j] = 1;
        }
        Map<Integer,Integer> sort = new HashMap<>();
        for(int i=0;i<min_dist.length;i++)
            sort.put(i,min_dist[i]);
        List<Map.Entry<Integer,Integer>> pair = new ArrayList<>(sort.entrySet());
        pair.sort((e1,e2)->{
            if(e1.getValue().equals(e2.getValue()))
                return e1.getKey()-e2.getKey();
            return e1.getValue()-e2.getValue();
        });
        ways[0] = 1;
        for(int i=1;i<pair.size();i++){
            int index = pair.get(i).getKey();
            for(int j=0;j<i;j++){
                int prev = pair.get(j).getKey();
                if(map_[prev][index] == 1)
                ways[index] = (ways[prev] + ways[index]) % mod;
            }
        }
        return ways[n-1];
    }
    public int numberOfCombinations(String num) {
        return 0;
    }
    public int numWays(int n) {
        int mod = (int) (Math.pow(10,9) + 7);
        int[] dp = new int[n+1];
        dp[0] = 1;
        for(int i=1;i<=n;i++){
            dp[i] = (dp[i-1] + (i-2>=0?dp[i-2]:0)) % mod;
        }
        return dp[n];
    }
    public int[][] updateMatrix(int[][] mat) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        int[][] ret = new int[mat.length][mat[0].length];
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                if(mat[i][j]==0)
                visited.add(i + "_" + j);
                queue.add(i+"_"+j);
            }
        }
        int step = 0;
        int[][] direct = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        while (!queue.isEmpty()){
            int size = queue.size();
            step++;
            for(int i=0;i<size;i++){
                String[] top = (Objects.requireNonNull(queue.poll()).split("_"));
                int row = Integer.parseInt(top[0]),column = Integer.parseInt(top[1]);
                for(int[] e:direct){
                    int nextRow = row+e[0],nextColumn = column+e[1];
                    if(nextRow>=0&&nextRow<mat.length && nextColumn<mat[0].length
                    && nextColumn>=0 && !visited.contains(nextRow+"_"+nextColumn))
                    {
                        ret[nextRow][nextColumn] = step;
                        visited.add(nextRow+"_"+nextColumn);
                        queue.add(nextRow+"_"+nextColumn);
                    }
                }
            }
        }
        return ret;
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        dp[0][0] = obstacleGrid[0][0]==0?1:0;
        for(int i=0;i<obstacleGrid.length;i++){
            for(int j=0;j<obstacleGrid[0].length;j++){
                if(obstacleGrid[i][j] ==1)
                {
                    dp[i][j] = 0;
                    continue;
                }
                if(i-1>=0 && obstacleGrid[i-1][j] == 0)
                    dp[i][j] += dp[i-1][j];
                if(j-1>=0 && obstacleGrid[i][j-1] == 0)
                    dp[i][j] += dp[i][j-1];
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }
}
