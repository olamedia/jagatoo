package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;

/**
 * A COLLADA Controller.
 * It's what used, for example, to compute frame animations or skeletal animations.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @see COLLADASkeletalController
 */
public abstract class COLLADAController extends COLLADAGeometryProvider {

    /**
     * Creates a new COLLADA Controller
     * @param libGeoms
     */
    public COLLADAController(COLLADALibraryGeometries libGeoms) {
        
        super(libGeoms);
        
    }
    
}
