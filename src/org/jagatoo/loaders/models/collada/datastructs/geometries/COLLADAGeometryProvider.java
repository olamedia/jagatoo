package org.jagatoo.loaders.models.collada.datastructs.geometries;


/**
 * A Geometry provider. Basically it can be what
 * instance_geometry or instance_controller is in a COLLADA file,
 * library_visuals_scenes section.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class COLLADAGeometryProvider {
    
    /** The destination geometry : it will contain the result of the computations of this COLLADAController */
    protected COLLADAGeometry destinationGeometry;
    
    /** The geometry libraries */
    protected COLLADALibraryGeometries libGeoms;

    /**
     * Creates a new COLLADAController
     * @param libGeoms The {@link COLLADALibraryGeometries} we need to compute
     * the destination mesh.
     * @param destinationGeometry Geometry is the {@link COLLADAGeometry} which will
     * be updated with the result of the computations of this
     * {@link COLLADAGeometryProvider}. It can be null at first and then
     * set by setDestinationMesh()
     */
    public COLLADAGeometryProvider(COLLADALibraryGeometries libGeoms) {
        
        this.libGeoms = libGeoms;
        
    }
    
    /**
     * @return the destinationGeometry computed from the source mesh
     */
    public abstract COLLADAGeometry updateDestinationGeometry();

    /**
     * @return the destinationGeometry
     */
    public COLLADAGeometry getDestinationGeometry() {
        return destinationGeometry;
    }

    /**
     * @param destinationGeometry the destinationGeometry to set
     */
    public void setDestinationMesh(COLLADAGeometry destinationGeometry) {
        this.destinationGeometry = destinationGeometry;
    }
    
}
