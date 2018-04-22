package com.yuriidr.model;

public class Matrix {

    private int[][] matrix;

    public Matrix(int rows, int columns) {
        matrix = new int[rows][columns];
    }

    public Matrix(int[][] resultMatrix) {
        this.matrix = resultMatrix;
    }

    public int getRows() {
        return matrix.length;
    }

    public int getColumns() {
        return matrix[0].length;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
