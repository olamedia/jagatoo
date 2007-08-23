package org.jagatoo.loaders.models.collada.datastructs.geometries;


/**
 * A Geometry provider. Basically it can be what
 * instance_geometry or instance_controller is in a COLLADA file,
 * library_visuals_scenes section.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class GeometryProvider {
    
    /** The destination geometry : it will contain the result of the computations of this COLLADAController */
    protected Geometry destinationGeometry;
    
    /** The geometry libraries */
    protected LibraryGeometries libGeoms;

    /**
     * Creates a new COLLADAController
     * @param libGeoms The {@link LibraryGeometries} we need to compute
     * the destination mesh.
     * @param destinationGeometry Geometry is the {@link Geometry} which will
     * be updated with the result of the computations of this
     * {@link GeometryProvider}. It can be null at first and then
     * set by setDestinationMesh()
     */
    public GeometryProvider(LibraryGeometries libGeoms) {
        
        this.libGeoms = libGeoms;
        
    }
    
    /**
     * @param currentTime the frame time in miliseconds
     * @return the destinationGeometry computed from the source mesh
     */
    public abstract Geometry updateDestinationGeometry(long currentTime);

    /**
     * @return the destinationGeometry
     */
    public Geometry getDestinationGeometry() {
        return destinationGeometry;
    }

    /**
     * @param destinationGeometry the destinationGeometry to set
     */
    public void setDestinationMesh(Geometry destinationGeometry) {
        this.destinationGeometry = destinationGeometry;
    }
    
}
