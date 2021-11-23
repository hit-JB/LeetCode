package com.hit.sol;

import java.util.*;

public class SortII {
    public static void main(String[] args) {
        SortII sort = new SortII();


        int[][] edges = new int[][]{{1,2}};
        System.out.println(sort.secondMinimum(2,edges,3,2));
    }
    public void quickSort(int[] nums,int p,int q){
        if (p<q){
            int r = partition(nums,p,q);
            quickSort(nums,p,r-1);
            quickSort(nums,r+1,q);
        }
    }//左闭右闭区间
    public int partition(int[] nums,int p,int q){
        int rand = (int) (p + Math.random() * (q-p));
        int temp = nums[rand];
        nums[rand] = nums[q];
        nums[q] = temp;
        int i=p-1;
        for(int j=p;j<q;j++){
            if(nums[j]<nums[q]){
                i++;
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        i++;
        temp = nums[i];
        nums[i] = nums[q];
        nums[q] = temp;
        return i;
    }

    public void heapSort(int[] nums){
        int size = nums.length;
        for(int i=size / 2;i>0;i--){
            createHeap(nums,i,size);
        }
        for(int i=size;i>0;i--){
            int tail = nums[i-1];
            nums[i-1] = nums[0];
            nums[0] = tail;
            createHeap(nums,1,i-1);
        }
    }//下表从1开始
    public void createHeap(int[] nums,int start,int size){
        int peak = nums[start-1];
        int maxIndex = start;
        int left = 2 * start;
        int right = 2 * start+1;
        if(left<=size && nums[left-1]>peak){
            peak = nums[left-1];
            maxIndex = left;
        }
        if(right<=size && nums[right-1]>peak){
            peak = nums[right-1];
            maxIndex = right;
        }
        if(maxIndex==start)
            return;
        nums[maxIndex-1] = nums[start-1];
        nums[start-1] = peak;
        createHeap(nums,maxIndex,size);
    }
    public void bubble(int[] nums){
        int size = nums.length;
        for(int i=size-1;i>=0;i--){
            for(int j=i;j+1<size;j++){
                if(nums[j+1]<nums[j]){
                    int temp = nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
    }
    public void insertSort(int[] nums){
        for(int i=0;i<nums.length;i++){
            int key = nums[i];
            for(int j=i-1;j>=0;j--){
                if(nums[j]>key){
                    nums[j+1] = nums[j];
                    nums[j]=  key;
                }
            }
        }
    }
    public void selectSort(int[] nums){
        for(int i=nums.length-1;i>=0;i--){
            int maxIndex = i;
            for(int j=i;j>=0;j--){
                if(nums[j]>nums[maxIndex]){
                    maxIndex = j;
                }
            }
            int temp = nums[maxIndex];
            nums[maxIndex] = nums[i];
            nums[i] = temp;
        }
    }
    public String manaChe(String s){
        StringBuilder builder = new StringBuilder();
        builder.append("^");
        for(char c:s.toCharArray()){
            builder.append("#").append(c);
        }
        builder.append("#"+"$");
        s = builder.toString();
        int c=0,r=0;
        int size = s.length();
        int[] p = new int[size];
        for(int i=1;i<size-1;i++){
            int mirror = 2 * c-i;
            if(i<r){
                p[i] = Math.min(p[mirror],r - i);
            }else{
                p[i] = 0;
            }
            while(s.charAt(i+1+p[i])==s.charAt(i-p[i]-1)){
                p[i]++;
            }
            if(i + p[i]>r){
                c = i;
                r = i+p[i];
            }
        }
        int maxIndex = 1;
        int maxLength = Integer.MIN_VALUE;
        for(int i=1;i<size-1;i++){
            if(p[i]>p[maxIndex]){
                maxIndex = i;
                maxLength = p[maxIndex];
            }
        }
        return s.substring(maxIndex-maxLength,maxIndex+maxLength+1).replace("#","");
    }//manaChe算法，时间复杂度哦o(n);
    public int threeSumMulti(int[] arr, int target) {
       int mod = (int) (Math.pow(10,9)+7);
       long[] nums = new long[101];
       for(int e:arr)nums[e]++;
       long ret = 0;
       for(int i=100;i>=0;i--){
           int sum = target-i;
           if(nums[i]==0 || sum<0)
               continue;
           int p =0,q=i;
           while(p<=q){
               if(nums[q]==0 || nums[p]==0) {
                   if(nums[q]==0)
                       q--;
                   if(nums[p]==0)
                       p++;
                   continue;
               }
               int temp;
               if((temp = p+q)<sum){
                   p++;
               }else if(temp>sum){
                   q--;
               }else{
                   if(q==i){
                       if(p==q){
                           if(nums[i]>=3)
                           ret = (ret + (nums[i] * (nums[i]-1) * (nums[i]-2)) / 6) % mod;
                       }else{
                           ret =( ret + nums[p] * ((nums[i] * (nums[i]-1))) / 2) % mod;
                       }
                   }else if(p==q){
                       ret = (ret + (nums[p] * (nums[p]-1)) / 2 * nums[i]) % mod;
                   }else{
                       ret = (ret + nums[p] * nums[q] * nums[i]) % mod;
                   }
                   p++;
               }
           }
       }
       return (int) ret;
    }
    public int findLatestStep(int[] arr, int m) {
        int size = arr.length;
        int[] label = new int[size];
        Arrays.fill(label,1);
        if(m==arr.length)
            return size;
        for(int i=size-1;i>=0;i--){
            label[arr[i]-1] = 0;
            int p = arr[i]-2;
            int l = 0;
            while(p>=0 && label[p]==1){
                p--;
                l++;
            }
            if((p==-1 || label[p]==0) && l==m)
                return i;
            p= arr[i];
            l = 0;
            while(p<size && label[p]==1){
                p++;
                l++;
            }
            if((p==size || label[p]==0) && l==m)
                return i;
        }
        return -1;
    }
    public int minNumberOfFrogs(String croakOfFrogs) {
        int ret = 0;
        int temp = 0;
        Map<Character,Integer> map = new HashMap<>();
        map.put('c',0);map.put('r',1);map.put('o',2);map.put('a',3);map.put('k',4);
        int[] count = new int[4];
        for(char c:croakOfFrogs.toCharArray()){
            if(c=='c'){
                temp ++;
                ret = Math.max(ret,temp);
                count[0]++;
            }else if(c=='k'){
                temp --;
                for(int i=0;i<count.length;i++){
                    count[i]--;
                    if(count[i]<0)
                        return -1;
                }
            }else{
                count[map.get(c)]++;
                if(count[map.get(c)-1]<map.get(c)){
                    return -1;
                }
            }
        }
        for(int e:count){
            if(e!=0)
                return -1;
        }
        return ret;
    }
    public List<List<String>> displayTable(List<List<String>> orders) {
        Map<String,Map<String,Integer>> map = new HashMap<>();
        Set<String> menu = new HashSet<>();
        Set<String> table = new HashSet<>();
        for(List<String> e:orders){
            menu.add(e.get(2));
            table.add(e.get(1));
        }
        for(String e:table){
            map.put(e,new HashMap<>());
        }
        for(String e:table){
            Map<String,Integer> m = map.get(e);
            for(String e1:menu){
                m.put(e1,0);
            }
        }
        for(List<String> e:orders){
            String tableNum = e.get(1);
            String foodName = e.get(2);
            Map<String,Integer> order = map.get(tableNum);
            order.put(foodName,order.get(foodName)+1);
        }
        List<String> temp = new ArrayList<>(menu);
        temp.sort(String::compareTo);
        List<String> t = new ArrayList<>();
        t.add("Table");
        t.addAll(temp);
        List<List<String>> ret = new ArrayList<>();
        ret.add(t);
        List<Map.Entry<String,Map<String,Integer>>> entry= new ArrayList<>(map.entrySet());
        entry.sort(Comparator.comparingInt(e-> Integer.parseInt(e.getKey())));
        for(Map.Entry<String,Map<String,Integer>> e: entry){
            List<String> r = new ArrayList<>();
            r.add(String.valueOf(e.getKey()));
            for(String name:t.subList(1,t.size())){
                r.add(String.valueOf(e.getValue().get(name)));
            }
            ret.add(r);
        }
        return ret;
    }
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<Integer>[] map = new ArrayList[n];
        for(int i=0;i<n;i++)
            map[i] = new ArrayList<>();
        for(int[] e:edges){
            map[e[0]-1].add(e[1]-1);
            map[e[1]-1].add(e[0]-1);
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        visited.add(0);
        queue.add(0);
        int step = 1;
        while(!queue.isEmpty()){
            step++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                int top = queue.remove();
                for(int k:map[top]){
                    if(!visited.contains(k)){
                        visited.add(k);
                        queue.add(k);
                    }
                }
            }
            if(queue.contains(n-1)){
                break;
            }
        }
        boolean[] is  = new boolean[n];
        boolean[] prev = new boolean[n];
        is[0] = true;
        for(int i = 2;i<step+2;i++){
            System.arraycopy(is,0,prev,0,n);
            Arrays.fill(is,false);
            for(int j=0;j<n;j++){
                for(int e :map[j]){
                    is[e] = is[e] || prev[j];
                }
            }
        }
        if(is[n-1]){
            step = step+1;
        }else{
            step = step+2;
        }
        int tau = 0;
        for(int i=0;i<step-1;i++){
            int t = tau / change;
            if(t % 2==1){
                tau = (t+1)  * change;
            }
            tau += time;
        }
        return tau;
    }
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n = patience.length;
        List<Integer>[] map = new List[n];
        for(int i=0;i<n;i++){
            map[i] = new ArrayList<>();
        }
        for(int[] e:edges){
            map[e[0]].add(e[1]);
            map[e[1]].add(e[0]);
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        visited.add(0);
        queue.add(0);
        int tau = 0;
        int max = Integer.MIN_VALUE;
        while(!queue.isEmpty()){
            tau++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                int top = queue.remove();
                for(int k:map[top]){
                    if(!visited.contains(k)){
                        queue.add(k);
                        visited.add(k);
                        int count = 2 * tau  / patience[k];
                        if((2 *tau) % patience[k]==0){
                            count--;
                        }
                        max= Math.max(count * patience[k] + 2 * tau,max);
                    }
                }
            }
        }
        return max+1;
    }
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        return 0L;
    }
    public int smallestDistancePair(int[] nums, int k) {
        
        return 0xffffffff;
    }
}
