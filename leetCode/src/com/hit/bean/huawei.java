package com.hit.bean;

import com.sun.tools.javac.Main;

import java.nio.channels.WritableByteChannel;
import java.util.*;

public class huawei {
    public static void main(String[] args){
        huawei m = new huawei();
        int[] plant = new int[]{2,2,3,3};
        int cap = 5;
        System.out.println(m.kMirror(3,2));

    }
    public int longestSubsequence(int[] arr, int difference) {
        Map<Integer,Integer> diff = new HashMap<>();
        int max =Integer.MIN_VALUE;
        for(int e:arr){
            int prev = e - difference;
            int p = diff.getOrDefault(prev,0)+1;
            diff.put(e,p);
            max = Math.max(max,p);
        }
        return max;
    }
    public int[] platesBetweenCandles(String s, int[][] queries) {
        Map<Integer,Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i)=='|'){
                list.add(i);
            }
        }
        int[] ret = new int[queries.length];
        for(int i=0;i<ret.length;i++){
            int[] que = queries[i];
            int floor =findFloor(list,que[1]),ceil = findCeil(list,que[0]);
            if(ceil > floor){
                ret[i] += list.get(floor) - list.get(ceil) - (ceil - floor);
            }
        }
        return ret;
    }
    public int findCeil(List<Integer> list ,int target){
        int left = -1,right= list.size(),mid = (left +right) / 2;
        while (right-left>1){
            if(list.get(mid)>=target){
                right = mid;
            }else{
                left = mid;
            }
            mid = (left + right) / 2;
        }
        return right;
    }
    public int findFloor(List<Integer> list,int target){
        int left = -1,right= list.size(),mid = (left +right) / 2;
        while (right-left>1){
            if(list.get(mid)>target){
                right = mid;
            }else{
                left = mid;
            }
            mid = (left + right) / 2;
        }
        return left;
    }
    public int maxTwoEvents(int[][] events) {
        int size = events.length;
        List<int[]> list = new ArrayList<>();
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(e->-e[0]));
        for(int[] e: events){
            list.add(e);
            queue.add(e);
        }
        list.sort(Comparator.comparingInt(e->e[1]));
        int[] dp = new int[size];
        int[] dp_ = new int[size];
        for(int i=0;i<size;i++){
            int[] event = list.get(i);
            dp[i] = Math.max(i-1>=0?dp[i-1]:-1,event[2]);
        }
        int ret = 0;
        int max = 0;
        for(int i= size - 1;i>=0;i--){
            int[] event = list.get(i);
            while(!queue.isEmpty() && queue.peek()[0]>event[1]){
                max = Math.max(max,queue.poll()[2]);
            }
            ret = Math.max(ret,max + dp[i]);
        }
        return ret;
    }
    public int maxCoins(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        int n = nums.length;
        for(int i=n-1;i>=0;i--){
            dp[i][i] = nums[i];
            for(int j=i+1;j<n;j++){
                int e1 = nums[i] * nums[i+1];
                int e2 = nums[j] * nums[j-1];
                int max = Math.max(e1 + dp[i+1][j],e2 + dp[i][j-1]);
                for(int k=i+1;k<j;k++){
                    max = Math.max(nums[k] * nums[k-1] * nums[k+1] + dp[i][k-1] + dp[k+1][j],
                            max);
                }
                dp[i][j] = max;
            }
        }
        return dp[0][n-1];
    }
    public void flatten(TreeNode root) {
        dfsFlatten(null,root);
    }
    public void dfsFlatten(TreeNode prev,TreeNode root){
        TreeNode right = root.right;
        TreeNode left = root.left;
        if(prev==null){
            prev = root;
        }else{
            prev.right = root;
            prev.left = null;
            prev = root;
        }
        if(left!=null){
            dfsFlatten(prev,left);
        }
        if(right!=null){
            dfsFlatten(prev,right);
        }
    }
    public int getMoneyAmount(int n) {
        int[][] money = new int[n][n];
        for(int i= n-1;i>=0;i--){
            money[i][i] = 0;
            for(int j=i+1;j<n;j++){
                int min = Integer.MAX_VALUE;
                for(int k=i;k<=j;k++){
                    int e1 = k-1<=i?0:money[i][k-1];
                    int e2 = k+1>=j?0:money[k+1][j];
                    min = Math.min(k+1+Math.max(e1,e2),min);
                }
                money[i][j] = min;
            }
        }
        return money[0][n-1];
    }
    public int findMinStep(String board, String hand) {
        if(board.equals("")){
            return 0;
        }
        int step = 0;
        Queue<BoardHand> queue = new ArrayDeque<>();
        Set<BoardHand> visited = new HashSet<>();
        queue.add(new BoardHand(board,hand));
        visited.add(new BoardHand(board,hand));
        while(!queue.isEmpty()){
            step++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                BoardHand top = queue.poll();
                String s1 = top.board;
                String s2 = top.hand;
                StringBuilder builder = new StringBuilder();
                for(int k=0;k<s2.length();k++) {
                    char c = s2.charAt(k);
                    for (int j = 0; j < s1.length(); j++) {
                        String s = builder.append(s1, 0, j).append(c).append(s1,j,s1.length()).toString();
                        String rets = eliminateBoard(s);
                        if(rets.length()==0)
                            return step;
                        BoardHand b = new BoardHand(s,s2.substring(0,k)+s2.substring(k+1));
                        if(!visited.contains(b)){
                            queue.add(b);
                            visited.add(b);
                        }
                        builder.replace(0,builder.length(),"");
                    }
                }

            }
        }
        return -1;
    }
    public String eliminateBoard(String board){
        StringBuilder builder = new StringBuilder();
        int l= 0 ,r = 0;
        while(r<board.length()){
            while(r<board.length()&& board.charAt(l)==board.charAt(r)){
                r++;
            }
            if(r-l>=3){
                String s1 = board.substring(0,l);
                String s2 = board.substring(r);
                return eliminateBoard(s1+s2);
            }else{
                l = r;
            }
        }
        return board;
    }
    public static class BoardHand{
        public String board;
        public String hand;
        public BoardHand(String board,String hand){
            this.board = String.valueOf(board);
            this.hand = String.valueOf(hand);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BoardHand boardHand = (BoardHand) o;
            return Objects.equals(board, boardHand.board) && Objects.equals(hand, boardHand.hand);
        }

        @Override
        public int hashCode() {
            return Objects.hash(board, hand);
        }
    }
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for(int e:tasks){
            l1.add(e);
        }
        for(int e:workers){
            l2.add(e);
        }
        l1.sort(Integer::compare);
        l2.sort(Integer::compare);
        int left = 0,right = Math.min(tasks.length,workers.length)+1,mid= (left +right) / 2;
        while(right - left>1){
            if(validTasks(l1,l2,pills,strength,mid)){
                left = mid;
            }else{
                right = mid;
            }
            mid = (left +right) / 2;
        }
        return left;
    }
    public boolean validTasks(List<Integer> tasks,List<Integer> workers,int pills,int strength,int target){
        int p = workers.size()-target;
        TreeMap<Integer,Integer> map = new TreeMap<>(Integer::compare);
        for(int i=0;i<target;i++){
            int e = tasks.get(i);
            map.put(e,map.getOrDefault(e,0)+1);
        }
        while(p<workers.size()){
            int w = workers.get(p);
            Map.Entry<Integer,Integer> e;
            if(w>=(e = map.firstEntry()).getKey()){
                if(e.getValue()>1){
                    map.put(e.getKey(),e.getValue()-1);
                }else{
                    map.pollFirstEntry();
                }
                p++;
            }else if(pills>0){
                w = w+strength;
                e = map.floorEntry(w);
                if(e==null){
                    break;
                }
                if(e.getValue()==1){
                    map.remove(e.getKey());
                }else{
                    map.put(e.getKey(),e.getValue()-1);
                }
                p++;
                pills--;
            }else{
                break;
            }
        }
        return map.size()==0;
    }
    public ListNode reverseEvenLengthGroups(ListNode head) {
        List<List<ListNode>> list = new ArrayList<>();
        ListNode temp = head;
        for(int k=1;temp!=null;k++){
            int i=0;
            List<ListNode> l = new ArrayList<>();
            while(i<k && temp!=null){
                l.add(temp);
                temp = temp.next;
                i++;
            }
            list.add(l);
        }
        ListNode nil = new ListNode();
        temp = nil;
        for(List<ListNode> e:list){
            ListNode[] headTail = reverseTalHead(e);
            nil.next = headTail[0];
            nil = headTail[1];
        }
        nil.next = null;
        return temp.next;
    }
    public ListNode[] reverseTalHead(List<ListNode> node){
        if(node.size() % 2==1){
            return new ListNode[]{node.get(0),node.get(node.size()-1)};
        }
        for(int i=node.size()-1;i>0;i--){
            node.get(i).next = node.get(i-1);
        }
        return new ListNode[]{node.get(node.size()-1),node.get(0)};
    }
    public int maxProduct(String[] words) {
        Map<Integer,Integer> count = new HashMap<>();
        for(String e:words){
            int num = 0;
            for(char c:e.toCharArray()){
                num |= 1<<(c-'a');
            }
            count.put(num,Math.max(count.getOrDefault(num,e.length()),e.length()));
        }
        int max = 0;
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(count.entrySet());
        for(int i=0;i<list.size();i++){
            Map.Entry<Integer,Integer> e1 = list.get(i);
            for(int j=0;j<list.size();j++){
                Map.Entry<Integer,Integer> e2 = list.get(j);
                if((e1.getKey() & e2.getKey())==0){
                    max = Math.max(e1.getValue() * e2.getValue(),max);
                }
            }
        }
        return max;
    }
    public int[] getOrder(int[][] tasks) {
        int length = tasks.length;
        List<int[]> list = new ArrayList<>();
        int maxTime = 0;
        for(int i = 0;i<length;i++){
            int[] e = tasks[i];
            maxTime = Math.max(e[0]+e[1],maxTime);
            list.add(new int[]{e[0],e[1],i});
        }
        list.sort(Comparator.comparingInt(e->e[0]));
        Queue<int[]> queue = new PriorityQueue<>((e1,e2)->{
            if(e1[1] == e2[1])
                    return e1[2] -e2[2];
            return e1[1] - e2[1];
        });
        int tau = list.get(0)[0];
        List<Integer> order = new ArrayList<>();
        int start = 0;
        while(tau<maxTime || !queue.isEmpty()){
            while(start < length && list.get(start)[0]==tau){
                queue.add(list.get(start));
                start++;
            }
            if(queue.isEmpty())
            {
            }
            int[] top = queue.remove();
            order.add(top[2]);
            tau = tau + top[1];
            while(start < length && list.get(start)[0]<=tau){
                queue.add(list.get(start));
                start++;
            }
        }
        int[] ret = new int[length];
        for(int i=0;i<length;i++){
            ret[i] = order.get(i);
        }
        return ret;
    }
    public int maxDistance(int[] colors) {
        int length  = colors.length;
        int max =Integer.MIN_VALUE;
        for(int i=0;i<length;i++){
            for(int j = length-1;j>i;j--){
                if(colors[j]!=colors[i]){
                    max = Math.max(j-i,max);
                    break;
                }
            }
        }
        return max;
    }
    public int wateringPlants(int[] plants, int capacity) {
        int dist = 1;
        int cap = capacity - plants[0], i = 1;
        int n = plants.length;
        while (i<n){
            int plant = plants[i];
            if(cap >=plant){
                i++;
                cap -= plant;
                dist+=1;
            }else{
                cap = capacity;
                dist += i * 2;
            }
        }
        return dist;
    }
    static class RangeFreqQuery {
        Map<Integer,List<Integer>> map = new HashMap<>();
        int length;
        public RangeFreqQuery(int[] arr) {
            length = arr.length;
            for(int i=0;i<length;i++){
                map.computeIfAbsent(arr[i], k -> new ArrayList<>());
                map.get(arr[i]).add(i);
            }
        }
        public int query(int left, int right, int value) {
            if(!map.containsKey(value))
                return 0;
            int l = ceil(value,left);
            int r = floor(value,right);
            int ret = r- l;
            return Math.max(0,ret+1);
        }
        public int ceil(int value,int target){
            List<Integer> list = map.get(value);
            int left =  -1, right  = list.size(),mid = (left +right) / 2;
            while(right - left>1){
                int e =  list.get(mid);
                if(e<target){
                    left = mid;
                }else{
                    right = mid;
                }
                mid = (left + right) / 2;
            }
            return right;
        }
        public int floor(int value,int target){
            List<Integer> list = map.get(value);
            int left =  -1, right  = list.size(),mid = (left +right) / 2;
            while(right - left>1){
                int e =  list.get(mid);
                if(e>target){
                    right = mid;
                }else{
                    left = mid;
                }
                mid = (left + right) / 2;
            }
            return left;
        }
    }
    public long kMirror(int k, int n) {
        long ret = 0;
        int count =0;
        List<String> prev = new ArrayList<>();
        List<String> prev2 = new ArrayList<>();
        List<Long> r = new ArrayList<>();
        for(int i=0;i<k;i++){
            String s = String.valueOf(i);
            prev2.add(s);
            long temp;
            if((temp = isTenPor(s,k))!=-1){
                count++;
                r.add(temp);
            }
            if(count==n) {
                for(long e:r){
                    ret+=e;
                }
                return ret;
            }

        }
        for(int i=0;i<k;i++){
            String ss = String.valueOf(i).repeat(2);
            prev.add(ss);
            long temp;
            if((temp = isTenPor(ss,k))!=-1){
                count++;
                r.add(temp);
            }
            if(count==n){
                for(long e:r){
                    ret+=e;
                }
                return ret;
            }
        }
        while(count<n){
            List<String> temp = new ArrayList<>();
            long code;
            for(int i=0;i<k && count<n;i++){
                for(String e:prev2){
                    StringBuilder builder = new StringBuilder();
                    builder.append(i);
                    builder.append(e);
                    builder.append(i);
                    if((code = isTenPor(builder.toString(),k))!=-1){
                        count++;
                        r.add(code);
                        if(count==n){
                            break;
                        }
                    }
                    temp.add(builder.toString());
                }
            }
            prev2 = prev;
            prev = temp;
        }
        for(long e:r){
            ret +=e;
        }
        return ret;
    }
    public long isTenPor(String s,int carry){
        if(s.charAt(0)=='0')
            return -1;
        long sum = 0;
        for(char c:s.toCharArray()){
            sum = sum * carry + (c-'0');
        }
        String ten = String.valueOf(sum);
        int p= 0,q = ten.length()-1;
        while(q>p){
            if(ten.charAt(q)==ten.charAt(p)){
                p++;
                q--;
            }
            else{
                return -1;
            }
        }
        return sum;
    }
    static  class Solution {
        int[] nums;
        public Solution(int[] nums) {
            int length = nums.length;
            this.nums = new int[length];
            System.arraycopy(nums,0,this.nums,0,length);
        }

        public int[] reset() {
            int[] ret = new int[nums.length];
            System.arraycopy(nums,0,ret,0,nums.length);
            return ret;
        }

        public int[] shuffle() {
            int[] ret = new int[nums.length];
            System.arraycopy(nums,0,ret,0,nums.length);
            for(int i = nums.length-1;i>=0;i--){
                int index = (int)(Math.random() * (i+1.));
                int temp = ret[i];
                ret[i] = ret[index];
                ret[index] = temp;
            }
            return ret;
        }
    }
}
