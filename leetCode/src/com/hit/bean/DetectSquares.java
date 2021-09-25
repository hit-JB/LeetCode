package com.hit.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DetectSquares {
    int[][] map = new int[1001][1001];
    public DetectSquares() {

    }

    public void add(int[] point) {
       map[point[0]][point[1]]++;
    }

    public int count(int[] point) {
        int x =point[0],y = point[1];
        int sum = 0;
        for(int i = 0;i<10001;i++){
            if(map[i][y]==0 || i == x)
                continue;
            int delta = Math.abs(x - i);
            if(y + delta<1001 && map[x][y+delta]!=0 && map[i][y+delta]!=0){
                sum += map[i][y] * map[x][y+delta] * map[i][y+delta];
            }
            if(y-delta>=0 && map[x][y-delta]!=0 && map[i][y-delta]!=0){
                sum += map[i][y] * map[x][y-delta] * map[i][y-delta];
            }
        }
        return sum;
    }
}
