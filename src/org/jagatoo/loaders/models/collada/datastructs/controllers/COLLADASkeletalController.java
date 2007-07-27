package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLController;

/**
 * A Dummy COLLADA Skeletal Controller.
 * It just returns the source mesh.
 *
 * @see COLLADAGeometryProvider
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADASkeletalController extends COLLADAController {

    /** The ID of the source mesh */
    private String sourceMeshId = null;

    /** COLLADA Geometry */
    @SuppressWarnings("unused")
    private COLLADAGeometry sourceMesh;

    /**
     * Creates a new COLLADASkeletalController
     * @param libGeoms The {@link COLLADALibraryGeometries} we need
     * @param sourceMeshId The source mesh ID
     * @param controller
     */
    public COLLADASkeletalController(COLLADALibraryGeometries libGeoms, String sourceMeshId, XMLController controller) {

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
    public COLLADAGeometry updateDestinationGeometry() {

        COLLADAGeometry sourceGeom = libGeoms.getGeometries().get(getSourceMeshId());

        if(destinationGeometry == null) {
            destinationGeometry = sourceGeom;
        }

        return destinationGeometry;

    }

}
