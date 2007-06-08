package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.openmali.vecmath.Matrix4f;

/**
 * A COLLADA Transform using a 4x4 Matrix to represent the transformation.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAMatrixTransform extends COLLADATransform {
    
    /** The matrix used */
    private final Matrix4f matrix;
    
    /**
     * Creates a new COLLADAMatrixTransform with an
     * identity matrix.
     *
     */
    public COLLADAMatrixTransform() {
        
        this(new float[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 0,
        });
        
    }
    
    /**
     * Creates a new COLLADAMatrixTransform
     * @param floats Floats in the Matrix
     *
     */
    public COLLADAMatrixTransform(float[] floats) {
        
        this.matrix = new Matrix4f(floats);
        
    }

    /**
     * @return the matrix
     */
    public Matrix4f getMatrix() {
        return matrix;
    }

    @Override
    public COLLADAMatrixTransform getMatrixTransform() {
        return this;
    }
    
}
