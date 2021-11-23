package com.hit.bean;

class NumMatrix {
    int[][] subSum;
    public static void main(String[] args){
        NumMatrix matrix = new NumMatrix(new int[][]{{3,0,1,4,2},{5,6,3,2,1},
                {1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}});
        System.out.println(matrix.sumRegion(2,1,4,3));
    }
    public NumMatrix(int[][] matrix) {
        subSum = new int[matrix.length+1][matrix[0].length+1];
        for(int i=1;i<=matrix.length;i++){
            for(int j=1;j<=matrix[0].length;j++){
                subSum[i][j] = subSum[i-1][j] + subSum[i][j-1] - subSum[i-1][j-1] + matrix[i-1][j-1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return subSum[row2+1][col2+1] - subSum[row2+1][col1] - subSum[row1][col2+1] + subSum[row1][col1];
    }
}