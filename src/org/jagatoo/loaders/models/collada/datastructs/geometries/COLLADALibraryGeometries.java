package org.jagatoo.loaders.models.collada.datastructs.geometries;

import java.util.HashMap;

/**
 * This class is the equivalent of LibraryGeometries in a
 * COLLADA file. It contains every Geometry and relevant information
 * about it.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryGeometries {

    /** Map of all geoms : key is id, value is the COLLADAGeometry object */
    private final HashMap<String, COLLADAGeometry> geometries;
    
    /**
     * Create a new COLLADALibraryGeometries
     */
    public COLLADALibraryGeometries() {
        
        geometries = new HashMap<String, COLLADAGeometry>();
        
    }

    /**
     * @return the geometries in this COLLADALibraryGeometries
     */
    public HashMap<String, COLLADAGeometry> getGeometries() {
        return geometries;
    }
    
}
