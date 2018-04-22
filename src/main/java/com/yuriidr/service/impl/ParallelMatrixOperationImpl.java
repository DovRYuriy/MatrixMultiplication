package com.yuriidr.service.impl;

import com.yuriidr.model.Matrix;
import com.yuriidr.service.MatrixOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelMatrixOperationImpl implements MatrixOperation {

    private static final int NUMBER_OF_THREADS = 2;

    @Override
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (!canMultiplyMatrices(matrixA, matrixB)) {
            throw new IllegalArgumentException("Not allowed to multiply matrices with different dimension");
        }
        int[][] resultMatrix = new int[matrixA.getRows()][matrixB.getColumns()];
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<Future<int[][]>> list = new ArrayList<>();

        int step = calculateStep(matrixA.getRows());
        multiplyParts(matrixA, matrixB, executor, list, step);
        copyResultFromPartsMultiplication(resultMatrix, list, step);

        executor.shutdown();
        return new Matrix(resultMatrix);
    }

    private int calculateStep(int rows) {
        return (rows / NUMBER_OF_THREADS < 1) ? 1 : rows / NUMBER_OF_THREADS;
    }

    private void multiplyParts(Matrix matrixA, Matrix matrixB, ExecutorService executor, List<Future<int[][]>> list, int step) {
        for (int i = 0; i < matrixA.getRows(); i += step) {
            Callable<int[][]> worker = new PartMultiplier(matrixA, matrixB, i, i + step);
            list.add(executor.submit(worker));
        }
    }

    private void copyResultFromPartsMultiplication(int[][] resultMatrix, List<Future<int[][]>> list, int step) {
        int position = 0;
        for (Future<int[][]> future : list) {
            try {
                System.arraycopy(future.get(), position, resultMatrix, position, step);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            position += step;
        }
    }

    private class PartMultiplier implements Callable<int[][]> {

        int[][] matrixA;
        int[][] matrixB;
        int[][] resultMatrix;
        int start;
        int end;

        PartMultiplier(Matrix matrixA, Matrix matrixB, int start, int end) {
            this.matrixA = matrixA.getMatrix();
            this.matrixB = matrixB.getMatrix();
            this.resultMatrix = new int[matrixA.getRows()][matrixB.getColumns()];
            this.start = start;
            this.end = end;
        }

        @Override
        public int[][] call() {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < matrixB.length; j++) {
                    for (int k = 0; k < matrixB[0].length; k++) {
                        resultMatrix[i][k] += matrixA[i][j] * matrixB[j][k];
                    }
                }
            }
            return resultMatrix;
        }
    }
}
