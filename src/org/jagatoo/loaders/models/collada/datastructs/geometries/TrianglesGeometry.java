package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.jibx.XMLGeometry;

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
public class TrianglesGeometry extends Geometry {

    /**
     * Creates a new COLLADAGeometry.
     *
     * @param file The given AssetFolder to load from
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param geometry the geometry
     */
    public TrianglesGeometry(AssetFolder file, String id, String name, XMLGeometry geometry) {

        super(file, id, name, geometry);

    }

    @Override
    public TrianglesGeometry copy() {

        TrianglesGeometry newGeom = new TrianglesGeometry(this.getFile(), this.getId()+"-copy", this.getName(), this.getGeometry());
        newGeom.setMesh(this.getMesh().copy());

        return newGeom;

    }

}
