package com.yuriidr.service.impl;

import com.yuriidr.model.Matrix;
import com.yuriidr.service.MatrixOperation;

public class SequenceMatrixOperationImpl implements MatrixOperation {

    @Override
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (!canMultiplyMatrices(matrixA, matrixB)) {
            throw new IllegalArgumentException("Not allowed to multiply matrices with different dimension");
        }

        int[][] resultMatrix = new int[matrixA.getRows()][matrixB.getColumns()];

        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixB.getColumns(); j++) {
                for (int k = 0; k < matrixA.getColumns(); k++) {
                    resultMatrix[i][j] += matrixA.getMatrix()[i][k] * matrixB.getMatrix()[k][j];
                }
            }
        }

        return new Matrix(resultMatrix);
    }

}
