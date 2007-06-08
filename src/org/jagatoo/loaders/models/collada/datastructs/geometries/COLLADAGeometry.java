package org.jagatoo.loaders.models.collada.datastructs.geometries;

/**
 * A COLLADA Geometry. For now, only polygons with a constant number of
 * vertex-per-polygons and triangles are supported.
 * 
 * Note that this class is abstract. Interesting ones are
 * COLLADATrianglesGeometry, and COLLADAPolygonsGeometry.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class COLLADAGeometry {
    
    /** The source ID of this COLLADA Geometry */
    private final String id;
    
    /** The name of the COLLADA Geometry */
    private String name;
    
    /**
     * Create a new COLLADAGeometry
     * 
     * @param id
     *            the id it is referenced by
     * @param name
     *            the name of the geometry
     */
    public COLLADAGeometry(String id, String name) {
        
        this.id = id;
        this.name = name;
        
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
}
