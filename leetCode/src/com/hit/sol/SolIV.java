package com.hit.sol;

import com.hit.bean.ListNode;

import java.util.*;
import java.io.*;
public class SolIV {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        SolIV sol = new SolIV();
        String s = "-123213";
        System.out.println(sol.countValidWords("he bought 2 pencils, 3 erasers, and 1  pencil-sharpener."));

    }

    public int balancedStringSplit(String s) {
        int l = 0, r = 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'L')
                l++;
            else
                r++;
            if (l == r) {
                count++;
                l = 0;
                r = 0;
            }
        }
        return count;
    }

    public int maxAbsValExpr(int[] arr1, int[] arr2) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE,
                max3 = Integer.MIN_VALUE, max4 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE,
                min3 = Integer.MAX_VALUE, min4 = Integer.MAX_VALUE;
        for (int i = 0; i < arr1.length; i++) {
            max1 = Math.max(arr1[i] + arr2[i] + i, max1);
            min1 = Math.min(arr1[i] + arr2[i] + i, min1);
            max2 = Math.max(-arr1[i] + arr2[i] + i, max2);
            min2 = Math.min(-arr1[i] + arr2[i] + i, min2);
            max3 = Math.max(arr1[i] - arr2[i] + i, max3);
            min3 = Math.min(arr1[i] - arr2[i] + i, min3);
            max4 = Math.max(-arr1[i] - arr2[i] + i, max4);
            min4 = Math.min(-arr1[i] - arr2[i] + i, min4);
        }
        int max = Math.max(max1 - min1, max2 - min2);
        return Math.max(max, Math.max(max3 - min3, max4 - min4));
    }

    public int countSubstrings(String s) {

        s = processStr(s);
        int[] l = new int[s.length()];
        int c = 0, r = 0;
        for (int i = 1; i < s.length() - 1; i++) {
            int mirror = c - (i - c);
            if (r > i) {
                l[i] = Math.min(r - i, l[mirror]);
            } else {
                l[i] = 0;
            }
            while (i + l[i] + 1 < s.length() &&
                    i - l[i] - 1 >= 0 && s.charAt(i + l[i] + 1) == s.charAt(i - l[i] - 1))
                l[i]++;
            if (i + l[i] > r) {
                c = i;
                r = i + l[i];
            }
        }
        int ret = 0;
        for (int e : l) {
            if (e % 2 == 0)
                ret += e / 2;
            else {
                ret += (e + 1) / 2;
            }
        }
        return ret;
    }

    public String processStr(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray())
            builder.append("#").append(c);
        builder.append('#');
        return builder.toString();
    }

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int sum = 0;//k个不同的项目
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < profits.length; i++)
            list.add(new int[]{capital[i], profits[i]});
        list.sort((e1, e2) -> {
            if (e1[0] == e2[0])
                return e2[1] - e1[1];
            return e1[0] - e2[0];
        });
        List<int[]> profitSort = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < k && i < profits.length; i++) {
            int preIndex = index + 1;
            index = findIndex(list, w);
            if (index == -1)
                return w;

            if (preIndex < index + 1) {
                List<int[]> subList = list.subList(preIndex, index + 1);
                subList.sort(Comparator.comparingInt(e -> e[1]));
                profitSort = mergeProfit(profitSort, subList);
            }
            w += profitSort.get(profitSort.size() - 1)[1];
            profitSort.remove(profitSort.size() - 1);
        }
        return w;
    }

    public List<int[]> mergeProfit(List<int[]> list1, List<int[]> list2) {
        if (list1.isEmpty())
            return new ArrayList<>(list2);
        int i1 = 0, i2 = 0;
        List<int[]> ret = new ArrayList<>();
        while (i1 < list1.size() && i2 < list2.size()) {
            int[] e1 = list1.get(i1);
            int[] e2 = list2.get(i2);
            if (e1[1] <= e2[1]) {
                ret.add(e1);
                i1++;
            } else {
                ret.add(e2);
                i2++;
            }
        }
        ret.addAll(list1.subList(i1, list1.size()));
        ret.addAll(list2.subList(i2, list2.size()));
        return ret;
    }

    public int findIndex(List<int[]> list, int target) {
        int left = -1, right = list.size(), mid = (right + left) / 2;
        while (right - left > 1) {
            int[] e = list.get(mid);
            if (e[0] <= target)
                left = mid;
            else {
                right = mid;
            }
            mid = (left + right) / 2;
        }
        return left;
    }

    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int max1 = maxSubarray(nums, firstLen, secondLen);
        int max2 = maxSubarray(nums, secondLen, firstLen);
        return Math.max(max1, max2);
    }

    public int maxSubarray(int[] nums, int firstLen, int secondLen) {
        int[] sum = new int[nums.length];
        int[] left_first = new int[sum.length - firstLen + 1];
        int[] right_second = new int[sum.length - secondLen + 1];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        left_first[0] = sum[firstLen - 1];
        for (int i = 1; i < left_first.length; i++) {
            left_first[i] = Math.max(left_first[i - 1], sum[i + firstLen - 1] - sum[i - 1]);
        }
        right_second[0] = sum[nums.length - 1] - sum[nums.length - 1 - secondLen];
        for (int i = 1; i < right_second.length; i++) {
            right_second[i] = Math.max(right_second[i - 1], sum[nums.length - 1 - i] - (nums.length - 1 - i - secondLen >= 0 ?
                    sum[nums.length - 1 - i - secondLen] : 0));
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length - secondLen - firstLen + 1; i++) {
            max = Math.max(max, left_first[i] + right_second[nums.length - firstLen - i - secondLen]);
        }
        return max;
    }

    public int longestArithSeqLength(int[] nums) {
        Map<Integer, Integer>[] map = new Map[nums.length];
        for (int i = 0; i < map.length; i++) {
            map[i] = new HashMap<>();
        }
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            Map<Integer, Integer> e = map[i];
            for (int k = i - 1; k >= 0; k--) {
                if (map[k].get(nums[i] - nums[k]) == null) {
                    map[i].putIfAbsent(nums[i] - nums[k], 2);
                } else {
                    map[i].put(nums[i] - nums[k],
                            Math.max(map[k].get(nums[i] - nums[k]) + 1,
                                    map[i].getOrDefault(nums[i] - nums[k], 0)));
                }
                max = Math.max(max, e.get(nums[i] - nums[k]));
            }
        }
        return max;
    }

    public List<Integer> diffWaysToCompute(String expression) {
        //有效的运算符包括加减乘
        Map<String, List<Integer>> map = new HashMap<>();
        dfsCompute(map, expression);
        List<Integer> set = map.get(expression);
        return new ArrayList<>(set);
    }

    public void dfsCompute(Map<String, List<Integer>> map, String expression) {
        if (map.containsKey(expression))
            return;
        try {
            int num = Integer.parseInt(expression);
            List<Integer> set = new ArrayList<>();
            set.add(num);
            map.put(expression, set);
        } catch (Exception e) {
            int i = 0;
            List<Integer> set = new ArrayList<>();

            while (i < expression.length()) {
                while (i < expression.length() && expression.charAt(i) >= '0' && expression.charAt(i) < '9')
                    i++;
                if (i == expression.length())
                    break;
                dfsCompute(map, expression.substring(0, i));
                dfsCompute(map, expression.substring(i + 1));
                List<Integer> set1 = new ArrayList<>(map.get(expression.substring(0, i)));
                List<Integer> set2 = new ArrayList<>(map.get(expression.substring(i + 1)));
                for (int e1 : set1) {
                    for (int e2 : set2) {
                        if (expression.charAt(i) == '-')
                            set.add(e1 - e2);
                        else if (expression.charAt(i) == '+')
                            set.add(e1 + e2);
                        else
                            set.add(e1 * e2);
                    }
                }
                i++;
            }
            map.put(expression, set);
        }
    }

    public int chalkReplacer(int[] chalk, int k) {
        long[] sum = new long[chalk.length];
        sum[0] = chalk[0];
        for (int i = 1; i < chalk.length; i++) {
            sum[i] = sum[i - 1] + chalk[i];
        }
        long remain = k % sum[sum.length - 1];
        return findIndex(sum, remain);
    }

    public int findIndex(long[] sum, long target) {
        int left = -1, right = sum.length, mid = (right + left) / 2;
        while (right - left > 1) {
            if (sum[mid] > target) {
                right = mid;
            } else {
                left = mid;
            }
            mid = (right + left) / 2;
        }
        return right;
    }

    public int combinationSum4(int[] nums, int target) {
        int[][] dp = new int[nums.length + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = 1;
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = dp[i - 1][j] + (j - nums[i - 1] >= 0 ? dp[i][j - nums[i - 1]] : 0);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    public boolean canWinNim(int n) {
        if (n <= 3)
            return true;
        int remain = n % 4;
        return remain != 0;
    }

    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        List<List<Integer>> rMap = new ArrayList<>();
        List<List<Integer>> bMap = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rMap.add(new LinkedList<>());
            bMap.add(new LinkedList<>());
        }
        for (int[] e : red_edges)
            rMap.get(e[0]).add(e[1]);
        for (int[] e : blue_edges)
            bMap.get(e[0]).add(e[1]);
        Set<Integer> rVisited = new HashSet<>();
        Set<Integer> bVisited = new HashSet<>();
        rVisited.add(0);
        bVisited.add(0);
        Deque<Integer> rNode = new ArrayDeque<>();
        Deque<Integer> bNode = new ArrayDeque<>();
        rNode.add(0);
        bNode.add(0);
        int step = 0;
        int[] rDist = new int[n];
        int[] bDist = new int[n];
        Arrays.fill(rDist, -1);
        Arrays.fill(bDist, -1);
        while (!rNode.isEmpty() || !bNode.isEmpty()) {
            int rSize = rNode.size();
            int bSize = bNode.size();
            step++;
            for (int i = 0; i < rSize; i++) {
                int e = rNode.remove();
                for (int next : bMap.get(e)) {
                    if (!bVisited.contains(next)) {
                        bDist[next] = step;
                        bVisited.add(next);
                        bNode.add(next);
                    }
                }
            }
            for (int i = 0; i < bSize; i++) {
                int e = bNode.remove();
                for (int next : rMap.get(e)) {
                    if (!rVisited.contains(next)) {
                        rDist[next] = step;
                        rVisited.add(next);
                        rNode.add(next);
                    }
                }
            }
        }
        int[] ret = new int[n];
        for (int i = 1; i < n; i++) {
            if (rDist[i] == -1)
                ret[i] = bDist[i];
            else if (bDist[i] == -1)
                ret[i] = rDist[i];
            else
                ret[i] = Math.min(rDist[i], bDist[i]);
        }
        return ret;
    }

    public int minSteps(int n) {
        List<Integer> list = new LinkedList<>();
        dfsDiv(n, list);
        int ret = 0;
        for (int e : list)
            ret += e;
        return ret;
    }

    public void dfsDiv(int n, List<Integer> list) {
        boolean div = false;
        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0) {
                dfsDiv(n / i, list);
                dfsDiv(i, list);
                div = true;
                break;
            }
        }
        if (!div)
            list.add(n);
    }

    public int finalValueAfterOperations(String[] operations) {
        int sum = 0;
        for (String e : operations) {
            char c = e.charAt(0);
            if (c == '+')
                sum++;
            else if (c == '-')
                sum--;
            else {
                char c1 = e.charAt(1);
                if (c1 == '+')
                    sum++;
                else
                    sum--;
            }
        }
        return sum;
    }

    public int sumOfBeauties(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = nums[0];
        min[0] = nums[nums.length - 1];
        for (int i = 1; i < nums.length; i++) {
            max[i] = Math.max(nums[i], max[i - 1]);
            min[i] = Math.min(nums[nums.length - 1 - i], min[i - 1]);
        }
        int ret = 0;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > max[i - 1] && nums[i] < min[nums.length - 2 - i])
                ret += 2;
            else if (nums[i] > nums[i - 1] && nums[i] < nums[i + 1])
                ret += 1;
        }
        return ret;
    }

    public int findNumberOfLIS(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int e : nums) {
            if (list.isEmpty() || e > list.get(list.size() - 1))
                list.add(e);
            else {
                insert(e, list);
            }
        }
        return list.size();
    }

    public void insert(int target, List<Integer> list) {
        int left = -1, right = list.size(), mid = (left + right) / 2;
        while (right - left > 1) {
            if (list.get(mid) >= target)
                right = mid;
            else
                left = mid;
            mid = (left + right) / 2;
        }
        list.set(right, target);
    }

    public int findNumberOfLISII(int[] nums) {
        int[] ret = new int[nums.length];
        int[] count = new int[nums.length];
        if (nums.length == 1)
            return 1;
        ret[0] = 1;
        count[0] = 1;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            ret[i] = 1;
            for (int k = i - 1; k >= 0; k--) {
                if (nums[i] > nums[k])
                    ret[i] = Math.max(ret[k] + 1, ret[i]);
            }
            if (ret[i] == 1)
                count[i] = 1;
            else {
                for (int k = i - 1; k >= 0; k--) {
                    if (ret[k] == ret[i] - 1 && nums[i] > nums[k]) {
                        count[i] += count[k];
                    }
                }
            }
            max = Math.max(ret[i], max);
        }
        if (max == 1)
            return nums.length;
        int sum = 0;
        for (int i = 0; i < ret.length; i++) {
            if (ret[i] == max)
                sum += count[i];
        }
        return sum;
    }

    public int lengthOfLastWord(String s) {
        s = s.trim();
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) != ' ') {
            i--;
        }
        return s.length() - 1 - i;
    }

    public int[] rearrangeBarcodes(int[] barcodes) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int e : barcodes) {
            map.put(e, map.getOrDefault(e, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((e1, e2) -> -Integer.compare(e1.getValue(), e2.getValue()));
        int[] ret = new int[barcodes.length];
        int i = 0;
        int k = 0;
        while (i < ret.length) {
            Map.Entry<Integer, Integer> entry = list.get(k);
            int size = entry.getValue();
            int key = entry.getKey();
            while (i < ret.length && size > 0) {
                ret[i] = key;
                size--;
                i += 2;
            }
            if (size == 0)
                k++;
            map.put(key, size);
        }
        i = 1;
        k = 0;
        while (i < ret.length) {
            Map.Entry<Integer, Integer> entry = list.get(k);
            int size = entry.getValue();
            if (size == 0) {
                k++;
                continue;
            }
            int key = entry.getKey();
            while (i < ret.length && size > 0) {
                ret[i] = key;
                size--;
                i += 2;
            }
            map.put(key, size);
        }
        return ret;
    }

    public ListNode[] splitListToParts(ListNode head, int k) {
        int length = 0;
        ListNode temp = head;
        while (temp != null) {
            length++;
            temp = temp.next;
        }
        ListNode[] ret = new ListNode[k];
        int div = length / k;
        int remain = length % k;
        if (div < 1) {
            temp = head;
            for (int i = 0; i < ret.length; i++) {
                ret[i] = temp;

                temp = temp == null ? null : temp.next;
                ret[i].next = null;
            }

            return ret;
        }
        temp = head;
        for (int i = 0; i < ret.length; i++) {
            ret[i] = temp;
            ListNode listNode = null;
            if (i == ret.length - 1)
                break;
            if (remain > 0) {
                for (int p = 0; p < div + 1; p++) {
                    listNode = temp;
                    temp = temp.next;
                }
                remain--;
            } else {
                for (int p = 0; p < div; p++) {
                    listNode = temp;
                    temp = temp.next;
                }
            }
            if (listNode != null)
                listNode.next = null;
        }
        return ret;
    }

    public boolean isPowerOfThree(int n) {
        if (n <= 0)
            return false;
        if (n == 1)
            return true;
        if (n % 3 != 0)
            return false;
        return isPowerOfThree(n / 3);
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        quickDiv(arr, 0, arr.length, k);
        int[] ret = new int[k];
        System.arraycopy(arr, 0, ret, 0, ret.length);
        return ret;
    }

    public void quickDiv(int[] arr, int p, int q, int k) {
        if (q - p <= 1)
            return;
        int index = (int) (Math.random() * (q - p) + p);
        int num = arr[index];
        int start = p - 1;
        int temp = arr[q - 1];
        arr[q - 1] = num;
        arr[index] = temp;
        for (int i = p; i < q - 1; i++) {
            if (arr[i] <= num) {
                start++;
                int t = arr[start];
                arr[start] = arr[i];
                arr[i] = t;
            }
        }
        start++;
        temp = arr[start];
        arr[start] = num;
        arr[q - 1] = temp;

        if (start - p < k) {
            quickDiv(arr, start, q, k - (start - p));
        } else if (start - p > k) {
            quickDiv(arr, p, start, k);
        }
    }

    public String fractionAddition(String expression) {
        int addIndex = expression.lastIndexOf('+');
        int subIndex = expression.lastIndexOf('-');
        int index = Math.max(addIndex, subIndex);
        if (index == -1)
            return expression;
        String temp = fractionAddition(expression.substring(0, index));
        if (Objects.equals(temp, ""))
            return expression.substring(index);
        else {
            String tailResult = expression.substring(index + 1);
            String[] nums = temp.split("/");
            String[] tailNums = tailResult.split("/");
            int[] n1 = new int[2];
            int[] n2 = new int[2];
            n1[0] = Integer.parseInt(nums[0]);
            n1[1] = Integer.parseInt(nums[1]);
            n2[0] = Integer.parseInt(tailNums[0]);
            n2[1] = Integer.parseInt(tailNums[1]);
            int num = addIndex > subIndex ? n1[0] * n2[1] + n2[0] * n1[1] : n1[0] * n2[1] - n2[0] * n1[1];
            int den = n1[1] * n2[1];
            int div = maxDiv(Math.abs(num), Math.abs(den));
            return num / div + "/" + den / div;
        }

    }

    public int maxDiv(int n1, int n2) {
        if (n1 < n2)
            return maxDiv(n2, n1);
        if (n2 == 0)
            return n1;
        return maxDiv(n2, n1 % n2);
    }

    public boolean isRobotBounded(String instructions) {
        int[] start = new int[2];
        int[][] direct = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
        int k = 0;
        for (char c : instructions.toCharArray()) {
            if (c == 'G') {
                start[0] += direct[k][0];
                start[1] += direct[k][1];
            } else if (c == 'L') {
                k = k - 1 >= 0 ? k - 1 : 3;
            } else {
                k = (k + 1) % 4;
            }
        }
        return !(k == 0 && (!(start[0] == 0 && start[1] == 0)));
    }

    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()][word2.length()];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (word1.charAt(i) == word2.charAt(j))
                    dp[i][j] = 1 + (i - 1 >= 0 && j - 1 >= 0 ? dp[i - 1][j - 1] : 0);
                else {
                    if (i - 1 >= 0 && j - 1 >= 0)
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    else if (i - 1 >= 0)
                        dp[i][j] = dp[i - 1][j];
                    else if (j - 1 >= 0)
                        dp[i][j] = dp[i][j - 1];
                    else
                        dp[i][j] = 0;
                }
            }
        }
        int com = dp[dp.length - 1][dp[0].length - 1];
        return word1.length() + word2.length() - 2 * com;
    }

    public int maximumDifference(int[] nums) {
        int ret = Integer.MIN_VALUE;
        int[] min = new int[nums.length];
        int[] max = new int[nums.length];
        min[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = Math.min(min[i - 1], nums[i]);
        }
        max[max.length - 1] = nums[nums.length - 1];
        for (int i = max.length - 2; i >= 0; i--) {
            max[i] = Math.max(nums[i], max[i + 1]);
        }
        for (int i = 0; i < nums.length; i++) {
            ret = Math.max(max[i] - min[i], ret);
        }
        return ret > 0 ? ret : -1;
    }

    public long gridGame(int[][] grid) {
        int n = grid[0].length;
        long[] topPrxSum = new long[n + 1];
        long[] bottomPrxSum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            topPrxSum[i] = topPrxSum[i - 1] + grid[0][i - 1];
            bottomPrxSum[i] = bottomPrxSum[i - 1] + grid[1][i - 1];
        }
        long max = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            max = Math.min(Math.max(topPrxSum[n] - topPrxSum[i],
                    bottomPrxSum[i - 1]), max);
        }
        return max;
    }

    public boolean placeWordInCrossword(char[][] board, String word) {
        int[][] direct = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int length = word.length();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                if (c == '#')
                    continue;
                for (int[] d : direct) {
                    int[] d_ = new int[]{-d[0], -d[1]};
                    if (!(i + d_[0] >= board.length || i + d_[0] < 0 ||
                            j + d_[1] >= board[0].length || j + d_[1] < 0 || board[i + d_[0]][j + d_[1]] == '#'))
                        continue;
                    int k = 0;
                    int i_ = i, j_ = j;
                    while (k < length && i_ < board.length && i_ >= 0 && j_ < board[0].length &&
                            j_ >= 0 && (board[i_][j_] == word.charAt(k) || board[i_][j_] == ' ')) {
                        k++;
                        i_ = i_ + d[0];
                        j_ = j_ + d[1];
                    }
                    if (k == word.length() && (i_ < 0 || i_ >= board.length || j_ < 0 || j_ >= board[0].length ||
                            board[i_][j_] == '#'))
                        return true;
                }
            }
        }
        return false;
    }

    public int scoreOfStudents(String s, int[] answers) {
        Map<String,Set<Integer>> map = new HashMap<>();
        dfsScore(map,s);
        Set<Integer> ans = map.get(s);
        int sum = 0;
        int correct =getAnswer(s);
        for (int e:answers){
            if(e == correct)
                sum +=5;
            else if(ans.contains(e)){
                sum +=2;
            }
        }
        return sum;
    }
    public int getAnswer(String s){
        String[] sub = s.split("\\+");
        int ret = 0;
        for(String e:sub){
            if(!e.contains("*")) {
                int result = Integer.parseInt(e);
                ret += result;
            }else {
                String[] subs = e.split("\\*");
                int mul = 1;
                for(String num:subs){
                    mul *= Integer.parseInt(num);
                }
                ret += mul;
            }
        }
        return ret;
    }
    public void dfsScore(Map<String, Set<Integer>> map, String expression) {
        if (map.containsKey(expression))
            return;
        try {
            int num = Integer.parseInt(expression);
            Set<Integer> set = new HashSet<>();
            set.add(num);
            map.put(expression, set);
        } catch (Exception e) {
            int i = 0;
            Set<Integer> set = new HashSet<>();

            while (i < expression.length()) {
                while (i < expression.length() && expression.charAt(i) >= '0' && expression.charAt(i) < '9')
                    i++;
                if (i == expression.length())
                    break;
                dfsScore(map, expression.substring(0, i));
                dfsScore(map, expression.substring(i + 1));
                Set<Integer> set1 = new HashSet<>(map.get(expression.substring(0, i)));
                Set<Integer> set2 = new HashSet<>(map.get(expression.substring(i + 1)));
                for (int e1 : set1) {
                    for (int e2 : set2) {

                        if (expression.charAt(i) == '+')
                            set.add(e1 + e2);
                        else
                            set.add(e1 * e2);
                    }
                }
                i++;
            }
            map.put(expression, set);
        }
    }
    public boolean winnerOfGame(String colors) {
        Queue<Integer> A = new PriorityQueue<>((e1,e2)->-Integer.compare(e1,e2));
        Queue<Integer> B = new PriorityQueue<>((e1,e2)->-Integer.compare(e1,e2));
        int i=0;
        int size = colors.length();
        while(i<size){
            int num = 0;
            if(colors.charAt(i)=='A'){
                while (i<size && colors.charAt(i)=='A'){
                    num ++;
                    i++;
                }
                A.add(num);
            }else{
                while (i<size && colors.charAt(i)=='B'){
                    num++;
                    i++;
                }
                B.add(num);
            }
        }
        while(!A.isEmpty() && A.peek()>2 && !B.isEmpty() && B.peek()>2){
            int a = A.remove();
            int b = B.remove();
            A.add(a-1);
            B.add(b-1);
        }
        if(A.isEmpty() || A.peek() <= 2){
            return false;
        }
        return true;
    }
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n = patience.length;
        int[][] map = new int[n][n];
        for(int[] e:edges){
            map[e[0]-1][e[1]-1] = 1;
            map[e[1]-1][e[0]-1] = 1;
        }
        int[] time = new int[n];
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        visited.add(0);
        queue.add(0);
        int tau = 0;
        while(!queue.isEmpty()){
            tau++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                int top = queue.remove();
                for(int k=0;k<n;k++){
                    if(map[top][k]==1 && !visited.contains(k)){
                        queue.add(k);
                        visited.add(k);
                        time[k] = tau;
                    }
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i=1;i<n;i++){
            int count = 2 * time[i] / patience[i];
            if(time[i] % patience[i]==0)
                count--;
            max= Math.max(count * patience[i] + 2 * time[i],max);
        }
        return max;
    }
    public int movingCount(int m, int n, int k) {
        int[][] visited = new int[m][n];
        boolean[][] valid = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0,0});
        int[][] directs  =new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        valid[0][0] = true;
        visited[0][0] = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                int[] top = queue.poll();
                for(int[] d:directs){
                    int nextI = top[0] + d[0],nextJ = top[1] +d[1];
                    if(isValid(nextI,nextJ,k,m,n) && visited[nextI][nextJ]==0){
                        visited[nextI][nextJ] = 1;
                        valid[nextI][nextJ] = true;
                        queue.add(new int[]{nextI,nextJ});
                    }
                }
            }
        }
        int ret =0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(valid[i][j]){
                    ret++;
                }
            }
        }
        return ret;
    }
    public boolean isValid(int i,int j,int k,int m,int n){
        String s1 = String.valueOf(i);
        String s2 = String.valueOf(j);
        int sum =0;
        for(char c:s1.toCharArray()){
            sum += (c - '0');
        }
        for(char c:s2.toCharArray()){
            sum += ( c-'0');
        }
        return sum<=k && i>=0 && j>=0 && i<m && j<n;
    }
    public int cuttingRope(int n) {
        if(n <=3){
            return n-1;
        }
        if(n==4)
            return 4;
        int mod = (int) (Math.pow(10,9) +7);
        int count = n / 3;
        int remain = n % 3;
        int mul = 1;
        if(remain == 1) {
            count--;
            remain = 4;
        }
        for(int i=0;i<count;i++){
            mul = mod(mul,3,mod);
        }
        return mod(mul,remain,mod);
    }
    public int mod(int a,int b,int mod){
        if(b==0)
            return a;
        return ((a % mod) * b) % mod;
    }
    public int[] getMaxMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int max = Integer.MIN_VALUE;
        int[] ret = new int[4];
        for(int i=0;i<m;i++){
            int[] subArray = new int[n];
            for(int j=i;j<m;j++){
                for(int k=0;k<n;k++){
                    subArray[k] += matrix[j][k];
                }
                int[] t = new int[2];
                int temp = subArrayMax(subArray,t);
                if(max < temp){
                    ret[0] = i;ret[1] = t[0];
                    ret[2] = j;ret[3] = t[1];
                    max = temp;
                }
            }
        }
        return ret;
    }
    public int subArrayMax(int[] nums,int[] t){
        int max;
        int[] dp = new int[nums.length];
        int[] parent = new int[nums.length];
        dp[0] = nums[0];
        max = dp[0];
        for(int i=1;i<nums.length;i++){
            if(dp[i-1]>0){
                dp[i] = dp[i-1] + nums[i];
                parent[i] = -1;
            }else{
                dp[i] = nums[i];
                parent[i] = 0;
            }
            max = Math.max(dp[i],max);
        }
        for(int i=0;i<dp.length;i++){
            if(dp[i] == max){
                int k = i;
                while(parent[k]==-1){
                    k--;
                }
                t[0] = k;
                t[1] = i;
                return max;
            }
        }
        return -1;
    }
    public int countValidWords(String sentence) {
        String[] subs = sentence.split(" ");
        int ret = 0;
        for(String e:subs){
            e = e.trim();
            if(e.length()==0)
                continue;
            if(isSentence(e)){
                ret ++;
            }
        }
        return ret;
    }
    public boolean isSentence(String e){
        boolean is = true;
        String[] s = e.split("\\-");
        if(s.length>2 || s.length == 0){
            return false;
        }
        if(s.length==1){
            int i = 0;
            char c=' ';
            while(i<s[0].length()){
                if((c = s[0].charAt(i))>='a' && c<='z'){
                    i++;
                    continue;
                }
                break;
            }
            return i==s[0].length()-1 && !(c>='0' && c<='9') || i==s[0].length();
        }else{
            if(s[0].length()==0)
                return false;
            for(char c:s[0].toCharArray()){
                if(!(c>='a' && c<='z'))
                    return false;
            }
            int i = 0;
            char c=' ';
            if(s[1].length()==0)
                return false;
            while(i<s[1].length()){
                if((c = s[1].charAt(i))>='a' && c<='z'){
                    i++;
                    continue;
                }
                break;
            }
            return i==s[1].length()-1 && !(c>='0' && c<='9') || i==s[1].length();
        }
    }
    public int nextBeautifulNumber(int n) {
        String s = String.valueOf(n);
        char c = s.charAt(0);
        Map<Integer,Integer> map = new HashMap<>();

       return 0;

    }
}
