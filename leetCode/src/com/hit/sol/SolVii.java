package com.hit.sol;

import java.util.*;

public class SolVii {
    public static void main(String[] args){
        SolVii sol = new SolVii();
        List<String> list = new ArrayList<>();
        list.toArray();

        String s = "ABC";
        System.out.println(s.toLowerCase());
    }
    public List<String> removeInvalidParentheses(String s) {
        int n = s.length();
        boolean[] valid = new boolean[1<<n];
        valid[0] = true;
        int[] map = new int[26];
        for(int i=1;i<(1<<n);i++){
            int k = 0;
            int bit;
            while(((bit=(1<<k)) & i) != (bit) && bit <= i){
                k++;
            }
            char c;
            if((c=s.charAt(k))=='('){
                int count = 1;
                while(count>0 && (1<<k)<= i){
                    k++;
                    if(((1<<k) & i)==i){
                        bit |= (1<<k);
                        if((c = s.charAt(k))=='('){
                            count++;
                        }else if(c==')'){
                            count--;
                        }
                    }
                }
                if(count==0){
                    valid[i] = valid[i ^ bit];
                }else{
                    valid[i] = false;
                }
            }else if(c==')'){
                valid[i] = valid[i ^ bit];
            }else{
                valid[i] = true;
            }
            if(valid[i]){
               map[Integer.bitCount(i)]++;
            }
        }
        int maxCount = 0;
        for(int i=25;i>=0;i--){
            i--;
            if(map[i]>0){
                maxCount = i;
                break;
            }
        }
        Set<String> ret = new HashSet<>();
        for(int i=1;i<1<<n;i++){
            if(valid[i] && Integer.bitCount(i)==maxCount){
                int k=1;
                StringBuilder builder = new StringBuilder();
                while(k<=i){
                    if((k & i)==k){
                        builder.append(s.charAt(k-1));
                    }
                    k<<=1;
                }
                ret.add(builder.toString());
            }
        }
        return Arrays.asList(ret.toArray(new String[0]));
    }
    public boolean reorderedPowerOf2(int n) {
        int[][] code = new int[32][10];
        int i = 1;
        for(int k=0;k<32 && i>0;k++){
            String encode = String.valueOf(i);
            for(char c:encode.toCharArray()){
                try {
                    code[k][c-'0']++;
                }catch (RuntimeException e){
                    System.out.println(i);
                }

            }
            i<<=1;
        }
        int[] count = new int[10];
        for(char c:String.valueOf(n).toCharArray()){
            count[c-'0']++;
        }
        boolean ret = false;
        for(int k=0;k<32;k++){
            ret = true;
            for(int j=0;j<=9;j++){
                if(count[j]!=code[i][j]){
                    ret = false;
                    break;
                }
            }
            if(ret){
                return ret;
            }
        }
        return false;
    }
}
