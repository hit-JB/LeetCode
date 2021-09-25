package com.hit.sol;


import com.hit.bean.DictTree;
import com.hit.bean.ListNode;
import com.hit.bean.TreeNode;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SolIII {
    public static void main(String[] args) {
        SolIII sol = new SolIII();

        System.out.println(sol.numSteps("1101"));
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
    public int sumOddLengthSubarrays(int[] arr) {
        int length = arr.length;
        int[] size = new int[length];
        for(int i=0;i<length;i++){
            for(int j=1;j<=length - i;j++){
                if(j % 2 ==1)
                    for(int k=0;k<j;k++){
                        size[i+k]++;
                    }
            }
        }
        int sum = 0;
        for(int i=0;i<arr.length;i++){
            sum += size[i] * arr[i];
        }
        return sum;
    }
    public int minimumDifference(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for(int e:nums)list.add(e);
        list.sort(Integer::compare);
        int ret = Integer.MAX_VALUE;
        for(int i=0;i<=list.size()-k;i++){
            ret = Math.min(list.get(i+k-1) - list.get(i),ret);
        }
        return ret;
    }
    public String kthLargestNumber(String[] nums, int k) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length()>o2.length())
                    return 1;
                else if(o1.length()<o2.length())
                    return -1;
                else{
                    int i=0;
                    int length = o1.length();
                    while(i<length && o1.charAt(i)==o2.charAt(i))
                        i++;
                    if(i==length)
                        return 0;
                    return o1.charAt(i)-o2.charAt(i);
                }
            }
        };
        List<String> list = new ArrayList<>(Arrays.asList(nums));
        list.sort(comparator);
        return list.get(list.size()-k);
     }
    public int minSessions(int[] tasks, int sessionTime) {
        int[] min = new int[1];
        min(tasks,new int[tasks.length],sessionTime,min,new int[1],new int[1],0,0);
        return min[0];
    }
    public void min(int [] tasks,int[] visited,int sessionTime,int[] min,
                    int[] size,int[] count,int index,int sum){
       if(size[0] ==visited.length-1){
           min[0] = Math.min(min[0],count[0]);
       }
       if(sum>sessionTime){
           sum = 0;
           visited[index-1] = 0;
           count[0]++;
           size[0]--;
       }
       for(int i=index;i<visited.length;i++){
           if(visited[i]==0){
               visited[i] = 1;
               size[0]++;
               min(tasks,visited,sessionTime,min,size,count,i+1,sum+tasks[i]);
               visited[i] = 0;
               size[0] --;
           }
       }
    }

    public int numberOfUniqueGoodSubsequences(String binary) {
        return 0;
    }
    public List<TreeNode> generateTrees(int n) {
        int[] nums = new int[n];
        for(int i=0;i<n;i++){
            nums[i] = i+1;
        }
        return dfsGenerate(nums, 0, n - 1);
    }
    public List<TreeNode> dfsGenerate(int[] nums,int left,int right)
    {
        List<TreeNode> roots = new ArrayList<>();
        if(right<left) {
            roots.add(null);
            return roots;
        }

        for(int i=left;i<=right;i++){
            List<TreeNode> l = dfsGenerate(nums,left,i-1);
            List<TreeNode> r = dfsGenerate(nums,i+1,right);
            for(TreeNode _l:l) {
                for(TreeNode _r:r){
                    TreeNode root = new TreeNode(nums[i]);
                    root.left = _l;
                    root.right = _r;
                    roots.add(root);
                }
            }
        }
        return roots;
    }
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i=1;i<=amount;i++){
            for(int e:coins){
                if(i-e>=0 && dp[i-e] != Integer.MAX_VALUE){
                    dp[i] = Math.min(dp[i],dp[i-e] +1);
                }
            }
        }
        return dp[amount]==Integer.MAX_VALUE?-1:dp[amount];
    }
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ret = new int[n];//查分算法，注意边界条件即可
        for(int[] e:bookings){
            ret[e[0]-1] += e[2];
            if(e[1]<n)
                ret[e[1]] -=e[2];
        }
        for(int i=1;i<n;i++){
            ret[i] = ret[i-1] + ret[i];
        }
        return ret;
    }
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int e:nums)list.add(e);
        list.sort(Integer::compare);
        int[] ret = new int[nums.length];
        ret[0] = 1;
        int max = 1;
        for(int i=1;i<nums.length;i++){
            for(int j = i;j>=0;j--){
                if(list.get(i) % list.get(j) == 0){
                    ret[i] = Math.max(ret[i],ret[j] + 1);
                }
            }
            max = Math.max(max,ret[i]);
        }
        int index = 0;
        for(int i=list.size()-1;i>=0;i--){
            if(ret[i] ==max)
                index = i;
        }
        int num = list.get(index);
        int[] id = new int[max];
        List<Integer> retNums = new ArrayList<>();
        for(int i = index;i>=0 && max>0;i--){
            if(ret[i] == max && num % list.get(i) ==0) {
                id[max-1] = i;
                max --;
                num = list.get(i);
            }
        }
        for(int e:id)
            retNums.add(list.get(e));
        return retNums;
    }
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n][n];
        for(int i=n-1;i>=0;i--){
            for(int j=i;j<n;j++){
                if(i==j)
                    dp[i][j] = 0;
                else{
                    dp[i][j] = Integer.MAX_VALUE;
                    for(int k=i;k<=j;k++){
                        dp[i][j] = Math.min(k+1 + Math.max((k-1)<i?0:dp[i][k-1],
                                k+1>j?0:dp[k+1][j]),dp[i][j]);
                    }
                }
            }
        }
        return dp[0][n-1];
    }
    public int wiggleMaxLength(int[] nums) {
        int[] down = new int[nums.length];
        int[] up = new int[nums.length];
        down[0] = 1;up[0] = 1;
        for(int i=1;i<nums.length;i++){
            down[i] = up[i] = 1;
            int maxDown = 0,maxUp = 0;
            for(int j=i-1;j>=0;j--){
                if(nums[j] > nums[i])
                    maxDown = Math.max(maxDown,up[j]);
                if(nums[j] < nums[i])
                    maxUp = Math.max(maxUp,down[j]);
            }
            down[i] += maxDown;
            up[i] += maxUp;
        }
        int ret = 0;
        for(int i=0;i<nums.length;i++){
            ret = Math.max(ret,down[i]);
            ret = Math.max(ret,up[i]);
        }
        return ret;
    }
    public int compareVersion(String version1, String version2) {
        String[] value1 = version1.split("\\.");
        String[] value2 = version2.split("\\.");
        int min = Math.min(value1.length,value2.length);
        int i=0;
        while (i<min){
            int v1 = Integer.parseInt(value1[i]),v2=Integer.parseInt(value2[i]);
            if(v1 > v2)
                return 1;
            else if(v1 < v2)
                return -1;
            i++;
        }
        if(min == value1.length){
            while(i<value2.length)
            {
                if(value2[i].replace("0", "").equals("")) {
                    i++;
                    continue;
                }
                return -1;
            }
            return 0;
        }else{
            while(i<value1.length)
            {
                if(value1[i].replace("0", "").equals("")) {
                    i++;
                    continue;
                }
                return 1;
            }
            return 0;
        }
    }
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int e:nums){
            sum += e;
        }
        if(sum % 2==1)
            return false;
        int[][] dp = new int[nums.length+1][sum / 2+1];
        for(int i=1;i<=nums.length;i++){
            for(int j=1;j<=sum / 2;j++){
                dp[i][j] = dp[i-1][j];
                if(j-nums[i-1] >=0)
                    dp[i][j] = Math.max(dp[i-1][j] , dp[i-1][j-nums[i-1]] + nums[i-1]);
            }
        }
        return dp[dp.length-1][dp[0].length-1] == sum  /2;
    }
    public boolean canIWin(int maxChooseAbleInteger, int desiredTotal) {
        //无法选取重复数字//超时，无法获取,dfs超时，无法完成
        int sum = 0;
        for(int i=1;i<=maxChooseAbleInteger;i++)
            sum += i;
        if(sum < desiredTotal)
            return false;
        Boolean[] dp = new Boolean[1<<maxChooseAbleInteger];
        return dfsCan(0,maxChooseAbleInteger,desiredTotal,dp);
    }
    public boolean dfsCan(int state,int n,int desiredTotal,Boolean[] dp){
        for(int i=n-1;i>=0;i--) {
            Integer first = null;
            if((1<<i & state) == 0) {
                first = i + 1;
                if(first >=desiredTotal) {
                    dp[state] = true;
                    return true;
                }
                if(dp[state | 1<<i] !=null)
                {
                    if(!dp[state | 1<<i])
                    {
                        dp[state] = true;
                        return true;
                    }
                }
                else if (!dfsCan(state | 1 << i, n, desiredTotal - (i + 1),dp))
                {
                    dp[state] = true;
                    return true;
                }
            }
        }
        dp[state] = false;
        return false;
    }
    public boolean canWinII(int maxChoosableInteger, int desiredTotal){
        boolean[][] dp = new boolean[1<<maxChoosableInteger][desiredTotal];
        dp[0][0] = true;
        for(int i=0;i<1<<maxChoosableInteger;i++){
            for(int j=0;j<desiredTotal;j++){
                dp[i][j] = false;
                for(int k=0;k<maxChoosableInteger;k++){
                    if((i>>k & 1) ==0){
                        dp[i][j] = dp[i][j] || !(desiredTotal - (k + 1) < 0 || dp[i & (1 << k)][desiredTotal - (k + 1)]);
                    }
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast = head,low = head;
        for(int i=0;i<k;i++){
            fast = fast.next;
        }
        while(fast!=null){
            fast = fast.next;
            low = low.next;
        }
        return low;
    }
    public boolean PredictTheWinner(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for(int i=nums.length-1;i>=0;i--){
            for(int j=i;j<nums.length;j++){
                if(i==j)
                    dp[i][j] = nums[i];
                else{
                    dp[i][j] = Math.max(nums[i] - dp[i+1][j],nums[j] - dp[i][j-1]);
                }
            }
        }
        return dp[0][dp.length-1] >0;
    }
    public int findLongestChain(int[][] pairs) {
        List<int[]> list = new ArrayList<>(Arrays.asList(pairs));
        list.sort(Comparator.comparingInt(e -> e[1]));
        int[] dp= new int[list.size()];
        dp[0] = 1;
        int max = 1;
        for(int i=1;i<list.size();i++){
            for(int j=i-1;j>=0;j--){
                dp[i] = 1;
                if(list.get(j)[1] < list.get(i)[0]){
                    dp[i] = Math.max(dp[j]+1,dp[i]);
                }
            }
            max = Math.max(dp[i],max);
        }
        int size = 1;
        int head = list.get(0)[1];
        for(int i=1;i<list.size();i++){
            if(list.get(i)[0] >head){
                size++;
                head = list.get(i)[1];
            }
        }
        return size;
    }
    public String optimalDivision(int[] nums) {
        StringBuilder builder = new StringBuilder();
        if(nums.length==1) {
            builder.append(nums[0]);
            return builder.toString();
        }
        if(nums.length==2)
        {
            builder.append(nums[0]).append('/').append(nums[1]);
            return builder.toString();
        }
        builder.append(nums[0]).append("/(");
        for(int i=1;i<nums.length;i++)
            builder.append(nums[i]).append("/");
        builder.replace(builder.length()-1,builder.length(),"");
        builder.append(")");
        return builder.toString();
    }
    public int[] smallestK(int[] arr, int k) {
        if(k==0)
            return new int[0];
        quickSort(arr,0,arr.length-1,k);
        int[] ret = new int[k];
        System.arraycopy(arr,0,ret,0,k);
        return ret;
    }
    public void quickSort(int[] arr,int p,int q,int k){
        int rand = (int) (p + (q-p) * Math.random());
        int pair = arr[rand];
        arr[rand] = arr[q];
        arr[q] = pair;
        int i= p-1;
        for(int j=p;j<=q;j++){
            if(arr[j]<=pair){
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
         if(i-p+1<k){
            quickSort(arr,i+1,q,k-(i-p+1));
        }
         if (i-p+1>k){
            quickSort(arr,p,i-1,k);
        }
    }
    public boolean checkValidString(String s) {
        int low=0,high = 0;
        for(char c:s.toCharArray()){
            if(c=='('){
                low ++;
                high ++;
            }else if (c==')'){
                high--;
                if(low >0)
                    low --;
            }else{
                if(low >0)
                    low --;
                high ++;
            }
            if(high < 0)
                return false;
        }
        return low ==0;
    }
    public int maxProfit(int[] prices, int fee) {
        int[] buy = new int[prices.length];
        int[] sale = new int[prices.length];
        buy[0]= -prices[0]-fee;
        sale[0] = 0;
        for(int i=1;i<prices.length;i++){
            buy[i] = Math.max(buy[i-1],sale[i-1] - prices[i] - fee);
            sale[i] = Math.max(sale[i-1],buy[i-1] + prices[i]);
        }
        return sale[prices.length-1];
    }
    public int fib(int n) {
        int n_1 = 1,n_2 = 0;
        if(n==0)
            return 0;
        if(n==1)
            return 1;
        int mod = (int)Math.pow(10,9) + 7;
        for(int i=2;i<=n;i++){
            int temp = n_1;
            n_2 = (n_2 + n_1) % mod;
            n_1 = n_2;
            n_2 = temp;
        }
        return n_1;
    }
    public double new21Game(int n, int k, int maxPts) {
        double[] dp = new double[n+1];
        if(k==0)
            return 1;
        for(int i=k;i<=n;i++)
            dp[i] = 1;
        //int min = Math.min(n,maxPts);
        dp[k-1] = maxPts+k-1<=n?1:(n+1.-k) / maxPts;
        for(int i=k-2;i>=0;i--){
            if(n-i<maxPts)
            {
                dp[i] = dp[i+1] + (dp[i+1]-dp[i+maxPts+1]) / maxPts;
            }else
            {
                dp[i] = dp[i+1] + dp[i+1] / maxPts;
            }
        }
        return dp[0];
    }
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int n = price.size();
        List<List<Integer>> bag = new ArrayList<>();
        bag.addAll(special);

        int k=0;
        for(int e:price){
            List<Integer> temp = new ArrayList<>();
            for(int i=0;i<n+1;i++)
                temp.add(0);

            temp.set(k,1);
            k++;
            temp.set(n,e);
            bag.add(new ArrayList<>(temp));
        }
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;

        dfsShopping(bag,needs,0,min,0);
        return min[0];
    }
    public void dfsShopping(List<List<Integer>> bag,List<Integer> needs,
                            int sum,int[] min,int index){
        int i=0;
        while(i<needs.size()){
            if(needs.get(i)!=0){
                break;
            }
            i++;
        }

        if(i==needs.size())
        {

            min[0] = Math.min(min[0],sum);
            return;
        }
        if(index==bag.size())
            return;
        List<Integer> temp = new ArrayList<>(needs);
        for(int j=index;j<bag.size();j++){
            needs = new ArrayList<>(temp);
            List<Integer> b = bag.get(j);
            boolean can = true;
            for(int k=0;k<10;k++){
                for(int r=0;r<needs.size();r++){
                    needs.set(r,needs.get(r) - b.get(r) * k);
                    if(needs.get(r)<0) {
                            can = false;
                            break;
                    }
                }
                if(!can)
                    break;
                dfsShopping(bag,needs,sum + b.get(needs.size()) * k,min,j+1);
                needs = new ArrayList<>(temp);
            }
        }
    }
    public int countQuadruplets(int[] nums) {
        int[] count = new int[1];
        dfsCount(nums,count,0,0,0);
        return count[0];
    }
    public void dfsCount(int[] nums,int[] count,int size,int sum,int index){
        if(size == 4)
        {
            if(sum==0)
                count[0]++;
            return;
        }
        if(index >=nums.length)
            return;
        for(int i=index;i<nums.length;i++){
            if(size==3){
                dfsCount(nums,count,size+1,sum-nums[i],i+1);
            }else{
                dfsCount(nums,count,size+1,sum + nums[i],i+1);
            }
        }
    }
    public int numberOfWeakCharacters(int[][] properties) {
        List<int[]> list = new ArrayList<>(Arrays.asList(properties));
        list.sort((e1,e2)->{
            if(e1[0]==e2[0])
                return e1[1]-e2[1];
            else
                return e2[0]-e1[0];
        });
        int count = 0;
        int max_defense = Integer.MIN_VALUE;
        for (int[] e : list) {
            max_defense = Math.max(e[1], max_defense);
            if (e[1] < max_defense)
                count++;
        }
        return count;
    }
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        //如果房间访问为奇数则访问(nextVisit[i],否则访问(i+1)%n;
        return 0;
    }
    public int numSteps(String s) {
        int count = 0;
        StringBuilder builder = new StringBuilder(s);
        while(builder.length()!=1){
            int length = builder.length();
            if(builder.charAt(length-1)=='0'){
                builder.replace(length-1,length,"");
            }else{
                int index = builder.lastIndexOf("0");
                if(index==-1)
                {
                    builder.replace(1,length,"0".repeat(length));
                }else {
                    builder.replace(index, length, 1 + "0".repeat(length - index - 1));
                }
            }
            count++;
        }
        return count;
    }
}
