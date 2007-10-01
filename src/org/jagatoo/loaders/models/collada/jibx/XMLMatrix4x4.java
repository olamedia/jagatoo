package org.jagatoo.loaders.models.collada.jibx;

import org.openmali.vecmath2.Matrix4f;

/**
 * A column-major matrix
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLMatrix4x4 {
    
    public Matrix4f matrix4f = null;
    
    /**
     * Instanciate the contained matrix4f and set
     * it to the Identity matrix
     */
    public XMLMatrix4x4() {
        
        this.matrix4f = new Matrix4f();
        this.matrix4f.setIdentity();
        
    }
    
}
