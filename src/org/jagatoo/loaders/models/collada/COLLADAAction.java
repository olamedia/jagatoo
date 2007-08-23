package org.jagatoo.loaders.models.collada;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.jibx.XMLAnimation;

/**
 * A COLLADA "Action", or "Animation" or
 * "Animation clip" or "Animation strip", whatever you
 * call it : it's a "piece of movement" you can play on
 * your model. 
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAAction {
   
    /** The model this action belongs to. It can only be played on this model, no other */
    private final ColladaProtoypeModel model;
    //FIXME I think we don`t need ColladaProtoypeModel any more
    
    
    /**
     * The identifier of this animation. It should be unique in the same model, ex.
     * you cannot have two animations that have the same ID for the same model
     * */
    private final String id;

    /**
     * The list of XMLAnimationS associated with this action.
     */
    private final List<XMLAnimation> animations;
    //FIXME we don't need this too, we have it in the key frames maps
    
    
    /**
     * Key Frames for this action
     */
    private final Map<Bone, List<KeyFrame>> transKeyFrames = new HashMap<Bone, List<KeyFrame>>();
	private final Map<Bone, List<KeyFrame>> rotKeyFrames = new HashMap<Bone, List<KeyFrame>>();
	private final Map<Bone, List<KeyFrame>> scaleKeyFrames = new HashMap<Bone, List<KeyFrame>>();
    
	/**
	 * Skeleton for this action
	 */
	private Skeleton skeleton;
    
    
    /**
     * Creates a new COLLADAAction.
     * @param model
     */
    public COLLADAAction(ColladaProtoypeModel model, String id, List<XMLAnimation> animations) {
        
        this.model = model;
        this.id = id;
        this.animations = Collections.unmodifiableList(animations);
        
    }

    public ColladaProtoypeModel getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public List<XMLAnimation> getAnimations() {
        return animations;
    }

	/**
	 * @return the skeleton
	 */
	public Skeleton getSkeleton() {
		return skeleton;
	}

	/**
	 * @param skeleton the skeleton to set
	 */
	public void setSkeleton(Skeleton skeleton) {
		this.skeleton = skeleton;
	}

	
	/**
	 * Loop through each bone of the skeleton and complete their temp key frames arrays
	 */
	public void prepareBones() {
		for (Bone bone : this.skeleton) {
			bone.transKeyFrames = this.transKeyFrames.get( bone ).toArray(new KeyFrame[0]);
			bone.rotKeyFrames = this.rotKeyFrames.get( bone ).toArray(new KeyFrame[0]);
			bone.scaleKeyFrames = this.scaleKeyFrames.get( bone ).toArray(new KeyFrame[0]);
		}
	}
    
}
