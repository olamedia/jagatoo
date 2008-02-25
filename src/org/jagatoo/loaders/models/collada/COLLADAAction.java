package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFramePoint3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;

/**
 * A COLLADA "Action", or "Animation" or "Animation clip" or "Animation strip",
 * whatever you call it : it's a "piece of movement" you can play on your model.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class COLLADAAction {
    
    /**
     * The identifier of this animation. It should be unique in the same model,
     * ex. you cannot have two animations that have the same ID for the same
     * model
     */
    private final String id;
    
    /**
     * Translation key frames : if any, define the movement of the
     * Skeleton itself (e.g. moves the whole model)
     */
    public final List<KeyFramePoint3f> transKeyFrames = new ArrayList<KeyFramePoint3f> ();
    /**
     * Rotation key frames, per bone.
     */
    public final Map<Bone, List<KeyFrameQuat4f>> rotKeyFrames = new HashMap<Bone, List<KeyFrameQuat4f>>();
    /**
     * Scale key frames, per bone.
     */
    public final Map<Bone, List<KeyFramePoint3f>> scaleKeyFrames = new HashMap<Bone, List<KeyFramePoint3f>>();
    
    /**
     * Skeleton for this action
     */
    private Skeleton skeleton;
    
    
    /**
     * Creates a new COLLADAAction.
     * 
     * @param id the id of this COLLADA Action
     */
    public COLLADAAction(String id) {
        
        this.id = id;
        
    }
        
    /**
     * @return the ID String of this COLLADA Action.
     */
    public String getId() {
    
        return id;
    
    }
    
    /**
     * @return the skeleton
     */
    public Skeleton getSkeleton() {
        return skeleton;
    }
    
    /**
     * @param skeleton
     *                the skeleton to set
     */
    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }
    
    
    /**
     * Loop through each bone of the skeleton and complete their temp key frames
     * arrays
     */
    public void prepareBones() {
        skeleton.resetIterator();
        for (Iterator<Bone> iterator = this.skeleton.iterator(); iterator.hasNext();) {
        	Bone bone = iterator.next();
        	System.out.println(rotKeyFrames.get(bone));
            bone.rotKeyFrames = rotKeyFrames.get(bone);
            bone.scaleKeyFrames = scaleKeyFrames.get(bone);
            System.out.println(bone.rotKeyFrames.equals(rotKeyFrames.get(bone)));
        }
    }
    
}
