package org.jagatoo.loaders.models.collada.datastructs.animation;

import org.openmali.vecmath.Point3f;

/**
 * A KeyFrame containing Point3fs.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class KeyFramePoint3f extends KeyFrame {
    
    /**
     * Key frame transform values: translation or rotation (in radians)
     */
    public Point3f value;
    
}
