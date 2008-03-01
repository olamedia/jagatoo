package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.LibraryAnimations;
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
    private Geometry sourceGeom;

    /** The skeleton we're using to compute */
    private Skeleton skeleton;

    /**
     * Creates a new COLLADASkeletalController
     *
     * @param libGeoms
     *                The {@link LibraryGeometries} we need to get our sourceMesh from
     * @param sourceMeshId
     *                The source mesh ID
     * @param controller
     * 	              The XML Controller instance
     * @param libAnims 
     *                The library of animations to load actions from
     */
    public SkeletalController(LibraryGeometries libGeoms, String sourceMeshId,
            XMLController controller, LibraryAnimations libAnims, Skeleton skel) {

        super(libGeoms, controller);
        this.sourceMeshId = sourceMeshId;
        this.skeleton = skel;
        
        this.sourceGeom = libGeoms.getGeometries().get(sourceMeshId);
    }

    /**
     * @return the sourceMeshId
     */
    public String getSourceMeshId() {
        return sourceMeshId;
    }

    // // // // // // ANIMATION PART - BEGINS// // // // // //

    /**
     * List of animations that exist 
     * for this SkeletalController's skeleton.
     */
    private HashMap<String, COLLADAAction> actions = new HashMap<String, COLLADAAction>();
    
    /**
     * The current action that is being used
     */
    private COLLADAAction currentAction;

    /**
     * For animation looping
     */
    private boolean loop = false;

    /**
     * Last animated time
     */
    private long currentTime = -1;
    
    /**
     * Is the animation playing?
     */
    private boolean playing = false;
    
    /**
     * Tells if have bones been prepared for current action
     */
    private boolean bonesPrepared = false;
    
    /**
     * Iterator through skeleton bones
     */
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
        playing = true;
    }

    @Override
    public Geometry updateDestinationGeometry(long deltaT) {

        // TODO once this method works well, we should optimize all the
        // variables declarations


    	
        if(sourceGeom == null) {

            sourceGeom = libGeoms.getGeometries().get(sourceMeshId);
            
            if(!(sourceGeom instanceof TrianglesGeometry)) {
                throw new Error("Only TrianglesGeometry is supported for now ! Make" +
                            " sure your model is triangulated when exporting from your modeling tool.");
            } else {
                this.destinationGeometry = sourceGeom.copy();
            }

        }
        
    	if(!playing) return this.destinationGeometry;

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
        TrianglesGeometry triMesh = (TrianglesGeometry) sourceGeom;

        //current mesh
        Mesh mesh = triMesh.getMesh();

        //vertices and normals sources
        MeshSources sources = mesh.sources;

        //skin info
        XMLSkin skin = this.getController().skin;
        
        //iterate over vertices
        Influence[] influences;
        for (int i = 0; i < mesh.vertexIndices.length; i++) {
        	//TODO: I have no idea how to fix the fact that "i" must be
        	//divided by 6.  It has to do with the relative array sizes
        	//(see XMLSkin).
            influences = skin.buildInfluencesForVertex( i/6 );

            //check if there is any influence
            if( influences.length > 0 ) {

                //normalize influences
                normalizeInfluences( influences );

                //TODO: use the destinationGeometry

                //transform the normals, only rotation

                //transform the vertex, rotation and translation
                
                //Kukanani:This is a big test, still under construction.
                //TODO:

                for(int j = 0; j < sourceGeom.getMesh().vertexIndices.length; j+=3) {
//                     Point3f vertex = new Point3f(
//                    		 sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j]],
//                    		 sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j+1]],
//                    		 sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j+2]]);
            //         skin.vertexWeights.v.ints;
            //         vertex.add(x, y, z)
            //         System.out.println(vertex);
                }
            }
        }

        return destinationGeometry;

    }

    /**
     * Normalize the influences weights
     */
    private void normalizeInfluences(Influence[] influences) {
        //TODO: not done yet

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
        return actions.get(id);
    }

    public HashMap<String, COLLADAAction> getActions() {
        return actions;
    }

    public ColladaProtoypeModel getPrototypeModel() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasActions() {
        return (actions.values().size() > 0 ? true : false);
    }

    public boolean isLooping() {
        return loop;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int numActions() {
    	return actions.values().size();
    }

    public void pause() {
        playing = false;
    }

    public void play(String actionId, boolean loop) {
        this.play(actions.get(actionId), loop);
    }

    public void resume() {
        playing = true;
    }

}
