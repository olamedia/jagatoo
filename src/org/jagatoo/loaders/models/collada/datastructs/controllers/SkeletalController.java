package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLController;

/**
 * A Dummy COLLADA Skeletal Controller.
 * It just returns the source mesh.
 *
 * @see GeometryProvider
 * @author Amos Wenger (aka BlueSky)
 */
public class SkeletalController extends Controller {

    /** The ID of the source mesh */
    private String sourceMeshId = null;

    /** COLLADA Geometry */
    @SuppressWarnings("unused")
    private Geometry sourceMesh;

    /**
     * Creates a new COLLADASkeletalController
     * @param libGeoms The {@link LibraryGeometries} we need
     * @param sourceMeshId The source mesh ID
     * @param controller
     */
    public SkeletalController(LibraryGeometries libGeoms, String sourceMeshId, XMLController controller) {

        super(libGeoms, controller);
        this.sourceMeshId = sourceMeshId;
        this.sourceMesh = libGeoms.getGeometries().get(sourceMeshId);

    }

    /**
     * @return the sourceMeshId
     */
    public String getSourceMeshId() {
        return sourceMeshId;
    }

    @Override
    public Geometry updateDestinationGeometry() {

        Geometry sourceGeom = libGeoms.getGeometries().get(getSourceMeshId());

        if(destinationGeometry == null) {
            destinationGeometry = sourceGeom;
        }

        return destinationGeometry;

    }

}
