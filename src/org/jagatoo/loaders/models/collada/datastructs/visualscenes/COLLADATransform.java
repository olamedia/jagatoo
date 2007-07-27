package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

/**
 * A transform.
 * Used to specificy the position, orientation, and scale
 * of a COLLADA node. It's abstract cause it can be either
 * separate values or a matrix
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class COLLADATransform {
    
    /**
     * @return a Matrix transform version of this Transform
     * This is required for convenience when implementing scenegraph binding
     */
    public abstract MatrixTransform getMatrixTransform();
    
}
