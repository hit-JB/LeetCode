package com.hit.sol;

import com.hit.bean.ListNode;
import com.hit.bean.TreeNode;
import com.sun.source.tree.Tree;

import java.util.*;

public class SolI {
    public static void main(String[] args){
        //>>代表逻辑右移，>>>代表算数右移.
        SolI sol = new SolI();
        int[] nums1 = new int[]{2,3,4,5};
        int[] nums2 = new int[]{1};
        String s = "1-1+1";

        System.out.println(sol.calculate("1-2*5"));
    }
    public String minNumber(int[] nums) {
        List<String> list = new ArrayList<>();
        for(int e:nums)list.add(String.valueOf(e));
        list.sort(this::comString);
        StringBuilder builder = new StringBuilder();
        for(String e:list)
            builder.append(e);
        return builder.toString();
    }
    public int comString(String e1,String e2){
        if(e1.length()==e2.length())
            return Integer.compare(Integer.parseInt(e1),Integer.parseInt(e2));
        else if(e1.length()<e2.length()){
            String e2_ = e2.substring(0,e1.length());
            int result = comString(e1,e2_);
            if(result!=0)
                return result;
            return comString(e1,e2.substring(e1.length()));
        }else{
            String e1_ = e1.substring(0,e2.length());
            int result = comString(e1_,e2);
            if(result!=0)
                return result;
            return comString(e1.substring(e2.length()),e2);
        }
    }
    public int translateNum(int num) {
        String s = String.valueOf(num);
        int[] dp = new int[s.length()];
        dp[0] = 1;
        for(int i=1;i<s.length();i++){
          char c = s.charAt(i),c_ = s.charAt(i-1);
          int tail = (c_-'1') * 10 + c-'0';
          dp[i] = dp[i-1];
          if(tail>0 && tail< 16)
          {
              dp[i] += i-2<0?1:dp[i-2];
          }
        }
        return dp[dp.length-1];
    }
    public int triangleNumber(int[] nums) {
        if(nums.length<3)
            return 0;
        List<Integer> list = new ArrayList<>();
        for(int e:nums) list.add(e);
        list.sort(Integer::compare);
        int sum = 0;
        for(int i=list.size()-1;i>=2;i--){
            for(int j = 0;j<=i-2;j++){
                if(list.get(j) * 2>list.get(i))
                    sum +=(i-j-1);
                else{
                    int index = findFloor(list,list.get(i)-list.get(j));
                    if(index<i)
                        sum+=i-index;
                }
            }
        }
        return sum;
    }
    public int findFloor(List<Integer> temp,int target){
        int start =0,end = temp.size(),mid = start+(end-start) / 2;
        while(end-start>1){
            if(temp.get(mid)>target){
                end = mid;
            }else{//temp.get(mid)<=target;
                start = mid;
            }
            mid = start + (end -start) / 2;
        }
        return end;
    }
    public int maxValue(int[][] grid) {
        int[][]dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for(int i=1;i<dp.length;i++)
            dp[i][0] = dp[i-1][0] + grid[i][0];
        for(int j=1;j<dp[0].length;j++)
            dp[0][j] = dp[0][j-1] + grid[0][j];
        for(int i=1;i<dp.length;i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }
    public int lengthOfLongestSubstring(String s) {
        int[] pos = new int[128];
        Arrays.fill(pos,-1);
        int i=0,j=0;
        int maxLength = 1;
        while(j<s.length()){
            while(j<s.length() && pos[s.charAt(j)] ==-1){
                pos[s.charAt(j)] = 1;
                j++;
            }
            maxLength = Math.max(j-i,maxLength);
            if(j==s.length())
                break;
            while(i<s.length() && s.charAt(i)!=s.charAt(j))
            {
                pos[s.charAt(i)] = -1;
                i++;
            }
            pos[s.charAt(i)] = -1;
            i++;
        }
        return maxLength;
    }
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int[] color = new int[graph.length];
        int[] safe = new int[graph.length];
        int n = graph.length;
        Arrays.fill(color,0);
        for(int i=0;i<color.length;i++){
            if(color[i]!=0)
                continue;
            Stack<Integer> node = new Stack<>();
            node.push(i);
            color[i] = 1;
            while(!node.isEmpty()){
                int top = node.peek();
                boolean exit = false;
                for(int index=0;index<graph[top].length;index++)
                {
                    int k = graph[top][index];
                    if(color[k] ==1 || color[k] ==2 && safe[k]==1)
                    {
                        for(int e:node)
                            safe[e] =1;
                    }
                    if(color[k] ==0) {
                        exit = true;
                        node.push(k);
                        color[k] = 1;
                        break;
                    }
                }
                if(!exit){
                    node.pop();
                    color[top] = 2;
                }
            }
        }
        List<Integer> ret = new ArrayList<>();
        for(int i=0;i<safe.length;i++){
            if(safe[i] ==0)
                ret.add(i);
        }
        return ret;
    }
    public void graphGather(int[][] map){
        int[] f = new int[map.length];
        int[] color = new int[map.length];
        int time = 0;
        for(int i=0;i<color.length;i++){
            if(color[i]!=0)
                continue;
            color[i] = 1;
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            while (!stack.isEmpty()){
                time++;
                int top = stack.peek();
                boolean exit = false;
                for(int k=0;i<map[top].length;i++){
                    if(map[top][k] == 0)
                        continue;
                    if(color[k]==0)
                    {
                        stack.push(k);
                        color[k] = 1;
                        exit = true;
                        break;
                    }
                }
                if(!exit){
                    f[stack.pop()] = time;
                }
            }
        }
    }
    public void dfsGather(int[][] map,int[] f){

    }
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix.length<1)
            return false;
        int m = 0,n = matrix[0].length-1;
        while(m<matrix.length && n>=0){
            if(matrix[m][n]>target)
                n--;
            else if(matrix[m][n]<target)
                m++;
            else
                return true;
        }
        return false;
    }
    public int nthUglyNumber(int n) {
        int[] nums = new int[n];
        nums[0] = 1;
        long min;
        for(int i=1;i<n;i++){
            min = Long.MAX_VALUE;
            for(int j=0;j<i;j++){
                long num_2 = nums[j] * 2L;
                long num_3 = nums[j] * 3L;
                long num_5 = nums[j] * 5L;
                if(num_2 > nums[i-1])
                    min = Math.min(num_2,min);
                if(num_3 > nums[i-1])
                    min = Math.min(num_3,min);
                if(num_5 > nums[i-1])
                    min = Math.min(num_5,min);
                nums[i] = (int)min;
            }
        }
        return nums[n-1];
    }
    public int shortestPathLength(int[][] graph) {
        if(graph.length==1)
            return 0;
        int goal = 0;
        List<NodeVisited> list = new ArrayList<>();
        for(int i=0;i< graph.length;i++){
            list.add(new NodeVisited(i,1<<i));
            goal += 1<<i;
        }
        int[][] map = new int[graph.length][graph.length];
        for(int i = 0;i<graph.length;i++){
            for(int e:graph[i]){
                map[i][e] = map[e][i] = 1;
            }
        }
        int dist = Integer.MAX_VALUE;
        for(NodeVisited e:list){
            Set<NodeVisited> visited = new HashSet<>();
            Queue<NodeVisited> queue = new ArrayDeque<>();
            visited.add(e);queue.add(e);
            int step = 0;
            while(!queue.isEmpty()){
                int size = queue.size();
                step++;
                for(int i=0;i<size && !queue.isEmpty();i++){
                    NodeVisited top = queue.remove();
                    for(int k=0 ;k<map[top.id].length;k++){
                        if(map[top.id][k]==0)
                            continue;
                        NodeVisited nextState = new NodeVisited(k,top.visited | 1<< k);
                        if(!visited.contains(nextState)){
                            if((nextState.visited ^ goal) == 0)
                            {
                                dist = Math.min(dist,step);
                                queue.clear();
                                visited.clear();
                                break;
                            }
                            visited.add(nextState);
                            queue.add(nextState);
                        }
                    }
                }
            }
        }
        return dist;
    }
    public static  class NodeVisited{
         Integer id;
         Integer visited;

        public NodeVisited(Integer id, Integer visited) {
            this.id = id;
            this.visited = visited;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeVisited that = (NodeVisited) o;
            return Objects.equals(id, that.id) && Objects.equals(visited, that.visited);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, visited);
        }
    }
    public int[] constructArr(int[] a) {
        int[] left = new int[a.length];
        int[] right = new int[a.length];
        left[0] = 1;
        right[a.length-1] = 1;
        for(int i=1;i<a.length;i++){
            left[i] = left[i-1] * a[i-1];
            right[a.length-i-1] = right[a.length-i] * a[a.length-i];
        }
        int[] ret = new int[a.length];
        for(int i=0;i<a.length;i++){
            ret[i] = left[i] * right[i];
        }
        return ret;
    }
    public double[] dicesProbability(int n) {
        double[] ret = new double[n * 6-(n-1)];
        int[] counts = new int[n * 6-(n-1)];
        dfsDices(n,0,0,counts);
        double sum = Math.pow(6,n) -(n-1);
        for(int i=0;i<counts.length;i++){
            ret[i] = counts[i] / sum;
        }
        return ret;
    }
    public void dfsDices(int n,int k,int dots,int[] count){
        if(k==n){
            count[dots-n] ++;
            return;
        }
        for(int i=1;i<=6;i++){
            dfsDices(n,k+1,dots+i,count);
        }
    }
    public boolean circularArrayLoop(int[] nums) {
        if(nums.length<2)
            return false;
        int[] visited = new int[nums.length];
        int i=0;
        while(i<nums.length){
            if(visited[i]==0){
                int fast = i,low = i;
                do{
                    visited[low] = 1;
                    int fastNext = fast + (nums[fast] % nums.length);
                    int lowNext = low + (nums[low] % nums.length);
                    low = lowNext<0?nums.length + lowNext:lowNext>=nums.length?
                    lowNext % nums.length:lowNext;
                    fastNext = fastNext<0?nums.length + fastNext:fastNext>=nums.length?
                            fastNext % nums.length:fastNext;
                    int fastNext2 = fastNext + (nums[fastNext] % nums.length);
                    fast = fastNext2<0?fastNext2+nums.length:fastNext2>=nums.length?
                            fastNext2 % nums.length:fastNext2;
                }while(fast!=low);
                fast = low;
                int count=0;
                int flag = nums[low]<0?-1:1;
                boolean circle = true;
                do{
                    visited[low] = 1;
                    count++;
                    int lowNext = low + (nums[low] % nums.length);
                    low = lowNext<0?nums.length + lowNext:lowNext>=nums.length?
                            lowNext % nums.length:lowNext;
                    if(nums[low] * flag < 0)
                       circle = false;
                }while(low!=fast);
                if(count>1 && circle)
                    return true;
            }
            i++;
        }
        return false;
    }
    public boolean hasCycle(ListNode head){
        if(head==null || head.next==null)
            return false;
        if(head.next==head)
            return true;
        ListNode fast = head.next;
        ListNode low = head;
        do{
            fast =  fast.next.next;
            low = low.next;
            if(fast!=null && fast==low)
                return true;
        }while(fast!=null && fast.next!=null);
        return false;
    }
    public int strToInt(String str) {
        str = str.trim();
        if(str.length()==0 || str.charAt(0)!='+' &&
        str.charAt(0)!='-' && str.charAt(0)>'9' && str.charAt(0)<'0')
            return 0;
        int flag = 1;
        int start = 0;
        if(str.charAt(0)=='-' || str.charAt(0)=='+') {
            flag = str.charAt(0) == '-' ? -1 : 1;
            start++;
        }
        int i = start;
        while(i<str.length() && str.charAt(i)<='9' && str.charAt(i)>='0')
            i++;
        if(i==start)
            return 0;
        int result;
        try {
            result = Integer.parseInt(str.substring(0,i));
        }catch (NumberFormatException e){
            if(flag==1)
                result =  Integer.MAX_VALUE;
            else
                result = Integer.MIN_VALUE;
        }
        return result;
    }
    public int maxProfit(int[] prices) {
        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        for(int i=1;i<prices.length;i++){
            dp[i] = Math.min(prices[i],dp[i-1]);
        }
        int max = Integer.MIN_VALUE;
        for(int i=0;i<prices.length;i++){
            max = Math.max(max,prices[i]-dp[i]);
        }
        return max;
    }
    public int sumNums(int n) {
/*
要求不能使用乘除法
、for、while、if、else、switch、case
等关键字及条件判断语句（A?B:C）。
 */
        int sum = 0;
        boolean flag = n >0 && ((sum=n + sumNums(n-1)) >0);
        return sum;
    }
    public String multiply(String num1, String num2) {
        StringBuilder builder = new StringBuilder();
        if(num2.length()==1){
            int c = num2.charAt(0)-'0';
            if (c==0)
                return "0";
            int carry = 0;
            for(int i=num1.length()-1;i>=0;i--)
            {
                int next = c * (num1.charAt(i) - '0');
                int e = (next + carry) % 10;
                carry = (next + carry) / 10;
                builder.append(e);
            }
            if(carry!='0')
                builder.append(carry);
            return builder.reverse().toString();
        }
         String lastMul = multiply(num1,num2.substring(0,1));
         String prev = multiply(num1,num2.substring(1));
         if(lastMul.equals("0"))
            return prev;
         StringBuilder ret = new StringBuilder();
         lastMul = lastMul +"0".repeat(num2.length()-1);
         int carry = 0;
         for(int i= 0;i<prev.length();i++){
             int next = (prev.charAt(prev.length()-1-i) - '0') +
                     lastMul.charAt(lastMul.length()-1-i)- '0' + carry;
             carry = next >=10?1:0;
             ret.append(next % 10);
         }
         for(int j = prev.length();j<lastMul.length();j++){
             int next = lastMul.charAt(lastMul.length()-1-j)-'0' + carry;
             carry = next >=10?1:0;
             ret.append(next % 10);
         }
         if(carry!=0)
             ret.append(carry);
         return ret.reverse().toString();
    }
    public boolean isPrefixString(String s, String[] words) {
        int i=0;
        int k =0;
        while(i<s.length() && k<words.length){
            if(i+words[k].length() > s.length()
                    || !s.startsWith(words[k], i))
                return false;
            i = i+words[k].length();
            k++;
        }
        return k < words.length && i==s.length();
    }
    public int minStoneSum(int[] piles, int k) {
        Comparator<Integer> comparator = (o1, o2) -> -Integer.compare(o1,o2);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator
        );
        for(int e:piles)priorityQueue.add(e);
        for(int i=0;i<k;i++){
            Integer top = priorityQueue.poll();
            priorityQueue.add(top - top/2);
        }
        int sum = 0;
        while(!priorityQueue.isEmpty())
            sum+=priorityQueue.poll();
        return sum;
    }
    public int minSwaps( String s) {
        //]]][[["
        if(s.length()==0)
            return 0;
        char first =s.charAt(0);
        char last = s.charAt(s.length()-1);
        if(first=='[' && last==']')
            return minSwaps(s.substring(1,s.length()-1));
        else if(first==']' && last=='[')
            return 1+minSwaps(s.substring(1,s.length()-1));
        else

        return 0;
    }
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int[] dp = new int[obstacles.length];
        List<Integer> height = new ArrayList<>();
        dp[0] = 1;
        height.add(obstacles[0]);
        for(int i=1;i<obstacles.length;i++){
            if(obstacles[i]>= height.get(height.size()-1))
            {
                dp[i] = height.size()+1;
                height.add(obstacles[i]);
            }
            else {
                int index = divFind(height, obstacles[i]);
                dp[i] =index+1;
                height.set(index,obstacles[i]);
            }
        }
        return dp;//[1,1,2,3,2,3,4,5,3,5]
    }
    public int divFind(List<Integer> list,int target){
        if(target < list.get(0))
            return 0;
        int left = 0,right = list.size(),mid = left + (right - left) / 2;
        while(right - left>1){
            if(list.get(mid)<=target)
                left = mid;
            else
                right = mid;
            mid = left + (right - left) / 2;
        }
        return right;
    }
    public int nthSuperUglyNumber(int n, int[] primes) {
        if(n==1)
            return 1;
        int[] p = new int[primes.length];
        int[] nums = new int[n];
        nums[0] = 1;
        for(int i=1;i<n;i++){
            int min_index = 0;
            for(int j=0;j<p.length;j++){
                if(nums[p[j]] * primes[j]==nums[i-1])
                {
                    p[j]++;
                }
                if(nums[p[j]] * primes[j] < nums[p[min_index]] *
                primes[min_index])
                    min_index =j;
            }
            nums[i] = primes[min_index] * nums[p[min_index]];
            p[min_index]++;
        }
        return nums[n-1];
    }
    public ListNode rotateRight(ListNode head, int k) {
        int i=0;
        ListNode head_ = head;
        while(head_!=null){
            head_=head_.next;
            i++;
        }
        if(i==1 || i==0)
            return head;
        int rotate = k % i;
        ListNode fast = head,low = head;
        i = 0;
        while (i<rotate){
            fast= fast.next;
            i++;
        }
        while(fast.next!=null){
            low = low.next;
            fast = fast.next;
        }
        ListNode ret  = low.next;
        low.next = null;
        fast.next = head;
        return ret;
    }
    public int numberOfArithmeticSlices(int[] nums) {
        if(nums.length<3)
            return 0;
        int[] dp = new int[nums.length];
        dp[0] = dp[1] = 0;
        for(int i=2;i<dp.length;i++){
            dp[i]=(nums[i]-nums[i-1]) == (nums[i-1]-nums[i-2])?dp[i-1]+1:0;
        }
        int sum = 0;
        for(int e:dp){
            sum+=e;
        }
        return sum;
    }
    public ListNode sortList(ListNode head) {
        int length = 0;
        ListNode head_ = head;
        while(head_!=null){
            head_ = head_.next;
            length++;
        }
        if(length<=1)
            return head;
        head_ = head;
        for(int i=1;i<length / 2;i++){
            head_ = head_.next;
        }
        ListNode node2 = head_.next;
        head_.next = null;
        ListNode node1 = sortList(head);
        node2 = sortList(node2);
        return mergeListNode(node1,node2);
    }
    public ListNode mergeListNode(ListNode node1,ListNode node2){
        if(node1==null)
            return node2;
        if(node2==null)
            return node1;
        if(node1.val<=node2.val)
        {
            node1.next = mergeListNode(node1.next,node2);
            return node1;
        }
        node2.next = mergeListNode(node1,node2.next);
        return node2;
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val==p.val || root.val==q.val)
            return root;
        int[] count = new int[1];
        containPq(root.left,p.val,q.val,count);
        if(count[0]==2){
            return lowestCommonAncestor(root.left,p,q);
        }else if(count[0]==1){
            return root;
        }
        return lowestCommonAncestor(root.right,p,q);
    }
    public void containPq(TreeNode root,int p,int q,int[] count){
        if(root == null)
            return;
        if(root.val==p || root.val==q)
            count[0]++;
        containPq(root.left,p,q,count);
        containPq(root.right,p,q,count);

    }
    public TreeNode lowestCommonAncestorBottomToUp(TreeNode root, TreeNode p, TreeNode q){
        TreeNode[] ret = new TreeNode[1];
        dfsLowest(root,p.val,q.val,ret);
        return ret[0];
    }
    public boolean dfsLowest(TreeNode root,int p,int q,TreeNode[] ret){
        if(root==null)
            return false;
        boolean left = dfsLowest(root.left,p,q,ret);
        boolean right =dfsLowest(root.right,p,q,ret);
        if(left && right || (root.val == p || root.val ==q) &&(left || right))
            ret[0] = root;
        return left || right || root.val==p || root.val ==q;
    }
    public int kthSmallest(TreeNode root, int k) {
        int[] count = new int[1];
        return dfsKth(root,k,count);

    }
    public int dfsKth(TreeNode root,int k,int[] count){
        int val = -1;
        if(root.left!=null){
            val = dfsKth(root.left,k,count);
        }
        if(val!=-1)
            return val;
        count[0]++;
        if(count[0]==k)
        {
            val = root.val;
            return val;
        }
        if(root.right!=null){
            val =dfsKth(root.right,k,count);
        }
        return val;
    }
    public ListNode detectCycle(ListNode head) {
        ListNode low = head,fast  = head;
        while(fast!=null && fast.next!=null ){
            fast = fast.next.next;
            low = low.next;
            if(fast==low)
                return fast;
        }
        return null;
    }
    public int maxGap(int[] nums){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int e:nums){
            max = Math.max(max,e);
            min = Math.min(min,e);
        }
        boolean[] full = new boolean[nums.length+1];
        int[] minBuck = new int[nums.length+1];
        Arrays.fill(minBuck,Integer.MAX_VALUE);
        int[] maxBuck = new int[nums.length+1];
        Arrays.fill(maxBuck,Integer.MIN_VALUE);
        double step = (max - min) / (double)nums.length;
        for(double e:nums){
            int index =(int) ((e-min) / step);
            full[index]  = true;
            maxBuck[index] = Math.max(maxBuck[index],(int) e);
            minBuck[index] = Math.min(minBuck[index],(int) e);
        }
        int lastMax = 0;
        int firstMin;
        int ret = Integer.MIN_VALUE;
        for(int i=0;i<full.length;i++){
            if(!full[i])
                continue;
            firstMin = minBuck[i];
            if(i!=0)
                ret = Math.max(ret,firstMin-lastMax);
            lastMax = maxBuck[i];
        }
        return ret;
    }
    public String fractionToDecimal(int numerator, int denominator) {
        long num = Math.abs((long)numerator),den = Math.abs((long)denominator);
        boolean flag = numerator<0 && denominator >0 || numerator>0 && denominator < 0;
        if(numerator==0)
            return "0";
        StringBuilder builder = new StringBuilder();
        Map<Long,Integer> loc = new HashMap<>();
        long integer = num / den;
        if(flag)
            builder.append("-");
        builder.append(integer);
        long remain = (num % den) * 10L;
        if(remain==0) {
            return builder.toString();
        }
        builder.append(".");
        Integer index;
        while(remain!=0){
            integer = remain / den;
            builder.append(integer);
            loc.put(remain,builder.length()-1);
            remain = (remain % den) * 10;
            if((index = loc.getOrDefault(remain,null))!=null){
                String dot = builder.substring(index);
                String s1 = builder.substring(0,index);

                return s1 +'(' + dot + ')';
            }
            loc.put(remain,builder.length()-1);
        }
        return builder.toString();
    }
    public int countDigitOne(int n) {
        long mulk = 1;
        int ans = 0;
        for (int k = 0; n >= mulk; ++k) {
            ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
            mulk *= 10;
        }
        return ans;
    }
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int length = nums.length;
        int start = 0;
        int factor = maxFactor(nums.length,k);
        for(int i=0;i<factor;i++){
            int index = start;
            int prev = nums[index],next;
            do{
                next = nums[(index+k) % length];
                nums[(index+k) % length] = prev;
                index = (index+k) % length;
                prev = next;
            }while(index !=start);
            start++;
        }
    }
    public int maxFactor(int m,int n){
        return n>0?maxFactor(n,m%n):m;
    }
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[][] map = new int[numCourses][numCourses];
        for(int[] e:prerequisites){
            map[e[1]][e[0]] = 1;
        }
        int[] f = new int[numCourses];
        int[] color = new int[numCourses];
        int time = 0;
        for (int i = 0; i < color.length; i++) {
            if (color[i] != 0)
                continue;
            color[i] = 1;
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            while (!stack.isEmpty()) {
                time++;
                int top = stack.peek();
                boolean exit = false;
                for (int k = 0; k < map[top].length; k++) {
                    if (map[top][k] == 0)
                        continue;
                    if (color[k] == 0) {
                        stack.push(k);
                        color[k] = 1;
                        exit = true;
                        break;
                    }
                    if(color[k]==1) {
                        return new int[0];
                    }
                }
                if (!exit) {
                    int peak = stack.pop();
                    color[peak] = 2;
                    f[peak] = time;
                }
            }
        }
        int[][] loc = new int[numCourses][2];
        for(int i=0;i<f.length;i++){
            loc[i] = new int[]{i,f[i]};
        }
        List<int[]> list = new ArrayList<>();
        for(int[] e:loc) list.add(e);
        list.sort((e1,e2)-> -Integer.compare(e1[1],e2[1]));
        int[] ret = new int[numCourses];
        for(int i=0;i<ret.length;i++){
            ret[i] = list.get(i)[0];
        }
        return ret;
    }
    public int calculate(String s) {
        s = s.replaceAll(" ","");
        String[] exps = s.split("-|\\+");
        int index = 1;
        int ret = getResult(exps[0]);
        for(char c:s.toCharArray()){
            if(c=='+')
            {
                ret += getResult(exps[index]);
                index++;
            }
            if(c=='-')
            {
                ret -=getResult(exps[index]);
                index++;
            }
        }
        return ret;
    }
    public int getResult(String e){
        String[] nums = e.split("\\*|/");
        int ret = Integer.parseInt(nums[0]);
        int index = 1;
        for(char c:e.toCharArray()){
            if(c=='/')
            {
                ret /= Integer.parseInt(nums[index]);
                index++;
            }
            if(c=='*'){
                ret *= Integer.parseInt(nums[index]);
                index++;
            }
        }
        return ret;
    }
    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int[] e:pairs){
            map.put(e[0],e[1]);
            map.put(e[1],e[0]);
        }
        List<List<Integer>> list = new ArrayList<>();
        for(int[] el:preferences){
            List<Integer> temp = new ArrayList<>();
            for(int e:el){
                temp.add(e);
            }
            list.add(temp);
        }
        int count = 0;
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            int left = e.getKey(),right = e.getValue();
            List<Integer> leftPrefer = list.get(left);
            int indexRight = leftPrefer.indexOf(right);
            for(int i=0;i<indexRight;i++){
                int friend = list.get(left).get(i);
                int iFriend = map.get(friend);
                List<Integer> friendPrefer = list.get(friend);
                if(friendPrefer.indexOf(left) < friendPrefer.indexOf(iFriend))
                {
                    count +=1;
                    break;
                }
            }
        }
        return count;
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length,length2 = nums2.length;
        int loc = (length1 + length2) / 2;
        if((length1 + length2) % 2==0)
            return findMedian(nums1,nums2,loc,true);
        return findMedian(nums1,nums2,loc+1,false);
    }
    public double findMedian(int[] nums1,int[] nums2,int loc,boolean even){
        if(nums1.length==0)
        {
            if(even)
                return (nums2[loc-1] + nums2[loc]) / 2.;
            return nums2[loc-1];
        }
        if(nums2.length==0) {
            if (even)
                return (nums1[loc-1] + nums1[loc]) / 2.;
            return nums1[loc-1];
        }
        int div = loc / 2;
        if(div==0){
            if(!even)
                return Math.min(nums1[0],nums2[0]);
            if(nums1[0] < nums2[0]){
                int min = nums1.length > 1?nums1[1]:Integer.MAX_VALUE;
                min = Math.min(nums2[0],min);
                return (nums1[0] + min) / 2.;
            }
            int min = nums2.length > 1?nums2[1]:Integer.MAX_VALUE;
            min = Math.min(nums1[0],min);
            return (min + nums2[0]) / 2.;
        }
        int index1 = div-1,index2 = loc-div-1,num1,num2;//loc指第几个数
        if(div>nums1.length){
            index1 = nums1.length-1;
            index2 = loc - nums1.length-1;
        }else if(index2 >= nums2.length){
            index2 = nums2.length-1;
            index1 = loc - nums2.length - 1;
        }
        num1 = nums1[index1];num2 = nums2[index2];
        int[] nums1_,nums2_;
        if(num1 < num2){
            nums1_ = new int[nums1.length - index1-1];
            System.arraycopy(nums1,index1+1,nums1_,0,nums1_.length);
            return findMedian(nums1_,nums2,loc-index1-1,even);
        }else if(num1 == num2){
            if(!even)
                return num1;
            int min = Integer.MAX_VALUE;
            if(index1 +1 < nums1.length)
                min = Math.min(min,nums1[index1+1]);
            if(index2+1 < nums2.length)
                min = Math.min(min,nums2[index2 + 1]);
            return (num1 + min) / 2.;
        }else{
            nums2_ = new int[nums2.length - index2-1];
            System.arraycopy(nums2,index2 + 1,nums2_,0,nums2_.length);
            return findMedian(nums1,nums2_,loc - index2 - 1,even);
        }
    }
}
