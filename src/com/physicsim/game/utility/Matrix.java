package com.physicsim.game.utility;

public class Matrix {
    private final int myRows;
    private final int myCols;
    private final double[][] myMatrix;

    /**
     * Constructor to initialize the matrix with specific dimensions.
     *
     * @param theRows the number of rows
     * @param theCols the number of columns
     */
    public Matrix(final int theRows, final int theCols) {
        myRows = theRows;
        myCols = theCols;
        myMatrix = new double[theRows][theCols];
    }

    /**
     * Constructor to initialize a square diagonal matrix of a value. Non-diagonals are 0.
     * @param theSize the size of the square matrix
     * @param theDiagonalValue the value
     */
    public Matrix(final int theSize, final double theDiagonalValue) {
        this(theSize, theSize);

        for (int i = 0; i < theSize; i++) {
            myMatrix[i][i] = theDiagonalValue;
        }
    }

    /**
     * Constructor to initialize the matrix with a 2D array.
     *
     * @param theData the 2D array
     */
    public Matrix(final double[][] theData) {
        this(theData.length, theData[0].length);

        for (int i = 0; i < myRows; i++) {
            System.arraycopy(theData[i], 0, myMatrix[i], 0, myCols);
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
            throw new IllegalArgumentException("Matrices must have the same dimensions to add.");
        }

        final Matrix sum = new Matrix(myRows, myCols);
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                sum.myMatrix[i][j] = myMatrix[i][j] + theOther.myMatrix[i][j];
            }
        }
        return sum;
    }

    /**
     * Method to multiply two matrices. Dimensions must be correct.
     * The number of columns of 1 matrix must equal the number rows of the other.
     * @param theOther the other matrix
     * @return the product of the 2 matrices
     */
    public Matrix multiply(final Matrix theOther) {
        if (myCols != theOther.myRows) {
            throw new IllegalArgumentException("Matrices have incompatible dimensions for multiplication.");
        }

        final Matrix prod = new Matrix(this.myRows, theOther.myCols);
        for (int i = 0; i < prod.myRows; i++) {
            for (int j = 0; j < prod.myCols; j++) {
                for (int k = 0; k < myCols; k++) {
                    prod.myMatrix[i][j] += myMatrix[i][k] * theOther.myMatrix[k][j];
                }
            }
        }
        return prod;
    }

    /**
     * Method to get the transpose of this matrix.
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        final Matrix result = new Matrix(myCols, myRows);
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                result.myMatrix[j][i] = myMatrix[i][j];
            }
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(myRows * myCols);
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                sb.append(myMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(final Object theObj) {
        if (this == theObj) return true;
        if (theObj == null || getClass() != theObj.getClass()) return false;
        final Matrix matrix = (Matrix) theObj;
        if (myRows != matrix.myRows || myCols != matrix.myCols) return false;

        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                if (Double.compare(matrix.myMatrix[i][j], this.myMatrix[i][j]) != 0) return false;
            }
        }
        return true;
    }

    /**
     * Method to set a value at a specific position.
     *
     * @param theRow the row index
     * @param theCol the column index
     * @param theValue the new value
     * @throws IllegalArgumentException if invalid indices
     */
    public void set(final int theRow, final int theCol, final double theValue) {
        if (theRow >= myRows || theCol >= myCols || theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Invalid matrix indices.");
        }
        myMatrix[theRow][theCol] = theValue;
    }

    /**
     * Method to get a value at a specific position.
     *
     * @param theRow the row index
     * @param theCol the column index
     * @return the value at the position
     * @throws IllegalArgumentException if invalid indices
     */
    public double get(final int theRow, final int theCol) {
        if (theRow >= myRows || theCol >= myCols || theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Invalid matrix indices.");
        }
        return myMatrix[theRow][theCol];
    }
}

