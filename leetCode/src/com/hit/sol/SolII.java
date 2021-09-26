package com.hit.sol;

import com.hit.bean.ListNode;

import java.util.*;

public class SolII {
    public static void main(String[] args){
        SolII sol = new SolII();
//        [94,92,90,57,6,89,63,15,91,74]
//        6

        System.out.println(Arrays.toString(sol.computePrefix("ababababca")));
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
    public int latestDayToCrossII(int row, int col, int[][] cells){
        while(true){

        }
    }
    public static class UniNode{
        public int x;
        public int y;
        public UniNode parent;
        public UniNode(int x,int y,UniNode parent){
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
        public UniNode getRoot(UniNode parent){
            UniNode start = this;
            while(this.parent!=this)
                start = this.parent;
            return start;
        }
    }
    public int countArrangement(int n) {
        List<Integer>[] sets = new List[n];
        for(int i=0;i<n;i++){
            sets[i] = new ArrayList<>();
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(j % i==0 || i % j==0)
                    sets[i-1].add(j);
            }
        }
        int[] count = new int[1];
        dfsArrange(n,0,new HashSet<>(),count,sets);
        return count[0];
    }
    public void dfsArrange(int n,int index,Set<Integer> visited,int[] count,
                           List<Integer>[] sets){
        if(index==n)
        {
            count[0]++;
            return;
        }
        for(int e:sets[index]){
            if(visited.contains(e))
                continue;
            visited.add(e);
            dfsArrange(n,index+1,visited,count,sets);
            visited.remove(e);
        }
    }
    public void heapSort(int[] nums){//原地操作heap-size = nums-size,注意在建堆的时候下标是从1开始，
        //而数组下标从0开始
        for(int i = nums.length / 2;i>=1;i--){
            maxHeapFy(nums,i,nums.length);
        }
        System.out.println(Arrays.toString(nums));

        for(int i=nums.length;i>=1;i--){
            int temp = nums[0];
            nums[0] = nums[i-1];
            nums[i-1] = temp;
            maxHeapFy(nums,1,i-1);
        }
        System.out.println(Arrays.toString(nums));
    }
    public void maxHeapFy(int[] nums,int i,int heapSize){
        int left = 2 * i,right = 2*i+1;
        if(left>heapSize)
            return;
        int largest = i;
        if(nums[left-1] > nums[i-1])
            largest = left;
        if(right<=heapSize && nums[right-1] > nums[largest-1])
            largest =right;
        int temp = nums[i-1];
        if(i!=largest){
            nums[i-1] = nums[largest-1];
            nums[largest-1] = temp;
            maxHeapFy(nums,largest,heapSize);
        }
    }
    public int minSpaceWastedKResizing(int[] nums, int k) {
        int[][] dp = new int[nums.length][k+1];
        int[] maxNums = new int[nums.length];
        maxNums[0]=nums[0];
        for(int i=1;i<nums.length;i++){
            maxNums[i] = Math.max(maxNums[i-1],nums[i]);
        }
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<=k;j++){
                if(i<=j)
                    dp[i][j] = 0;
                else if(j==0){
                    for(int n =0;n<=i;n++){
                        dp[i][0] +=maxNums[i]-nums[n];
                    }
                }
                else{
                     dp[i][j] = Integer.MAX_VALUE;
                     int max = Integer.MIN_VALUE;
                     for(int start = i;start >= j;start--){
                        int sum = 0;
                        max = Math.max(max,nums[start]);
                        for(int end = start;end<=i;end++){
                            sum += max-nums[end];
                        }
                        dp[i][j] = Math.min(dp[i][j],dp[start-1][j-1] +sum);
                    }
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }
    public long maxProduct(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int length = s.length();
        for(int i = length-1;i>=0;i--){
            for(int j=i;j<length;j++){
                if(j==i)
                    dp[i][j] = true;
                else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    if(j==i+1)
                        dp[i][j]  = b;
                    else{
                        dp[i][j] = dp[i+1][j-1] && b;
                    }
                }
            }
        }
        int[] prefix = new int[s.length()],suffix = new int[length];
        for(int i=0;i<length;i++){
            for(int j=i;j<length;j++){
                if(dp[i][j]) {
                    suffix[i] = Math.max(j-i+1,suffix[i]);
                    prefix[j] = Math.max(j-i+1,prefix[j]);
                }
            }
        }
        int[] maxPrefix = new int[prefix.length],maxSuffix = new int[suffix.length];
        maxPrefix[0] = prefix[0];
        for(int i=1;i<prefix.length;i++){
            maxPrefix[i] = maxPrefix[i-1];
            if(prefix[i] %2== 1)
                maxPrefix[i] = Math.max(maxPrefix[i-1],prefix[i]);
        }
        maxSuffix[maxPrefix.length-1] = suffix[suffix.length-1];
        for(int i=suffix.length-2;i>=0;i--){
            maxSuffix[i] = maxSuffix[i+1];
            if(suffix[i] % 2==1)
            maxSuffix[i] = Math.max(maxSuffix[i+1],suffix[i]);
        }
        int max = Integer.MIN_VALUE;
        for(int i=0;i<prefix.length-1;i++){
            if(maxPrefix[i] % 2 ==1 && maxSuffix[i+1] % 2==1)
            max = Math.max(maxPrefix[i] * maxSuffix[i+1],max);
        }
        return max;
    }
    public String reverseVowels(String s) {
        Set<Character> vowels = new HashSet<>();
        char[] c = new char[]{'a','e','i','o','u','A','E','I','O','U'};
        for (char c1 : c) {
            vowels.add(c1);
        }
        StringBuilder builder = new StringBuilder();
        List<Integer> loc=new ArrayList<>();
        for(int i=0;i<s.length();i++){
            if(vowels.contains(s.charAt(i))) {
                builder.append(s.charAt(i));
                loc.add(i);
            }
        }
        builder.reverse();
        StringBuilder ret = new StringBuilder(s);
        for(int i=0;i<loc.size();i++){
            ret.replace(loc.get(i),loc.get(i)+1,builder.substring(i,i+1));
        }
        return ret.toString();
    }
    public int removeDuplicates(int[] nums) {
        int low = 0,fast = 0;
        while (fast<nums.length){
            nums[low] = nums[fast];
            while(fast < nums.length -1 &&
                    nums[fast]==nums[fast+1])
                fast++;
            fast++;
            low++;
        }
        return low;
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        Comparator<Integer> comparator  = (o1, o2) -> -Integer.compare(o1,o2);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        for(int i=0;i<k;i++){
            priorityQueue.add(nums[i]);
        }
        int[] ret = new int[nums.length-k+1];
        for(int i=0;i<ret.length;i++){
            ret[i] = priorityQueue.peek();
            priorityQueue.remove(nums[i]);
            if(i+k<nums.length)
            priorityQueue.add(nums[i+k]);
        }
        return ret;
    }
    public int[] nextGreaterElements(int[] nums){
        Stack<Integer> stack = new Stack<>();
        int[] ret = new int[nums.length];
        int i =1;
        stack.push(0);
        while(!stack.isEmpty() && i<nums.length){
            if(nums[i]<stack.peek() && i>stack.get(0)){
                stack.push(i);
            }else{
               while(!stack.isEmpty() && nums[i]>nums[stack.peek()]){
                   ret[stack.pop()] = i;
               }
               stack.push(i);
            }
            i++;
        }
        i = 0;
        while (i<stack.get(0)){
            if(nums[i] > nums[stack.peek()]){
                while(!stack.isEmpty() && nums[i]>nums[stack.peek()]){
                    ret[stack.pop()] = i;
                }
            }
            i++;
        }
        for(int e:stack){
            ret[e] = -1;
        }
        for(int j=0;j<ret.length;j++)
            if(ret[j]!=-1)
                ret[j] = nums[ret[j]];
        return ret;
    }
    public int nextGreaterElement(int n) {
        StringBuilder s = new StringBuilder(String.valueOf(n));
        for(int i = s.length()-1;i>0;i--){
            if(s.charAt(i-1) >= s.charAt(i))
                continue;
            for(int j=s.length()-1;j>=i;j--){
                if(s.charAt(j) > s.charAt(i-1))
                {
                    String temp = s.substring(i-1,i);
                    s.replace(i-1,i,s.substring(j,j+1));
                    s.replace(j,j+1,temp);
                    StringBuilder builder = new StringBuilder(s.substring(i));
                    s.replace(i,s.length(),"");
                    s.append(builder.reverse());
                }
                break;
            }
           break;
        }
        int result;
        try {
            result = Integer.parseInt(s.toString());
        }catch (NumberFormatException e){
            result = -1;
        }
        return result==n?-1:result;
    }
    public String reverseStr(String s, int k) {
        StringBuilder builder = new StringBuilder();
        int i=0;
        while(i<s.length()){
            StringBuilder temp = new StringBuilder(s.substring(i, Math.min(i + k, s.length())));
            builder.append(temp.reverse());
            if(i+k<s.length()){
                builder.append(s, i+k, Math.min(i+2 * k,s.length()));
            }
            i = i+2 * k;
        }
        return builder.toString();
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> list = new ArrayList<>();
        for (int candidate : candidates) {
            list.add(candidate);
        }
        list.sort(Integer::compare);
        List<List<Integer>> ret = new ArrayList<>();
        Stack<Integer> temp = new Stack<>();
        dfsCombine(list,target,ret,temp,0);

        return ret;
    }
    public void dfsCombine(List<Integer> candidates,int target, List<List<Integer>> ret,Stack<Integer> temp,int k){
        if(target==0 || k==candidates.size() ){
            if(target==0)
                ret.add(new ArrayList<>(temp));
            return;
        }
        if(target<0)
            return;
        for(int i=k; i<candidates.size();i++){
            if(target-candidates.get(i)<0)
                return;
            Stack<Integer> temp_ = new Stack<>();
            temp_.addAll(temp);
            for(int j = 0; j<target / candidates.get(i); j++){
                temp.add(candidates.get(i));
                Stack<Integer> stack = new Stack<>();
                stack.addAll(temp);
                dfsCombine(candidates,target-(j+1) * candidates.get(i),ret,temp,i+1);
                temp = stack;
            }
            temp = temp_;
        }
    }
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> list = new ArrayList<>();
        for (int candidate : candidates) {
            list.add(candidate);
        }
        list.sort(Integer::compare);
        List<List<Integer>> ret = new ArrayList<>();
        Stack<Integer> temp = new Stack<>();
        dfsCombineII(list,target,temp,0,ret);
        return ret;
    }
    public void dfsCombineII(List<Integer> candidates,int target,Stack<Integer> combine,int k,List<List<Integer>> ret){

        if(target==0 || k==candidates.size())
        {
            if(target==0)
                ret.add(new ArrayList<>(combine));
            return;
        }
        int i=k;
        while(i<candidates.size()){
            if(target-candidates.get(i)<0)
                return;
            combine.add(candidates.get(i));
            dfsCombineII(candidates,target-candidates.get(i),combine,i+1,ret);
            combine.pop();
            while(i+1<candidates.size() && candidates.get(i).equals(candidates.get(i + 1)))
                i++;
            i++;
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        return permuteC(list);
    }
    public List<List<Integer>> permuteC(List<Integer> list){
        List<List<Integer>> temp = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        if(list.size()==1) {
            combine.add(list.get(0));
            temp.add(combine);
            return temp;
        }
        List<List<Integer>> ret = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            List<Integer> list1 = new ArrayList<>(list);
            Integer integer = list.remove(i);
            List<List<Integer>> r = permuteC(list);
            for(List<Integer> e:r)e.add(integer);
            ret.addAll(new ArrayList<>(r));
            list = list1;
        }
        return ret;
    }
    public int maxProduct(String[] words) {
        int[] code = new int[words.length];
        for(int i=0;i<words.length;i++){
            int k=0;//注意初始值只能为0为其他值会导致结果出错
            for(char c:words[i].toCharArray()){
              k |=(1<<(c-'a'));
            }
            code[i] = k;
        }
        int max = 0;
        for(int i=0;i<code.length;i++){
            for(int j=i+1;j<code.length;j++){
                if((code[i] & code[j]) ==0){
                    max = Math.max(words[i].length() * words[j].length(),max);
                }
            }
        }
        return max;
    }
    public int compress(char[] chars) {
        int ret = 0;
        int count = 1;
        int i=0;
        int j = 0;
        while(i<chars.length){
            ret++;
            while (i < chars.length-1 && chars[i] == chars[i+1]){
                count ++;
                i++;
            }
            chars[j] = chars[i];
            j++;
            if(count>1){
                String s = String.valueOf(count);
                ret +=s.length();
                count = 1;
                for(int k=0;k<s.length();k++){
                    chars[j++] = s.charAt(k);
                }
            }
            i++;
        }
        return ret;
    }//剑指offer专项训练
    public int minCost(int[][] costs) {
        int[][] dp = new int[costs.length][3];
        dp[0][0] = costs[0][0];dp[0][1] = costs[0][1];
        dp[0][2] = costs[0][2];
        for(int i=1;i<dp.length;i++){
            dp[i][0] = Math.min(dp[i-1][1],dp[i-1][2]) + costs[i][0];
            dp[i][1] = Math.min(dp[i-1][0],dp[i-1][2]) + costs[i][1];
            dp[i][2] =Math.min(dp[i-1][0],dp[i-1][1]) + costs[i][2];
        }
        int index = dp.length-1;
        return Math.min(Math.min(dp[index][0],dp[index][1]),dp[index][2]);
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        return null;
    }
    public List<String> generateParenthesis(int n) {
        return null;
    }
    public int singleNumber(int[] nums) {
        int one = 0,two =0;
        for(int num:nums){
            int oneLast = one;
            one = ~two & (one ^ num);
            two = (~two & oneLast & num) |(two & ~oneLast & ~num);
        }
        return one;
    }
    public List<List<Integer>> threeSum(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        list.sort(Integer::compare);
        Integer[] c = list.toArray(new Integer[0]);
        List<List<Integer>> ret = new ArrayList<>();
        int i =0;
        while(i<c.length-2){
            int left = i+1,right = c.length-1;
            while(right>left){
                int sum = c[left] + c[right];
                if(sum > -c[i]){
                    right--;
                }else if(sum < -c[i]){
                    left++;
                }else{
                    ret.add(Arrays.asList(c[left],c[right],c[i]));
                    while (left!=right && c[left].equals(c[left + 1]))
                        left++;
                    left++;
                }
            }
            while (i<c.length-2 && c[i].equals(c[i + 1]))
                i++;
            i++;
        }
        return ret;
    }
    public void reorderList(ListNode head) {
        Deque<ListNode> list = new ArrayDeque<>();
        while (head!=null){
            list.add(head);
            head = head.next;
        }
        ListNode ret = null;
        while(!list.isEmpty()){
            if(ret==null)
                ret = list.pollFirst();
            else {
                ret.next = list.pollFirst();
                ret = ret.next;
            }
            ret.next = list.pollLast();
            ret = ret.next;
        }
        if(ret!=null)
            ret.next =null;
    }
    public int maximalSquare(char[][] matrix) {
        int row = matrix.length,column = matrix[0].length;
        int[][] sum = new int[row][column];
        for(int i=0;i<sum.length;i++){
            sum[i][0] = matrix[i][0]-'0';
            for(int j=1;j<sum[0].length;j++){
                sum[i][j] =sum[i][j-1] + matrix[i][j]-'0';
            }
        }
        int a = Math.min(row,column);
        while(a>=1){
            for(int i=0;i<=row-a;i++){
                for(int j=0;j<=column-a;j++){
                    boolean isSquare = true;
                    for(int k=0;k<a;k++){
                        if(sum[i+k][j+a-1] - (j-1>=0?sum[i+k][j-1]:0)!=a) {
                            isSquare = false;
                            break;
                        }
                    }
                    if(isSquare)
                        return a * a;
                }
            }
            a--;
        }
        return 0;
    }
    public boolean isPowerOfThree(int n) {
        if(n==0)
            return false;
        int start = 1;
        for(; start<=n; ){
            if(start==n)
                return true;
            start *=3;
        }
        return false;
    }
    public String alienOrder(String[] words) {
        Set<Character> alpha = new HashSet<>();
        int[][] chars = new int[26][26];
        for(int i=0;i<words.length-1;i++){
            for(int j=i+1;j<words.length;j++){
                int k =0;
                char c1,c2;
                int length = Math.min(words[i].length(),words[j].length());
                while (k<length && words[i].charAt(k) ==
                       words[j].charAt(k))
                    k++;
                if(k!=length){
                    c1 = words[i].charAt(k);
                    c2 = words[i].charAt(k);
                    alpha.add(c1);
                    alpha.add(c2);
                    chars[c2 - 'a'][c1 - 'a'] = 1;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        while(!alpha.isEmpty()){
            int size = alpha.size();
            for(char c:alpha){
                boolean isCurrent = true;
                for(char c1:alpha){
                    if(chars[c1-'a'][c-'a']==1 && c1!=c)
                    {
                        isCurrent = false;
                        break;
                    }
                }
                if(isCurrent){
                    builder.append(c);
                    alpha.remove(c);
                    break;
                }
            }
            if(alpha.size()==size)
                return "";
        }
        return builder.reverse().toString();
    }
    public int uniqueLetterString(String s) {
        //s=leeCode=92
        int mod = (int) (Math.pow(10,9) + 7);
        int[][] dp = new int[s.length()][s.length()];
        for(int i=dp.length-1;i>=0;i--){
            for(int j=i;j<dp.length;j++){
                if(i==j)
                    dp[i][j] = 1;
                else if(j==i+1){
                    dp[i][j] = 2;
                    if(s.charAt(i)!=s.charAt(j))
                        dp[i][j] +=2;
                }else{
                    dp[i][j] = (dp[i][j-1] + dp[i+1][j] - dp[i+1][j-1]) % mod;
                    Map<Character,Integer> map = new HashMap<>();
                    for(char c:s.substring(i,j+1).toCharArray()){
                        map.put(c,map.getOrDefault(c,0)+1);
                    }
                    for(int count:map.values()){
                        if(count==1)
                            dp[i][j]++;
                    }
                    dp[i][j] = dp[i][j] % mod;
                }
            }
        }
        return dp[0][dp.length-1];
    }
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        int dist = target[0] + target[1];
        for(int[] loc:ghosts){
            int enemyDist = Math.abs(target[0]-loc[0]) + Math.abs(target[1]-loc[1]);
            if(enemyDist <=dist)
                return false;
        }
        return true;
    }
    public int findGCD(int[] nums) {
        int min = Integer.MAX_VALUE,max =Integer.MIN_VALUE;
        for(int e:nums){
            min = Math.min(e,min);
            max = Math.max(e,max);
        }
        return Gcd(max,min);
    }
    public int Gcd(int a,int b){
        if(b==0)
            return a;
        if(a<b)
            return Gcd(b,a);
        return Gcd(b,a%b);
    }
    public String findDifferentBinaryString(String[] nums) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<nums.length;i++){
            String s = nums[i];
            builder.append(s.charAt(i)=='0'?1:0);
        }
        return builder.toString();
    }
    public int minimizeTheDifference(int[][] mat, int target) {
       int[] dp = new int[4900];
       int[] preDp = new int[4900];
       int row=mat.length,column=mat[0].length;
       for(int i=0;i<row;i++){
           if(i==0)
           {
               for(int j=0;j<column;j++){
                   dp[mat[i][j]]=1;
               }
               System.arraycopy(dp,0,preDp,0,dp.length);
               Arrays.fill(dp,0);
           }else{
               for(int j=0;j<column;j++) {
                   for (int k = 0; k < preDp.length; k++) {
                       if(preDp[k] ==1)
                            dp[k+mat[i][j]] |=1;
                   }
               }
               System.arraycopy(dp,0,preDp,0,dp.length);
               Arrays.fill(dp,0);
           }
       }
       int ret = Integer.MAX_VALUE;
       for(int i=0;i<preDp.length;i++){
           if(preDp[i]==1)
                ret = Math.min(Math.abs(target-i),ret);
       }
       return ret;
    }
    public int[] recoverArray(int n, int[] sums) {
        int[] ret = new int[n];
        return ret;
    }
    public int minTime(int[] time, int m)
    {
        if(m>=time.length)
            return 0;
        int[] sum = new int[time.length];
        sum[0] = time[0];
        for(int i=1;i<time.length;i++){
            sum[i] +=sum[i-1] + time[i];
        }
        int left = 0,right = sum[sum.length-1], mid = (left + right) / 2;
        while(right-left>1){
            if(satisfy(time,m,mid)){
                right = mid;
            }else{
                left = mid;
            }
            mid=(left+right)>>1;
        }
        return right;
    }
    public boolean satisfy(int[] time,int m,int target){
        int i=0;
        int count = 0;
        while(i<time.length){
            int max = Integer.MIN_VALUE;
            int temp = 0;
            count++;
            while(i<time.length) {
                temp += time[i];
                max = Math.max(max, time[i]);
                if (temp - max > target)
                {
                    break;
                }
                i++;
            }
            if(count>m)
                return false;
        }
        return true;
    }
    public int findFirstMax(int[] sums,int target){
        if(target<sums[0])
            return 0;
        if(target>=sums[sums.length-1])
            return sums.length-1;
        int left = 0,right = sums.length,mid=(left + right) /2;
        while(right-left>1){
            if(sums[mid]>target)
                right = mid;
            else if(sums[mid]<target)
                left = mid;
            else
                return mid+1;
            mid = (left + right) / 2;
        }
        return right;
    }
    public int getMaximumGenerated(int n) {
        //nums[2*i] = nums[i],nums[2*i+1]=nums[i]+nums[i+1];
        int[] nums = new int[n+1];
        if(n==0||n==1){
            if(n==0)
                return 0;
            return 1;
        }
        nums[0] = 0;nums[1]=1;
        int max = 1;
        for(int i=2;i<nums.length;i++){
            nums[i] = i % 2==0?nums[i / 2]:nums[i /2] + nums[i / 2+1];
            max = Math.max(max,nums[i]);
        }
        return max;
    }
    public String[][] partition(String s) {
        List<List<String>> ret = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        dfsPartition(s,ret,temp);
        String[][] r = new String[ret.size()][];
        for(int i=0;i<ret.size();i++){
            r[i] = ret.get(i).toArray(new String[0]);
        }
        return r;
    }
    public void dfsPartition(String s,List<List<String>> ret,List<String> temp)
    {
        if(!temp.isEmpty()&&!isPalindrome(temp.get(temp.size()-1)))
            return;
        if(s.length()==0)
        {
            ret.add(new ArrayList<>(temp));
        }
        int i=1;
        while(i<=s.length())
        {
            String subs = s.substring(0,i);
            temp.add(subs);
            dfsPartition(s.substring(i),ret,temp);
            temp.remove(temp.size()-1);
            i++;
        }
    }
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        int length = s.length();
        StringBuilder builder = new StringBuilder(),builder1 = new StringBuilder();
        for(int i=0;i<s.length();i++)
        {
            char c1 = s.charAt(i),c2 =s.charAt(length-i-1);
            if(c1>=97 && c1 <=122 ||c1 >=48 && c1<=57 )
                builder.append(c1);
            if(c2 >=97 && c2<=122||c2 >=48 && c2<=57)
                builder1.append(c2);
        }
        return builder.toString().equals(builder1.toString());
    }
    public int getSum(int a, int b) {
        int carry = 0;
        int ret = 0;
        for(int i=0;i<32;i++){
            int temp = carry;
            int aI = (a >>i & 1);
            int bI = (b >>i & 1);
            ret |= ((aI^bI^carry) & 1) << i;
            carry = (aI & bI | ((aI^bI) & temp)) & 1;
        }
        return ret;
    }
    public int matrixScore(int[][] grid) {
        for(int i=0;i<grid.length;i++){
            if(grid[i][0]==0){
                for(int j=0;j<grid[0].length;j++){
                    grid[i][j] = grid[i][j]==0?1:0;
                }
            }
        }
        for(int j=1;j<grid[0].length;j++)
        {
            int count = 0;
            for (int[] ints : grid) {
                count += ints[j];
            }
            if(count <= grid.length / 2){
                for(int i=0;i<grid.length;i++){
                    grid[i][j] = grid[i][j]==0?1:0;
                }
            }
        }
        int ret = 0;
        for (int[] ints : grid) {
            int sum = 0;
            for (int j = 0;j<grid[0].length;j++) {
                sum = (sum << 1) + ints[j];
            }
            ret += sum;
        }
        return ret;
    }
    public int integerReplacement(int n) {
        if(n==1)
            return 0;
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(n);
        visited.add(n);
        int count = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            count ++;
            for(int i=0;i<size;i++){
                int top = queue.remove();
                if((top  & 1)==1)
                {
                    if(!visited.contains(top+1)){
                        queue.add(top+1);
                        visited.add(top+1);
                        visited.add(top+1);
                    }
                    if(!visited.contains(top-1)){
                        visited.add(top-1);
                        visited.add(top-1);
                        queue.add(top+1);
                    }
                }
                else{
                    if(top / 2==1)
                        return count;
                    if(!visited.contains(top / 2))
                    {
                        queue.add(top /2);
                        visited.add(top / 2);
                    }
                }
            }
        }
        return 0;
    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[][] map = new int[n][n];
        for(int[] e:map)Arrays.fill(e,-1);
        for(int[] e:flights){
            map[e[0]][e[1]] = e[2];
        }
        long[] dpPrev = new long[n];
        long[] dp=new long[n];
        Arrays.fill(dpPrev,Integer.MAX_VALUE);
        dpPrev[src] = 0;
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[src] = 0;
        for(int p=0;p<=k;p++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(map[i][j] !=-1)
                    dp[j] = Math.min(dp[j],dpPrev[i]+map[i][j]);
                }
            }
            System.arraycopy(dp,0,dpPrev,0,dp.length);
        }
        return dp[dst]==Integer.MAX_VALUE?-1: (int) dp[dst];
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
}
