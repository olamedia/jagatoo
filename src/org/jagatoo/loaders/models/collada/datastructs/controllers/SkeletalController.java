package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;
import java.util.Iterator;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.COLLADALoader;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Mesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.TrianglesGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLController;
import org.jagatoo.loaders.models.collada.jibx.XMLSkin;

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
        System.out.println("The geometry at sourceMeshId, which is "
        		+ sourceMeshId + ", is equal to " + libGeoms.getGeometries().get(sourceMeshId));
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
    
    private boolean bonesPrepared = false;
    
    private Iterable<Bone> boneIt = this.skeleton;

    /**
     * Starts playing the selected animation
     */
    public void play(COLLADAAction action, boolean loop) {

        this.currentAction = action;
        
        // complete the temps array reference in each bone of the skeleton
        currentAction.prepareBones();
        bonesPrepared = true;
        
        // we use the skeleton of the action
        this.skeleton = currentAction.getSkeleton();
        this.loop = loop;
        this.skeleton.resetIterator();
        
        boneIt = this.skeleton;

        currentTime = 0;
    }

    @Override
    public Geometry updateDestinationGeometry(long deltaT) {

        // TODO once this method works well, we should optimize all the
        // variables declarations

        if(sourceMesh == null) {

            sourceMesh = libGeoms.getGeometries().get(sourceMeshId);

            System.out.println("Sourcemesh is a "+sourceMesh.getClass().getName());
            if(!(sourceMesh instanceof TrianglesGeometry)) {
                throw new Error("Only TrianglesGeometry is supported for now ! Try" +
                            " enabling the 'Triangles' option when exportin from your modeling tool");
            } else {
                this.destinationGeometry = sourceMesh.copy();
            }

        }

        if(skeleton == null) {

            System.out
            .println("Hey ! We haven't been initialized yet... Darn.");

        } else {
            System.out
            .println("Our skeleton root bone is named \""
                    + skeleton.getRootBone().getName()+"\"");

            // deltaT to animate
            currentTime += deltaT;

            // Translation : only for the skeleton

            KeyFrameComputer.computeTuple3f(currentTime, skeleton.transKeyFrames, skeleton.relativeTranslation);
            // loop through all bones
            if(currentAction == null) return destinationGeometry;
            if(!bonesPrepared) {
            	currentAction.prepareBones();
                this.skeleton = currentAction.getSkeleton();
            }
            boneIt = this.skeleton;
            for (Bone bone : boneIt) {
                // if there is no keyframes, don't do any transformations
                if (!bone.hasKeyFrames()) {
                	System.out.println("no keyframes!");
                    bone.setNoRelativeMovement();
                    continue;
                }
                System.out.println("Keyframes");
                // Scaling
                KeyFrameComputer.computeTuple3f(currentTime, bone.scaleKeyFrames, bone.relativeScaling);

                // Rotation
                KeyFrameComputer.computeQuaternion4f(currentTime, bone.rotKeyFrames, bone.relativeRotation);

            }

            // update absolutes transitions for all bones
            skeleton.updateAbsolutes();

        }



        /*
         * Now I need to to apply transitions to every vertex. I need the
         * following information and I don't know where 1) The
         * mesh I am animating is 2) The triangles of that mesh are 3) The
         * vertices, 3 for each triangle, are 4) The normals 3 for each triangle
         * are.
         *
         * I suppose there should be something in Geometry. The only place I
         * could find something is Mesh, It has the vertices and normals
         * indices, but not the triangles
         */


        /*
         * Apply the transitions to every vertex using the bone's weights
         */
        TrianglesGeometry triMesh = (TrianglesGeometry) sourceMesh;

        //current mesh
        Mesh mesh = triMesh.getMesh();

        //vertices and normals sources
        MeshSources sources = mesh.sources;

        //skin info
        XMLSkin skin = this.getController().skin;
        
        //iterate over vertices
        Influence[] influences;
        for (int i = 0; i < mesh.vertexIndices.length; i++) {
            influences = skin.buildInfluencesForVertex( i );

            //check if there is any influence
            if( influences.length > 0 ) {

                //normalize influences
                normalizeInfluences( influences );


                /*
                 * To be continued...
                 */

                //use the destinationGeometry

                //transform the normals, only rotation

                //transform the vertex, rotation and translation

            }
        }

        return destinationGeometry;

    }

    /**
     * Normalize the influences weights
     */
    private void normalizeInfluences(Influence[] influences) {
        //TODO not done yet

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
