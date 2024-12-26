package com.physicsim.game.utility;

import java.util.Arrays;

public class Matrix {
    private final double[][] myData;
    private final int myRows;
    private final int myCols;

    /**
     * Constructor to initialize the matrix with specific dimensions.
     *
     * @param theRows the number of rows
     * @param theCols the number of columns
     */
    public Matrix(final int theRows, final int theCols) {
        myRows = theRows;
        myCols = theCols;
        myData = new double[theRows][theCols];
    }

    /**
     * Constructor to initialize the matrix with a 2D array.
     *
     * @param theData the 2D array
     */
    public Matrix(final double[][] theData) {
        myRows = theData.length;
        myCols = theData[0].length;
        myData = new double[myRows][myCols];
        
        for (int i = 0; i < myRows; i++) {
            if (theData[i].length != myCols) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            System.arraycopy(theData[i], 0, myData[i], 0, myCols);
        }
    }

    // Constructor to create a matrix from a Vector3
    public Matrix(final Vector3 theV, final boolean isColumnVector) {
        if (isColumnVector) {
            myRows = 3;
            myCols = 1;
            myData = new double[][]{{theV.getX()}, {theV.getY()}, {theV.getZ()}};
        } else {
            myRows = 1;
            myCols = 3;
            myData = new double[][]{{theV.getX(), theV.getY(), theV.getZ()}};
        }
    }

    /**
     * Method to add two matrices. Must have same dimensions.
     *
     * @param theOther the other matrix
     * @return the sum of the 2 matrices
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public Matrix add(final Matrix theOther) {
        if (myRows != theOther.myRows || myCols != theOther.myCols) {
            throw new IllegalArgumentException("Matrix dimensions must match for addition");
        }

        double[][] result = new double[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                result[i][j] = myData[i][j] + theOther.myData[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Method to subtract two matrices. Must have same dimensions.
     *
     * @param theOther the other matrix
     * @return the difference of the 2 matrices
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public Matrix subtract(final Matrix theOther) {
        if (myRows != theOther.myRows || myCols != theOther.myCols) {
            throw new IllegalArgumentException("Matrix dimensions must match for subtraction");
        }

        double[][] result = new double[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                result[i][j] = myData[i][j] - theOther.myData[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Method to multiply two matrices. Dimensions must be correct.
     * The number of columns of 1 matrix must equal the number rows of the other.
     * @param theOther the other matrix
     * @return the product of the 2 matrices
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public Matrix multiply(final Matrix theOther) {
        if (myCols != theOther.myRows) {
            throw new IllegalArgumentException("Matrix dimensions must align for multiplication");
        }

        double[][] result = new double[myRows][theOther.myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < theOther.myCols; j++) {
                for (int k = 0; k < myCols; k++) {
                    result[i][j] += myData[i][k] * theOther.myData[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    /**
     * Method to get the transpose of this matrix.
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        double[][] result = new double[myCols][myRows];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                result[j][i] = myData[i][j];
            }
        }
        return new Matrix(result);
    }

    // Inverse of a diagonal matrix
    public Matrix inverseDiagonal() {
        if (myRows != myCols) {
            throw new UnsupportedOperationException("Only square matrices can have an inverse");
        }

        double[][] result = new double[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            if (myData[i][i] == 0) {
                throw new ArithmeticException("Diagonal element is zero, matrix is not invertible");
            }
            result[i][i] = 1.0 / myData[i][i];
        }
        return new Matrix(result);
    }

    // Getter for matrix data
    public double[][] getData() {
        return myData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : myData) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
}