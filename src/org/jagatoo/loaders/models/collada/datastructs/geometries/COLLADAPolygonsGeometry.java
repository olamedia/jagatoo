package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.jibx.XMLGeometry;

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
     * @param file TODO
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param polygonCount The number of polygons that should be
     * in that PolygonsGeometry
     * @param geometry the geometry
     */
    public COLLADAPolygonsGeometry(COLLADAFile file, String id, String name, int polygonCount, XMLGeometry geometry) {

        super(file, id, name, geometry);
        polygons = new COLLADAMesh[polygonCount];

    }

    /**
     * @return the polygons
     */
    public COLLADAMesh[] getPolygons() {
        return polygons;
    }

}
