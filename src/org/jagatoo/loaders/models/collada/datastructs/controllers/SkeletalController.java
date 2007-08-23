package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.Rotations;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLController;
import org.jagatoo.loaders.models.ms3d.utils.RotationUtils;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Quat4f;
import org.openmali.vecmath.util.Interpolation;

/**
 * A Dummy COLLADA Skeletal Controller. It just returns the source mesh.
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
	 * 
	 * @param libGeoms
	 *            The {@link LibraryGeometries} we need
	 * @param sourceMeshId
	 *            The source mesh ID
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

	// //////////ANIMATION PART - BEGINS////////////

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
	private long lastTime = -1;

	/**
	 * Time when the animation began
	 */
	private long startTime = -1;
	
	/**
	 * Aux for speed calculation
	 */
	private Point3f speed = new Point3f();


	/**
	 * Starts playing the selected animation
	 */
	public void play(COLLADAAction action, boolean loop, long currentTime ) {
		this.currentAction = action;
		
		// we use the skeleton of the action
		this.skeleton = action.getSkeleton();
		this.loop = loop;

		// complete the temps array reference in each bone of the skeleton
		action.prepareBones();
		
		startTime = currentTime;
		lastTime = startTime;
	}

	@Override
	public Geometry updateDestinationGeometry(long currentTime) {
		
		//TODO once this method works well, we should optimize all the variables declarations
		

		System.out
				.println((skeleton == null ? "Hey ! We haven't been initialized yet... Damn."
						: "Know what ? Our skeleton root bone is named is : "
								+ skeleton.getRootBone().getName()));
		
		

		//deltaT to animate
		float deltaT = currentTime - lastTime;
		lastTime = currentTime;
		
		//loop through all bones
		for (Bone bone : this.skeleton) {
			
			//if there is no keyframes, don't do any transformations
			if( !bone.hasKeyFrames() ) {
				bone.setNoRelativeMovement();
				continue;
			}
			
			//Translation
			int frame = bone.selectCurrentTransFrame( currentTime - startTime );
			
			//if its at the extremes
			if( frame == 0 ) {
				bone.relativeTranslation.set( bone.transKeyFrames[ frame ].values );
				
			} else if( frame == bone.transKeyFrames.length ) {
				bone.relativeTranslation.set( bone.transKeyFrames[ frame - 1 ].values );
				
			//if its in the middle of two frames
			} else {

				KeyFrame curFrame = bone.transKeyFrames[ frame ];
				KeyFrame prevFrame = bone.transKeyFrames[ frame - 1 ];

				//time distance beetween both frames
				float timeDist = curFrame.time - prevFrame.time;

				//space distance beetween both frames
				speed.set( curFrame.values );
				speed.sub( prevFrame.values );

				//interpolation speed => distance/time
				speed.scale( 1 / timeDist );

				//interpolate translation with speed and the elapsed time beetween the last call
				speed.scale( deltaT );
				bone.relativeTranslation.set( curFrame.values );
				bone.relativeTranslation.add( speed );
			}
			
			
			//Scaling
			//comming soon, need research...
			
			
			//Rotation
			frame = bone.selectCurrentRotFrame(currentTime);

			//if its at the extremes
			if( frame == 0 ) {
				Rotations.toQuaternion( bone.relativeRotation, bone.rotKeyFrames[ frame ].values );

			} else if( frame == bone.rotKeyFrames.length ) {
				Rotations.toQuaternion( bone.relativeRotation,  bone.rotKeyFrames[ frame - 1 ].values );


			//if its in the middle of two frames, use quaternion NLERP operation
			//to calculate a new position
			} else {

				KeyFrame curFrame = bone.rotKeyFrames[ frame ];
				KeyFrame prevFrame = bone.rotKeyFrames[ frame - 1 ];

				//time distance beetween both frames
				float timeDist = curFrame.time - prevFrame.time;

				//interpolate distance with nlerp
				Quat4f quatCur = Rotations.toQuaternion( curFrame.values );
				Quat4f quatPrev = Rotations.toQuaternion( prevFrame.values );
				Quat4f quatSpeed = Interpolation.nlerp(quatCur, quatPrev, timeDist);

				//scale quatSpeed with elapsed time
				quatSpeed.scale( 1 / timeDist );

				//set the relative rotation to the bone
				bone.relativeRotation.set( quatSpeed );
			}
			
		}
		
		//update absolutes transitions for all bones
		skeleton.updateAbsolutes();
		
		
		
		/* Now I need to to apply transitions to every vertex.
		 * I need the following information and I don't know where the hell it's:
		 * 		1) The mesh I am animating
		 * 		2) The triangles of that mesh
		 * 		3) The vertices 3 for each triangle
		 * 		4) The normals 3 for each triangle
		 * 
		 * I suppose there should be something in Geometry.
		 * The only place I could find something is Mesh, It has the vertices and normals indices,
		 * but not the triangles
		 */
		
		
		
		//Old implementation
//		Geometry sourceGeom = libGeoms.getGeometries().get(getSourceMeshId());
//
//		if (destinationGeometry == null) {
//			destinationGeometry = sourceGeom;
//		}

		return destinationGeometry;

	}
	
	
	

	// //////////ANIMATION PART - ENDS////////////

	/**
	 * Set the skeleton of this controller
	 * 
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

	public void play(String actionId, boolean loop, long currentTime) {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}


}
