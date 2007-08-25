package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLController;

/**
 * A COLLADA Skeletal Controller. It computes mesh
 * deformation from the keyframe information.
 * 
 * @see GeometryProvider
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
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
     * 
     * @param libGeoms
     *                The {@link LibraryGeometries} we need
     * @param sourceMeshId
     *                The source mesh ID
     * @param controller
     */
    public SkeletalController(LibraryGeometries libGeoms, String sourceMeshId,
            XMLController controller) {
        
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
    
    // // // // // // ANIMATION PART - BEGINS// // // // // //
    
    /**
     * The current action that is being used
     */
    @SuppressWarnings("unused")
    private COLLADAAction currentAction;
    
    /**
     * For animation looping
     */
    @SuppressWarnings("unused")
    private boolean loop = false;
    
    /**
     * Last animated time
     */
    private long currentTime = -1;
    
    /**
     * Starts playing the selected animation
     */
    public void play(COLLADAAction action, boolean loop) {
        
        this.currentAction = action;
        
        // we use the skeleton of the action
        this.skeleton = action.getSkeleton();
        this.loop = loop;
        
        // complete the temps array reference in each bone of the skeleton
        action.prepareBones();
        
        currentTime = 0;
        
    }
    
    @Override
    public Geometry updateDestinationGeometry(long deltaT) {
        
        // TODO once this method works well, we should optimize all the
        // variables declarations
        
        System.out
                .println((skeleton == null ? "Hey ! We haven't been initialized yet... Damn."
                        : "Know what ? Our skeleton root bone is named is : "
                                + skeleton.getRootBone().getName()));
        


        // deltaT to animate
        currentTime += deltaT;
        
        // Translation : only for the skeleton
        KeyFrameComputer.computeTuple3f(currentTime, skeleton.transKeyFrames, skeleton.relativeTranslation);
        
        // loop through all bones
        for (Bone bone : this.skeleton) {
            
            // if there is no keyframes, don't do any transformations
            if (!bone.hasKeyFrames()) {
                bone.setNoRelativeMovement();
                continue;
            }
            
            // Scaling
            KeyFrameComputer.computeTuple3f(currentTime, bone.scaleKeyFrames, bone.relativeScaling);
            
            // Rotation
            KeyFrameComputer.computeQuat4f(currentTime, bone.rotKeyFrames, bone.relativeRotation);
            
        }
        
        // update absolutes transitions for all bones
        skeleton.updateAbsolutes();

        /*
         * Now I need to to apply transitions to every vertex. I need the
         * following information and I don't know where the hell it's: 1) The
         * mesh I am animating 2) The triangles of that mesh 3) The vertices 3
         * for each triangle 4) The normals 3 for each triangle
         * 
         * I suppose there should be something in Geometry. The only place I
         * could find something is Mesh, It has the vertices and normals
         * indices, but not the triangles
         */

        return destinationGeometry;
        
    }

    // // // // // // ANIMATION PART - ENDS// // // // // //
    
    /**
     * Set the skeleton of this controller
     * 
     * @param skeleton
     *                The skeleton to use
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
    
    public void play(String actionId, boolean loop) {
        // TODO Auto-generated method stub
        
    }
    
    public void resume() {
        // TODO Auto-generated method stub
        
    }
    
}
