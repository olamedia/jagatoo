package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.openmali.vecmath.Matrix4f;

/**
 * A COLLADA Transform using a 4x4 Matrix to represent the transformation.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class MatrixTransform extends COLLADATransform {

    /** The matrix used */
    private final Matrix4f matrix;

    /**
     * Creates a new COLLADAMatrixTransform with an
     * identity matrix.
     *
     */
    public MatrixTransform() {

        this.matrix = new Matrix4f();
        this.matrix.setIdentity();

    }

    /**
     * Creates a new COLLADAMatrixTransform
     * @param floats Floats in the Matrix
     */
    public MatrixTransform(float[] floats) {

        this.matrix = new Matrix4f(floats);

    }


    /**
     * Creates a new COLLADAMatrixTransform
     * @param matrix The Matrix to instanciate from
     */
    public MatrixTransform(Matrix4f matrix) {

        this.matrix = new Matrix4f(matrix);

    }

    /**
     * @return the matrix
     */
    public Matrix4f getMatrix() {

        return matrix;

    }

    @Override
    public MatrixTransform getMatrixTransform() {

        return this;

    }

}
