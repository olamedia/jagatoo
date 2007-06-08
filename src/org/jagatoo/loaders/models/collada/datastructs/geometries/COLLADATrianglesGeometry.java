package org.jagatoo.loaders.models.collada.datastructs.geometries;

/**
 * COLLADA Geometry which has been loaded from a COLLADA file with
 * the format "triangles".
 * 
 * All data here is public since it may be accessed very frequently,
 * thus it's a good thing that we do not need to access it via
 * getter/setter methods.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADATrianglesGeometry extends COLLADAGeometry {
 
    /** The mesh containing the triangle data. Three elements (eg. 3 vertices) are a triangle. */
    public COLLADAMesh mesh = null;
    
    /**
     * Creates a new COLLADAGeometry
     * @param id {@inheritDoc}
     * @param name {@inheritDoc} 
     */
    public COLLADATrianglesGeometry(String id, String name) {
        
        super(id, name);
        
    }
    
}
