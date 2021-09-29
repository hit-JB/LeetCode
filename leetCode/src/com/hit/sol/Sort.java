package com.hit.sol;


import com.hit.bean.Node;
import com.hit.bean.TreeNode;
import com.sun.source.tree.Tree;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

public class Sort {
    public static void main(String[] args){
        Sort sort = new Sort();
        System.out.println(sort.shortestSuperstring(new String[]{"alex","loves","leetcode"}));
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
    /*
    快速排序，重点是要找到分治点，然后分而治之
     */
    public void quickSort(int[] nums,int p,int r){
        if(p<r){
            int q = partition(nums,p,r);
            quickSort(nums,p,q-1);
            quickSort(nums,q+1,r);
        }
    }
    public int partition(int[] nums,int p,int r){
        int par = nums[r];
        int i = p-1;
        for(int j=p;j<=r-1;j++){
            if(nums[j]<=par){
                i=i+1;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[i+1];
        nums[i+1] = par;
        nums[r] = temp;
        return i+1;
    }
    public String shortestPalindrome(String s) {
        long base = 31,mod = (long) (Math.pow(10,9)+7);
        long prefix = 0,suffix = 0;
        int mul = 1;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            char cl = s.charAt(s.length()-i-1);
            prefix = (c-'a' + base * prefix) % mod;
            suffix = (suffix  + mul * cl) % mod;
        }
        return s;
    }
    public int findKNums(int[] nums,int p,int r,int k){
        int par = nums[r];
        int i = p-1;
        for(int j=p;j<=r-1;j++){
            if(nums[j]<=par){
                i=i+1;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[i+1];
        nums[i+1] = par;
        nums[r] = temp;
        if(i+2-p==k)
            return par;
        else if(i+2-p<k)
            return findKNums(nums,i+2,r,k-(i+2-p));
        else
            return findKNums(nums,p,i,k-1);
    }
    public Node flatten(Node head) {
        return null;
    }
    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        if(nums.length % k !=0)
            return false;
        for(int e:nums)
        {
            map.put(e,map.getOrDefault(e,0)+1);
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByKey());
        while(!map.isEmpty()){
            Map.Entry<Integer,Integer> first = list.get(0);
            int key = first.getKey();
            int value = first.getValue();
            if(value==0)
            {
                list.remove(0);
                continue;
            }
            try {
                for(int i=0;i<k;i++){
                    map.put(key+i,map.get(key+i)-1);
                    if(map.get(key+i)==0)
                        map.remove(key+i);
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
    public int numRabbits(int[] answers) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int e:answers)
            map.put(e,map.getOrDefault(e,0)+1);
        int sum = 0;
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            int key = e.getKey();
            int value = e.getValue();
            if(key>=value){
                sum = sum+key+1;
            }else{
                int group = value / (key+1);
                int remain = value % (key+1);
                sum += group * (key+1);
                if(remain!=0)
                    sum += (key+1);
            }
        }
        return sum;
    }
    public boolean makesquare(int[] matchsticks) {
        long sum = 0;
        for(int e:matchsticks){
            sum +=e ;
        }
        if(sum % 4!=0)
            return false;
        long a = sum / 4;
        Map<Integer,Boolean> map = new HashMap<>();
        Map<Integer,Boolean> can = new HashMap<>(1<<matchsticks.length);
        preManager(map,matchsticks,a,0,0);
        manager(can,map,(1<<matchsticks.length)-1);
        return can.get((1<<matchsticks.length)-1);
    }
    public void manager(Map<Integer,Boolean> can, Map<Integer,Boolean> map,int code){
        if(can.containsKey(code) || map.containsKey(code)) {
            if(map.containsKey(code))
                can.put(code,true);
            return;
        }
        boolean is;
        for(Map.Entry<Integer,Boolean> e:map.entrySet()){
            int num = e.getKey();
            if((num | code) == code){
                manager(can,map,code ^ num);
                is = can.get(code ^ num);
                if(is) {
                    can.put(code,true);
                    return;
                }
            }
        }
        can.put(code,false);
    }
    public void preManager(Map<Integer,Boolean> map,int[] matchsticks,
                           long a,int code,int index){
        if(a==0)
            map.put(code,true);
        for(int i=index;i<matchsticks.length;i++){
            if(a - matchsticks[i]>=0){
                preManager(map,matchsticks,a-matchsticks[i],code | (1<<i),i+1);
            }
        }
    }
    public int numDecodings(String s) {
        //'A' -> 1//'B' -> 2//...//'Z' -> 26//*->any 1~9
        int mod = (int) (Math.pow(10,9)+7);
        long[] dp = new long[s.length()+1];
        dp[0] = 1;
        for(int i=1;i<dp.length;i++){
            char c = s.charAt(i-1);
            if(c=='*'){
                dp[i] = (dp[i-1] * 9) % mod;
            }else if (c>'0'){
                dp[i] = dp[i-1];
            }
            if(i-2>=0){
                char cPrev = s.charAt(i-2);
                long result = (dp[i] + dp[i - 2]) % mod;
                if(cPrev=='*'){
                    if(c=='*'){
                        dp[i] =(dp[i] + (dp[i-2] * 15)) % mod;
                    }else{
                        if(c<='6')
                            dp[i] =(dp[i] +  (dp[i-2] * 2) ) % mod;
                        else
                            dp[i] = result;
                    }
                }else if(cPrev>'0'){
                    if(c=='*'){
                        if(cPrev=='1'){
                            dp[i] =(dp[i] + (dp[i-2] * 9)) % mod;
                        }
                        if(cPrev=='2'){
                            dp[i] =(dp[i] + (dp[i-2] * 6)) % mod;
                        }
                    }else{
                        if((cPrev-'0') * 10 + c-'0'<=26)
                            dp[i] = result;
                    }
                }
            }
        }
        return (int) dp[dp.length-1];
    }
    public int maxProduct(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for(int i=s.length()-1;i>=0;i--){
            for(int j=i;j<s.length();j++){
                if(i==j)
                    dp[i][j] = 1;
                else{
                    if(s.charAt(i) == s.charAt(j)){
                        dp[i][j] = i+1<=j-1?dp[i+1][j-1] +2:2;
                    }else{
                        int max = i+1<=j-1?dp[i+1][j-1]:0;
                        for(int k=j-1;k>i;k--){
                            if(s.charAt(k)== s.charAt(i)){
                                max = Math.max(i+1<=k-1?dp[i+1][k-1]+2:2,max);
                                break;
                            }
                        }
                        for(int k=i+1;k<j;k++){
                            if(s.charAt(k) == s.charAt(j)){
                                max = Math.max(k+1<=i-1?dp[k+1][i-1]+2:2,max);
                            }
                        }
                        dp[i][j] = max;
                    }
                }
            }
        }
        int ret = Integer.MIN_VALUE;
        for(int k=0;k<s.length()-1;k++){
            ret = Math.max(ret,dp[0][k] * dp[k+1][s.length()-1]);
        }
        return ret;
    }
    public int pathSum(TreeNode root, int targetSum) {
        int[] count = new int[1];
        dfsPathSum(root,root.val,targetSum,count);
        int left = 0,right = 0;
        if(root.left!=null)
             left = pathSum(root.left,targetSum);
        if(root.right!=null)
             right= pathSum(root.right,targetSum);
        return count[0] + left + right;
    }
    public void dfsPathSum(TreeNode root,int sum ,int target,int[] count){
        if(target==sum)
            count[0]++;
        if(root.left!=null)
        dfsPathSum(root.left,sum+root.left.val,target,count);
        if(root.right!=null)
        dfsPathSum(root.right,sum+ root.right.val,target,count);
    }
    public String largestMultipleOfThree(int[] digits) {

        List<Integer> zero = new ArrayList<>();
        List<Integer> one= new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        for(int e:digits)
        {
            if(e % 3==0)
                zero.add(e);
            else if(e % 3==1)
                one.add(e);
            else
                two.add(e);
        }
        one.sort((e1,e2)-> -Integer.compare(e1,e2));
        two.sort((e1,e2)-> -Integer.compare(e1,e2));
        int indexOne = one.size() / 3 * 3,indexTwo = two.size() /3 *3;
        List<Integer> ret = new ArrayList<>();
        ret.addAll(zero);
        ret.addAll(one.subList(0,indexOne));
        ret.addAll(two.subList(0,indexTwo));
        int remainOne = one.size() - indexOne;
        int remainTwo = two.size() - indexTwo;
        int remain;
        if((remain = Math.min(remainOne,remainTwo))>0){
            ret.addAll(one.subList(indexOne,indexOne+remain));
            ret.addAll(two.subList(indexTwo,indexTwo+remain));
        }
        ret.sort(Integer::compare);
        if(ret.size() != 0 && ret.get(ret.size()-1)==0)
            return "0";
        StringBuilder builder = new StringBuilder();
        for(int i=ret.size()-1;i>=0;i--){
            builder.append(ret.get(i));
        }

        return builder.toString();
    }
    public String shortestSuperstring(String[] words) {
        int[][] l = new int[words.length][words.length];
        for(int i=0;i<words.length;i++){
            for(int j=0;j<words.length;j++){
                if(i==j){
                    l[i][j] = Integer.MAX_VALUE;
                }else{
                    for(int k=0;k<Math.min(words[i].length(),words[j].length());k++){
                        if(words[i].substring(words[i].length()-1 - k).equals(words[j].substring(0,k+1))){
                            l[i][j] = Math.max(l[i][j],k+1);
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
        for(int i=1;i<1<<N;i++){
            int bit = 1;
            for(int b = 0;bit < i;b++){
                bit = bit<<b;
                if((bit | i) == i){
                    int num = bit ^ i;
                    for(int k = 0;k<N;k++)
                    {
                        if(mask[num][k] !=0){
                            mask[i][b] = Math.max(mask[i][b],mask[num][k] + l[k][b]);
                        }
                    }
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        int max = 0;
        int startIndex = 0;
        for(int i = 0;i<N;i++){
            if(mask[(1<<N)-1][i]>max){
                max = mask[(1<<N)-1][i];
                startIndex = i;
            }
        }
        path.add(startIndex);
        StringBuilder builder = new StringBuilder();
        builder.append(words[path.get(0)]);
        for(int i=1;i<path.size();i++){
            int index = path.get(i);
            int prev = path.get(i-1);
            builder.append(words[index].substring(l[prev][index]));
        }
        return builder.toString();
    }
    public void dfsWordPath(int[][] map,List<Integer> list,List<Integer> path,int sum,int[] min){
        if(list.size()==map.length)
        {
            if(sum >= min[0]){
                min[0] = sum;
                path.clear();
                path.addAll(list);
            }
            return;
        }
        for(int i=0;i<map.length;i++){
            if(list.contains(i))
                continue;
            list.add(i);
            if(list.size()>=2)
            dfsWordPath(map,list,path,sum + map[list.get(list.size()-2)][list.get(list.size()-1)] ,min);
            else
                dfsWordPath(map,list,path,sum,min);
            list.remove(list.size()-1);
        }
    }
}
