package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.jibx.Geometry;

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
     * @param file TODO
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     */
    public COLLADATrianglesGeometry(COLLADAFile file, String id, String name, Geometry geometry) {

        super(file, id, name, geometry);

    }

}
