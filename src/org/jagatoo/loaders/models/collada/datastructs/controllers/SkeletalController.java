package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
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
public class SkeletalController extends Controller implements AnimatableModel {
    
    /** The ID of the source mesh */
    private String sourceMeshId = null;
    
    /** COLLADA Geometry */
    @SuppressWarnings("unused")
    private Geometry sourceMesh;

    /** The skeleton we're using to compute */
    private Skeleton skeleton;
    
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
    public Geometry updateDestinationGeometry(long delta) {
        
        System.out.println(
                (skeleton == null ?
                        "Hey ! We haven't been initialized yet... Damn." :
                        "Know what ? Our skeleton root bone is named is : "+skeleton.getRootBone().getName()
                )
        );
        
        Geometry sourceGeom = libGeoms.getGeometries().get(getSourceMeshId());
        
        if(destinationGeometry == null) {
            destinationGeometry = sourceGeom;
        }
        
        return destinationGeometry;
        
    }

    /**
     * Set the skeleton of this controller
     * @param skeletonUrl
     */
    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public COLLADAAction getAction(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public HashMap<String, COLLADAAction> getActions() {
        // TODO Auto-generated method stub
        return null;
    }

    public ColladaProtoypeModel getPrototypeModel() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasActions() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isLooping() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isPlaying() {
        // TODO Auto-generated method stub
        return false;
    }

    public int numActions() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void pause() {
        // TODO Auto-generated method stub
        
    }

    public void play(COLLADAAction action, boolean loop) {
        // TODO Auto-generated method stub
        
    }

    public void play(String actionId, boolean loop) {
        // TODO Auto-generated method stub
        
    }

    public void resume() {
        // TODO Auto-generated method stub
        
    }
    
}
