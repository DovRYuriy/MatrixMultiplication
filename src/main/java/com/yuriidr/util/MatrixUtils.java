package com.yuriidr.util;

import com.yuriidr.model.Matrix;

import java.util.Arrays;
import java.util.Random;

public class MatrixUtils {

    private final static int LOW = 1;
    private final static int HIGH = 100;

    public static void initializeMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            matrix.getMatrix()[i] = new Random().ints(matrix.getColumns(), LOW, HIGH).toArray();
        }
    }

    public static void printMatrix(Matrix matrix) {
        System.out.println(Arrays.deepToString(matrix.getMatrix()));
    }
}
