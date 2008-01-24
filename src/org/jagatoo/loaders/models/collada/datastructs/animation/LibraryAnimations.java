package org.jagatoo.loaders.models.collada.datastructs.animation;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.COLLADAAction;

/**
 * This class is the equivalent contains a list of all COLLADAAction's in 
 * this COLLADA file.
 * 
 * @author Kukanani
 */
public class LibraryAnimations {
    
    private final HashMap<String, COLLADAAction> animations;
    
    /**
     * Create a new LibraryAnimations
     */
    public LibraryAnimations() {
        
        animations = new HashMap<String, COLLADAAction>();
        
    }
    
    /**
     * @return the animations in this LibraryAnimations
     */
    public HashMap<String, COLLADAAction> getAnimations() {
        return animations;
    }
    
}
