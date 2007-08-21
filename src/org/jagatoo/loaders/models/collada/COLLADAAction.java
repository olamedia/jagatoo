package org.jagatoo.loaders.models.collada;

import java.util.Collections;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
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
    
    /**
     * The identifier of this animation. It should be unique in the same model, ex.
     * you cannot have two animations that have the same ID for the same model
     * */
    private final String id;

    /**
     * The list of XMLAnimationS associated with this action.
     */
    private final List<XMLAnimation> animations;
    
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
    
}
