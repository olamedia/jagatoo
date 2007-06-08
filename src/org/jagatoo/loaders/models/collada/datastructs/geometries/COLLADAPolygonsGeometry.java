package org.jagatoo.loaders.models.collada.datastructs.geometries;

/**
 * COLLADA Polygons Geometry contains geometry loaded from a COLLADA
 * file which has the "polygon" format
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAPolygonsGeometry extends COLLADAGeometry {
    
    /** The Polygons in this geometry */
    private COLLADAMesh[] polygons = null;
    
    /**
     * Create a new COLLADA Polygons Geometry
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param polygonCount The number of polygons that should be
     * in that PolygonsGeometry
     */
    public COLLADAPolygonsGeometry(String id, String name, int polygonCount) {
        
        super(id, name);
        polygons = new COLLADAMesh[polygonCount];
        
    }

    /**
     * @return the polygons
     */
    public COLLADAMesh[] getPolygons() {
        return polygons;
    }
    
}
