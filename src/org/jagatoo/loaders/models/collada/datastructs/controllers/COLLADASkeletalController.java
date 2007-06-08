package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;

/**
 * A Dummy COLLADA Skeletal Controller.
 * It just returns the source mesh.
 * 
 * @see COLLADAGeometryProvider
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADASkeletalController extends COLLADAGeometryProvider {
    
    /** The ID of the source mesh */
    private String sourceMeshId = null;
    
    /**
     * Creates a new COLLADASkeletalController
     * @param libGeoms The {@link COLLADALibraryGeometries} we need
     * @param sourceMeshId The source mesh ID
     */
    public COLLADASkeletalController(COLLADALibraryGeometries libGeoms, String sourceMeshId) {
        
        super(libGeoms);
        this.sourceMeshId = sourceMeshId;  
        
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
