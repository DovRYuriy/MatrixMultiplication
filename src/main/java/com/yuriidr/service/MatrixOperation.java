package com.yuriidr.service;

import com.yuriidr.model.Matrix;

public interface MatrixOperation {

    Matrix multiply(Matrix a, Matrix b);

    default boolean canMultiplyMatrices(Matrix a, Matrix b) {
        return a.getRows() == b.getColumns();
    }

}
