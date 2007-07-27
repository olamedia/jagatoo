package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.jibx.XMLGeometry;

/**
 * COLLADA Polygons Geometry contains geometry loaded from a COLLADA
 * file which has the "polygon" format
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class PolygonsGeometry extends Geometry {

    /** The Polygons in this geometry */
    private Mesh[] polygons = null;

    /**
     * Create a new COLLADA Polygons Geometry
     * @param file TODO
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param polygonCount The number of polygons that should be
     * in that PolygonsGeometry
     * @param geometry the geometry
     */
    public PolygonsGeometry(AssetFolder file, String id, String name, int polygonCount, XMLGeometry geometry) {

        super(file, id, name, geometry);
        polygons = new Mesh[polygonCount];

    }

    /**
     * @return the polygons
     */
    public Mesh[] getPolygons() {
        return polygons;
    }

}
