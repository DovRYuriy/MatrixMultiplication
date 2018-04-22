package com.yuriidr;

import com.yuriidr.model.Matrix;
import com.yuriidr.service.MatrixOperation;
import com.yuriidr.service.impl.ParallelMatrixOperationImpl;
import com.yuriidr.service.impl.SequenceMatrixOperationImpl;

import static com.yuriidr.util.MatrixUtils.initializeMatrix;

public class ApplicationRunner {

    private static final int MATRIX_ROWS = 1000;
    private static final int MATRIX_COLUMNS = 1000;

    public static void main(String[] args) {
        Matrix matrixA = new Matrix(MATRIX_ROWS, MATRIX_COLUMNS);
        Matrix matrixB = new Matrix(MATRIX_COLUMNS, MATRIX_ROWS);

        initializeMatrix(matrixA);
        initializeMatrix(matrixB);

        MatrixOperation matrixOperation = new ParallelMatrixOperationImpl();
        multiplyMatrices(matrixA, matrixB, matrixOperation);

        matrixOperation = new SequenceMatrixOperationImpl();
        multiplyMatrices(matrixA, matrixB, matrixOperation);

    }

    private static Matrix multiplyMatrices(Matrix matrixA, Matrix matrixB, MatrixOperation matrixOperation) {
        long startTime = System.nanoTime();
        Matrix matrixC = matrixOperation.multiply(matrixA, matrixB);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1000000000.0;
        System.out.printf("Time in seconds using %s: %f \n", matrixOperation.getClass().getSimpleName(), seconds);
        return matrixC;
    }


}
