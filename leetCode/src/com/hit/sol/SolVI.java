package com.hit.sol;

import com.hit.bean.ListNode;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SolVI {
    static final int MAX = Integer.MAX_VALUE;
    public static void main(String[] args) {

        SolVI sol = new SolVI();
     System.out.println(sol.largestPerimeter(new int[]{1,1,2}));
     Map<Integer,Integer> map = new ConcurrentHashMap<>();

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

}
