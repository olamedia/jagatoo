package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.jibx.XMLGeometry;

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
    private final String name;

    /** The file this COLLADAGeometry belongs to */
    private final COLLADAFile file;

    /** The geometry */
    private final XMLGeometry geometry;

    /**
     * Create a new COLLADAGeometry
     * @param file the file this COLLADAGeometry belongs to
     * @param id
     *            the id it is referenced by
     * @param name
     *            the name of the geometry
     * @param geometry the geometry
     */
    public COLLADAGeometry(COLLADAFile file, String id, String name, XMLGeometry geometry) {

        this.file = file;
        this.id = id;
        this.name = name;
        this.geometry = geometry;

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

    /**
     * @return the file
     */
    public COLLADAFile getFile() {
        return file;
    }

    /**
     * @return the geometry
     */
    public XMLGeometry getGeometry() {
        return geometry;
    }

}
