package com.hit.com;

import java.util.ArrayList;
import java.util.List;


public class WangYi {
    public static void main(String[] args){

    }
    /*
    对于一个整型数组，里面任何2个元素相加，小于等于M的组合有多少种；
如果有符合的，输出组合对数；
     */
    public int combine(int[] nums,int target){
        int ret =0;
        for(int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++)
                if(nums[i]+nums[j]<target)
                    ret++;
        }
        return ret;
    }
}
