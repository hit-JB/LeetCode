package com.hit.sol;

import com.hit.bean.ListNode;
import com.hit.bean.Node;
import com.hit.bean.TreeNode;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SolVI {
    static final int MAX = Integer.MAX_VALUE;
    public static void main(String[] args) {

        SolVI sol = new SolVI();
        int[] nums = new int[]{0,6,11,17,18,24};
        System.out.println(sol.minimumDifference(nums));

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
        long [] d = new long[nums.length];
        for(int i=0;i<nums.length;i++){
            d[i] = k - nums[i];
        }
        long[] preSum = new long[nums.length];
        for(int i=0;i<nums.length;i++){
            preSum[i] = nums[i] + (i-1>=0?preSum[i-1]:0);
        }
        long ret = 0;
        int N = nums.length;
        Map<Long,Long> deltaCount = new HashMap<>();
        for(int i=1;i<N;i++){
            long temp=  preSum[N-1]-2* preSum[i-1];
            deltaCount.put(temp,deltaCount.getOrDefault(temp,0L)+1);
            if(temp==0){
                ret++;
            }
        }
        Map<Long,Long> prevMap = new HashMap<>();
        Map<Long,Long> tailMap = new HashMap<>(deltaCount);
        for(int i=1;i<N;i++){
            long count1 = tailMap.getOrDefault(d[i-1],0L);
            long count2 = prevMap.getOrDefault(-d[i-1], 0L);
            ret = Math.max(ret,count1+count2);
            Long temp=  preSum[N-1]-2* preSum[i-1];
            prevMap.put(temp,prevMap.getOrDefault(temp,0L)+1);
            tailMap.put(temp,tailMap.get(temp)-1);
        }
        ret = Math.max(prevMap.getOrDefault(-d[N-1],0L),ret);
        return (int) ret;
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
    public int subarraySum(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        int ret = 0;
        int prev = 0;
        map.put(0,1);
        for(int i=0;i<nums.length;i++){
            prev += nums[i];
            if(map.containsKey(prev-k)){
                ret += map.getOrDefault(prev-k,0);
            }
            map.put(prev,map.getOrDefault(prev,0)+1);
        }
        return ret;
    }
    public List<Integer> findAnagrams(String s2, String s1) {
        List<Integer> ret = new ArrayList<>();
        int[] count = new int[26];
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        int[] dp = new int[26];
        int q = 0, p = 0;
        int id = 0;
        for(int e:count)
            if(e!=0)
                id++;
        while (p < s2.length()) {
            char c = s2.charAt(p);
            int index = c - 'a';
            dp[index]++;
            if (dp[index] == count[index]) {
                id--;
            }else if (dp[index] > count[index]) {
                while (q <= p && dp[index] > count[index]) {
                    char c1 = s2.charAt(q);
                    int index1 = c1 - 'a';
                    dp[index1]--;
                    if (dp[index1] == count[index1] - 1)
                        id++;
                    q++;
                }
            }
            if(id==0) {
                ret.add(q);
            }
            p++;
        }
        return ret;
    }
    public void gameOfLife(int[][] board) {
        int[][] directs = new int[][]{{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        int[][] map = new int[board.length][board[0].length];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                int count = 0;
                for(int[] d:directs){
                    int nextI = i+d[0],nextJ = j+d[1];
                    if(nextI>=0 && nextI<map.length && nextJ>=0 && nextJ<map[0].length){
                        if(board[nextI][nextJ]==1)
                        {
                            count++;
                        }
                    }
                }
                if(count==3 && board[i][j]==0){
                    map[i][j] = 1;
                }else if(count<2 || count>3){
                    map[i][j] = 0;
                }else{
                    map[i][j] = board[i][j];
                }
            }
        }
        for(int i=0;i<map.length;i++){
            System.arraycopy(map[i], 0, board[i], 0, map[0].length);
        }
    }
    public int videoStitching(int[][] clips, int time) {
        List<int[]> list = new ArrayList<>(Arrays.asList(clips));
        list.sort((e1,e2)->{
            if(e1[0]==e2[0]){
                return -Integer.compare(e1[1],e2[1]);
            }
            return Integer.compare(e1[0],e2[0]);
        });
        Stack<int[]> stack = new Stack<>();
        for(int[] e:list){
            if(stack.isEmpty()){
                stack.add(e);
            }else{
                int[] peak;
                while(stack.size()>1){
                    peak = stack.pop();
                    if(!(e[1]>peak[1] && e[0]<=stack.peek()[1])){
                        stack.push(peak);
                        break;
                    }
                }
                if(e[1]>stack.peek()[1] && e[0]<=stack.peek()[0])
                    stack.push(e);
            }
            if(e[0]>=time||stack.peek()[1]>=time){
                if(stack.peek()[1]>=time)
                    return stack.size();
                return -1;
            }
        }
        return -1;
    }
    public int movesToChessboard(int[][] board){
        int n = board.length,m = board[0].length;
        int[] num = new int[board.length];
        int count1 = 0,count2 = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                num[i] = 2 * num[i] + board[i][j];
            }
        }
        int num1,num2 = 0;
        num1 = num[0];
        int count = Integer.bitCount(num1);
        if(!(m % 2==0 && count == m / 2 || m % 2==1 && (count== m /2 || count==m / 2+1)))
            return -1;
        for(int i=0;i<n;i++){
            if(num[i]!=num1) {
                if((num[i] ^ num1)!=(1<<m)-1)
                    return -1;
                num2 = num[i];
                count2++;
            }else{
                count1++;
            }
        }
        if(Math.abs(count1-count2)>1)
            return -1;
        int code = 0;
        for(int e:num){
            if(e==num1){
                code = 2 * code +1;
            }else{
                code = 2 * code;
            }
        }
        int ret = 0;
        ret += countDiff(code,n);
        ret += countDiff(num1,m);
        return ret;
    }
    public  int countDiff(int num1,int m){
        int bit = Integer.bitCount(num1);
        if(bit!=m/2 && bit != (m/2+1))
            return -1;
        int max = 0,min = 0;
        if(bit == m / 2 && m % 2==0){
            for(int i = 0;i<bit;i++){
                max = 2 + 4 * max;
                min = 1 + 4 * min;
            }
        }else if(m % 2==1){
            if(bit == m/2 +1) {
                for (int i = 0; i < bit; i++) {
                    max = 1 + 4 * max;
                }
            }else{
                for(int i=0;i<bit;i++){
                    max = 2 + 4 * max;
                }
            }
        }else{
            return -1;
        }
        int n1 = 0;
        if(max!=0 && min!=0){
            int diff1 = Integer.bitCount((max ^ num1)) / 2;
            int diff2 = Integer.bitCount((min ^ num1)) / 2;
            n1 += Math.min(diff1,diff2);
        }else{
            int diff = Integer.bitCount((max ^ num1)) / 2;
            n1 += diff;
        }
        return n1;
    }
    public List<String> findRepeatedDnaSequences(String s) {
        if(s.length()<11){
            return new ArrayList<>();
        }
        Map<Character,Integer> code = new HashMap<>();
        code.put('A',0);code.put('C',1);code.put('G',2);code.put('T',3);
        Map<Integer,Integer> count = new HashMap<>();
        int temp = 0;
        for(int i=0;i<10;i++){
            char c = s.charAt(i);
            temp = 4 * temp + code.get(c);
        }
        count.put(temp,0);
        Set<String> ret = new HashSet<>();
        int max = (int) Math.pow(4,9);
        for(int i=1;i<=s.length()-10;i++){
            char cPrev = s.charAt(i-1);
            char cNext = s.charAt(i+9);
            temp = 4 * (temp - max * code.get(cPrev)) + code.get(cNext);
            Integer start;
            if((start = count.get(temp))!=null){
                ret.add(s.substring(start,start+10));
            }else{
                count.put(temp,i);
            }
        }
        return new ArrayList<>(ret);
    }
    public ListNode swapPairs(ListNode head) {
        if(head==null)
            return null;
        List<ListNode> first = new ArrayList<>();
        List<ListNode> second = new ArrayList<>();
        int i=0;
        while(head!=null) {
            if (i % 2 == 0) {
                first.add(head);
            } else {
                second.add(head);
            }
            i++;
            head = head.next;
        }
        ListNode ret = new ListNode();
        ListNode temp = ret;
        while(!second.isEmpty()){
           ret.next = second.remove(0);
           ret = ret.next;
           ret.next = first.remove(0);
           ret = ret.next;
        }
        if(!first.isEmpty()) {
            ret.next = first.remove(0);
            ret = ret.next;
        }
        ret.next = null;
        return temp.next;
    }
    public ListNode swapPairsRecursive(ListNode head){
        if(head.next==null || head==null)
            return head;
        ListNode second = head.next;
        head.next = swapPairsRecursive(head.next.next);
        second.next = head;
        return second;
    }

    public int getRandom() {
        String s= "121321";
        return 1;
    }
    public int maximum69Number (int num) {
        StringBuilder builder = new StringBuilder(String.valueOf(num));
        int i=0;
        while(i<builder.length() && builder.charAt(i)=='9'){
            i++;
        }
        if(i<builder.length())
        builder.replace(i,i+1,"9");
        return Integer.parseInt(builder.toString());
    }
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        int p =0 , q= s.length()-1;
        while(p<q){
            if(s.charAt(p)==s.charAt(q)){
                p++;
                q--;
            }else{
                return  false;
            }
        }
        return true;
    }
    public int maxTurbulenceSize(int[] arr) {
        if(arr.length==1)
            return 1;
        if(arr.length==2)
            return arr[1]==arr[0]?1:2;
        int[] dp = new int[arr.length];
        dp[0] = 1;
        dp[1] = arr[1]==arr[0]?1:2;
        int max= dp[1];
        for(int i=2;i<arr.length;i++){
            if(arr[i] > arr[i-1]){
                if(arr[i-1] >= arr[i-2]){
                    dp[i] = 2;
                }else{
                    dp[i] = dp[i-1] +1;
                }
            }else if(arr[i]==arr[i-1]){
                dp[i] = 1;
            }else{
                if(arr[i-1] > arr[i-2])
                    dp[i] = dp[i-1] +1;
                else{
                    dp[i] = 2;
                }
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }
    public int largestPerimeter(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for(int e:nums)list.add(e);
        list.sort(Comparator.comparingInt(e->e));
        int i=list.size()-1;
        int l2,l3;
        int l1;
        int max = 0;
        while(i>=2){
            int p= i-1;
            int q = i-2;
            l1= list.get(i);
            if((l2 = list.get(p)) +(l3 = list.get(q))>l1){
                max = Math.max(max,l1+l2+l3);
            }
            i--;
        }
        return max;
    }
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Set<Integer> list = new HashSet<>();
        Set<Integer> set1 = new HashSet<>(),set2 = new HashSet<>(),set3 = new HashSet<>();
        for(int e:nums1) set1.add(e);
        for(int e:nums2) set2.add(e);
        for(int e:nums3) set3.add(e);
        for(int e:set1){
            if(set2.contains(e) || set3.contains(e))
                list.add(e);
        }
        for(int e:set2){
            if(set3.contains(e)){
                list.add(e);
            }
        }
        return new ArrayList<>(list);
    }
    public int minOperations(int[][] grid, int x) {
        int min = grid[0][0],max = grid[0][0];
        List<Integer> list = new ArrayList<>();
        int m = grid.length,n = grid[0].length;
        for (int[] ints : grid) {
            for (int j = 0; j < n; j++) {
                min = Math.min(ints[j], min);
                max = Math.max(ints[j], max);
                list.add(ints[j]);
            }
        }
        list.sort(Integer::compare);
        int dist = max - min;
        if(dist % x != 0)
            return -1;
        int sum = 0;
        for(int e:list){
            if((e-min) % x!=0)
                return -1;
            sum += (e-min) / x;
        }
        int ret = sum;
        int l = m * n;
        for(int i=1;i<l;i++){
            int d = (list.get(i) - list.get(i-1)) / x;
            sum = sum + (2 * i-l) * d;
            ret = Math.min(ret,sum);
            if(2 * i-l>0)
                break;
        }
        return ret;
    }
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if(lists.size()==1)
            return lists.get(0);
        int size = lists.size();
        ListNode first = mergeKLists(new ArrayList<>(lists.subList(0,size / 2)));
        ListNode second = mergeKLists(new ArrayList<>(lists.subList(size /2,size)));
        ListNode head= new ListNode();
        ListNode temp = head;
        while(first!=null || second!=null){
            if(first==null){
                temp.next = second;
                second = second.next;
            }else if(second==null){
                temp.next = first;
                first = first.next;
            }else{
                if(first.val<=second.val){
                    temp.next = first;
                    first = first.next;
                }else{
                    temp.next = second;
                    second = second.next;
                }
            }
            temp = temp.next;
        }
        temp.next = null;
        return head.next;
    }
    public int minimumDifference(int[] nums) {
        long[] min = new long[]{Long.MAX_VALUE};
        long sum = 0;
        for(int e:nums) sum += e;
        int n = nums.length;
        int remain = n /2-1;
        for(int i = n/2-1;i<n;i++){
            dfsMinimum(i-1,remain,min,nums[i],sum,nums);
        }
        return (int) min[0];
    }
    public void dfsMinimum(int index,long remain,long[] min,long temp,long sum,int[] nums){
        if(remain==0){
            min[0] = Math.min(Math.abs(2 * temp -sum),min[0]);
            return;
        }
        for(int i = index;i+1>=remain && i>=0;i--){
            dfsMinimum(i-1,remain-1,min,temp + nums[i],sum,nums);
        }
    }
    public String numberToWords(int num) {
        return null;
    }
    public String[][] topKstrings (String[] strings, int k)
    {
        // write code here
        Map<String,Integer> count = new HashMap<>();
        for(String e:strings){
            count.put(e,count.getOrDefault(e,0)+1);
        }
        List<Map.Entry<String,Integer>> list = new ArrayList<>(count.entrySet());
        list.sort((e1,e2)->{
            if(Objects.equals(e1.getValue(), e2.getValue())){
                return e1.getKey().compareTo(e2.getKey());
            }
            return -Integer.compare(e1.getValue(),e2.getValue());
        });
        String[][] ret = new String[k][2];
        for(int i=0;i<k;i++){
            ret[i][0] = list.get(i).getKey();
            ret[i][1] = String.valueOf(list.get(i).getValue());
        }
        return ret;
    }
    public String shortestPalindrome(String s) {
        long base = 31, mod = (long) (Math.pow(10, 9) + 7);
        long prefix = 0, suffix = 0;
        int mul = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            char cl = s.charAt(s.length() - i - 1);
            prefix = (c - 'a' + base * prefix) % mod;
            suffix = (suffix + mul * cl) % mod;
        }
        return s;
    }

    public int findKNums(int[] nums, int p, int r, int k) {
        int par = nums[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (nums[j] <= par) {
                i = i + 1;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[i + 1];
        nums[i + 1] = par;
        nums[r] = temp;
        if (i + 2 - p == k)
            return par;
        else if (i + 2 - p < k)
            return findKNums(nums, i + 2, r, k - (i + 2 - p));
        else
            return findKNums(nums, p, i, k - 1);
    }

    public Node flatten(Node head) {
        return null;
    }

    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        if (nums.length % k != 0)
            return false;
        for (int e : nums) {
            map.put(e, map.getOrDefault(e, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByKey());
        while (!map.isEmpty()) {
            Map.Entry<Integer, Integer> first = list.get(0);
            int key = first.getKey();
            int value = first.getValue();
            if (value == 0) {
                list.remove(0);
                continue;
            }
            try {
                for (int i = 0; i < k; i++) {
                    map.put(key + i, map.get(key + i) - 1);
                    if (map.get(key + i) == 0)
                        map.remove(key + i);
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public int numRabbits(int[] answers) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int e : answers)
            map.put(e, map.getOrDefault(e, 0) + 1);
        int sum = 0;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int key = e.getKey();
            int value = e.getValue();
            if (key >= value) {
                sum = sum + key + 1;
            } else {
                int group = value / (key + 1);
                int remain = value % (key + 1);
                sum += group * (key + 1);
                if (remain != 0)
                    sum += (key + 1);
            }
        }
        return sum;
    }

    public boolean makesquare(int[] matchsticks) {
        long sum = 0;
        for (int e : matchsticks) {
            sum += e;
        }
        if (sum % 4 != 0)
            return false;
        long a = sum / 4;
        Map<Integer, Boolean> map = new HashMap<>();
        Map<Integer, Boolean> can = new HashMap<>(1 << matchsticks.length);
        preManager(map, matchsticks, a, 0, 0);
        manager(can, map, (1 << matchsticks.length) - 1);
        return can.get((1 << matchsticks.length) - 1);
    }

    public void manager(Map<Integer, Boolean> can, Map<Integer, Boolean> map, int code) {
        if (can.containsKey(code) || map.containsKey(code)) {
            if (map.containsKey(code))
                can.put(code, true);
            return;
        }
        boolean is;
        for (Map.Entry<Integer, Boolean> e : map.entrySet()) {
            int num = e.getKey();
            if ((num | code) == code) {
                manager(can, map, code ^ num);
                is = can.get(code ^ num);
                if (is) {
                    can.put(code, true);
                    return;
                }
            }
        }
        can.put(code, false);
    }

    public void preManager(Map<Integer, Boolean> map, int[] matchsticks,
                           long a, int code, int index) {
        if (a == 0)
            map.put(code, true);
        for (int i = index; i < matchsticks.length; i++) {
            if (a - matchsticks[i] >= 0) {
                preManager(map, matchsticks, a - matchsticks[i], code | (1 << i), i + 1);
            }
        }
    }

    public int numDecodings(String s) {
        //'A' -> 1//'B' -> 2//...//'Z' -> 26//*->any 1~9
        int mod = (int) (Math.pow(10, 9) + 7);
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            char c = s.charAt(i - 1);
            if (c == '*') {
                dp[i] = (dp[i - 1] * 9) % mod;
            } else if (c > '0') {
                dp[i] = dp[i - 1];
            }
            if (i - 2 >= 0) {
                char cPrev = s.charAt(i - 2);
                long result = (dp[i] + dp[i - 2]) % mod;
                if (cPrev == '*') {
                    if (c == '*') {
                        dp[i] = (dp[i] + (dp[i - 2] * 15)) % mod;
                    } else {
                        if (c <= '6')
                            dp[i] = (dp[i] + (dp[i - 2] * 2)) % mod;
                        else
                            dp[i] = result;
                    }
                } else if (cPrev > '0') {
                    if (c == '*') {
                        if (cPrev == '1') {
                            dp[i] = (dp[i] + (dp[i - 2] * 9)) % mod;
                        }
                        if (cPrev == '2') {
                            dp[i] = (dp[i] + (dp[i - 2] * 6)) % mod;
                        }
                    } else {
                        if ((cPrev - '0') * 10 + c - '0' <= 26)
                            dp[i] = result;
                    }
                }
            }
        }
        return (int) dp[dp.length - 1];
    }

    public int maxProduct(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (i == j)
                    dp[i][j] = 1;
                else {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = i + 1 <= j - 1 ? dp[i + 1][j - 1] + 2 : 2;
                    } else {
                        int max = i + 1 <= j - 1 ? dp[i + 1][j - 1] : 0;
                        for (int k = j - 1; k > i; k--) {
                            if (s.charAt(k) == s.charAt(i)) {
                                max = Math.max(i + 1 <= k - 1 ? dp[i + 1][k - 1] + 2 : 2, max);
                                break;
                            }
                        }
                        for (int k = i + 1; k < j; k++) {
                            if (s.charAt(k) == s.charAt(j)) {
                                max = Math.max(k + 1 <= i - 1 ? dp[k + 1][i - 1] + 2 : 2, max);
                            }
                        }
                        dp[i][j] = max;
                    }
                }
            }
        }
        int ret = Integer.MIN_VALUE;
        for (int k = 0; k < s.length() - 1; k++) {
            ret = Math.max(ret, dp[0][k] * dp[k + 1][s.length() - 1]);
        }
        return ret;
    }

    public int pathSum(TreeNode root, int targetSum) {
        int[] count = new int[1];
        dfsPathSum(root, root.val, targetSum, count);
        int left = 0, right = 0;
        if (root.left != null)
            left = pathSum(root.left, targetSum);
        if (root.right != null)
            right = pathSum(root.right, targetSum);
        return count[0] + left + right;
    }

    public void dfsPathSum(TreeNode root, int sum, int target, int[] count) {
        if (target == sum)
            count[0]++;
        if (root.left != null)
            dfsPathSum(root.left, sum + root.left.val, target, count);
        if (root.right != null)
            dfsPathSum(root.right, sum + root.right.val, target, count);
    }

    public String largestMultipleOfThree(int[] digits) {

        List<Integer> zero = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        for (int e : digits) {
            if (e % 3 == 0)
                zero.add(e);
            else if (e % 3 == 1)
                one.add(e);
            else
                two.add(e);
        }
        one.sort((e1, e2) -> -Integer.compare(e1, e2));
        two.sort((e1, e2) -> -Integer.compare(e1, e2));
        int indexOne = one.size() / 3 * 3, indexTwo = two.size() / 3 * 3;
        List<Integer> ret = new ArrayList<>();
        ret.addAll(zero);
        ret.addAll(one.subList(0, indexOne));
        ret.addAll(two.subList(0, indexTwo));
        int remainOne = one.size() - indexOne;
        int remainTwo = two.size() - indexTwo;
        int remain;
        if ((remain = Math.min(remainOne, remainTwo)) > 0) {
            ret.addAll(one.subList(indexOne, indexOne + remain));
            ret.addAll(two.subList(indexTwo, indexTwo + remain));
        }
        ret.sort(Integer::compare);
        if (ret.size() != 0 && ret.get(ret.size() - 1) == 0)
            return "0";
        StringBuilder builder = new StringBuilder();
        for (int i = ret.size() - 1; i >= 0; i--) {
            builder.append(ret.get(i));
        }

        return builder.toString();
    }

    public String shortestSuperstring(String[] words) {
        int[][] l = new int[words.length][words.length];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) {
                    l[i][j] = Integer.MAX_VALUE;
                } else {
                    for (int k = 0; k < Math.min(words[i].length(), words[j].length()); k++) {
                        if (words[i].substring(words[i].length() - 1 - k).equals(words[j].substring(0, k + 1))) {
                            l[i][j] = Math.max(l[i][j], k + 1);
                        }
                    }
                }
            }
        }
        //用时过长，时间为0(n!)
//        List<Integer> path = new ArrayList<>();
//        dfsWordPath(l,new ArrayList<>(),path,0,new int[1]);
        int N = words.length;
        int[][] mask = new int[N][N];
        for (int i = 1; i < 1 << N; i++) {
            int bit = 1;
            for (int b = 0; bit < i; b++) {
                bit = bit << b;
                if ((bit | i) == i) {
                    int num = bit ^ i;
                    for (int k = 0; k < N; k++) {
                        if (mask[num][k] != 0) {
                            mask[i][b] = Math.max(mask[i][b], mask[num][k] + l[k][b]);
                        }
                    }
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        int max = 0;
        int startIndex = 0;
        for (int i = 0; i < N; i++) {
            if (mask[(1 << N) - 1][i] > max) {
                max = mask[(1 << N) - 1][i];
                startIndex = i;
            }
        }
        path.add(startIndex);
        StringBuilder builder = new StringBuilder();
        builder.append(words[path.get(0)]);
        for (int i = 1; i < path.size(); i++) {
            int index = path.get(i);
            int prev = path.get(i - 1);
            builder.append(words[index].substring(l[prev][index]));
        }
        return builder.toString();
    }

    public void dfsWordPath(int[][] map, List<Integer> list, List<Integer> path, int sum, int[] min) {
        if (list.size() == map.length) {
            if (sum >= min[0]) {
                min[0] = sum;
                path.clear();
                path.addAll(list);
            }
            return;
        }
        for (int i = 0; i < map.length; i++) {
            if (list.contains(i))
                continue;
            list.add(i);
            if (list.size() >= 2)
                dfsWordPath(map, list, path, sum + map[list.get(list.size() - 2)][list.get(list.size() - 1)], min);
            else
                dfsWordPath(map, list, path, sum, min);
            list.remove(list.size() - 1);
        }
    }

    public boolean checkInclusion(String s1, String s2) {
        int[] count = new int[26];
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        int[] dp = new int[26];
        int q = 0, p = 0;
        int id = 0;
        for(int e:count)
            if(e!=0)
                id++;
        while (p < s2.length()) {
            char c = s2.charAt(p);
            int index = c - 'a';
            dp[index]++;
            if (dp[index] == count[index]) {
                id--;
            }else if (dp[index] > count[index]) {
                while (q <= p && dp[index] > count[index]) {
                    char c1 = s2.charAt(q);
                    int index1 = c1 - 'a';
                    dp[index1]--;
                    if (dp[index1] == count[index1] - 1)
                        id++;
                    q++;
                }
            }
            if(id==0)
                return true;
            p++;
        }
        return false;
    }

    public int findBestValue(int[] arr, int target) {
        List<Long> list = new ArrayList<>();
        for(int e:arr)list.add((long) e);
        Collections.sort(list);
        long[] sum = new long[arr.length];
        sum[0] = list.get(0);
        for(int i=1;i< arr.length;i++){
            sum[i] = sum[i-1] + list.get(i);
        }

        long delta = Long.MAX_VALUE;
        long ret = Long.MAX_VALUE;
        if(sum[sum.length-1]<=target){
            ret = list.get(list.size()-1);
            return (int) ret;
        }
        long e;
        if((e = target / sum.length)<=list.get(0)){
            long l = target - e * sum.length <= (e + 1) * sum.length - target ? e : e + 1;
            return (int) l;
        }
        for(int i=sum.length-2;i>=0;i--){
            e = sum[i];
            int n = sum.length - 1-i;
            long num;
            long d;
            if((num = (target - e) / n)<=list.get(i+1) && num>= list.get(i)){
                long temp;
                if((temp = target - e - num * n)<delta){
                    delta = temp;
                    ret = num;
                }else if(temp==delta){
                    ret = Math.min(ret,num);
                }
                if(-temp + n<delta){
                    ret = num+1<=list.get(i+1)?num+1:ret;
                }
            }
        }
        return (int) ret;
    }
    public int findIndex(long[] sum,int target){
        int left =-1,right = sum.length,mid = (left +right) / 2;
        while(right-left>1){
            if(sum[mid] > target){
                right = mid;
            }else{
                left = mid;
            }
            mid = (left +right) / 2;
        }
        return right;
    }
    public int[][] transpose(int[][] matrix) {
        int[][] ret = new int[matrix[0].length][matrix.length];
        for(int j=0;j<matrix[0].length;j++){
            for(int i=0;i<matrix.length;i++){
                ret[j][i] = matrix[i][j];
            }
        }
        return ret;
    }
    public String reversePrefix(String word, char ch) {
        int index = word.indexOf(ch);
        if(index==-1)
            return word;
        String subs = word.substring(0,index+1);
        StringBuilder builder = new StringBuilder();
        for(int i=subs.length()-1;i>=0;i--)
            builder.append(subs.charAt(i));
        builder.append(word.substring(index+1));
        return builder.toString();
    }
    public long interchangeableRectangles(int[][] rectangles) {
        for(int[] e:rectangles){
            int d = maxDiv(e[0],e[1]);
            e[0] = e[0] / d;
            e[1] = e[1] / d;
        }
        long sum = 0;
        Map<Pair,Integer> map = new HashMap<>();
        for (int[] rectangle : rectangles) {
            Pair pair = new Pair(rectangle);
            Integer count;
            if ((count = map.get(pair)) == null) {
                map.put(pair, 0);
            } else {
                map.put(pair, count + 1);
                sum += count + 1;
            }
        }
        return sum;
    }
    static class Pair{
        public int[] e;
        public Pair(int[] e){
            this.e = e;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Arrays.equals(e, pair.e);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(e);
        }
    }
    public int maxDiv(int n1,int n2){
        if(n1<n2)
            return maxDiv(n2,n1);
        if(n2==0)
            return n1;
        return maxDiv(n2,n1 % n2);
    }
}
