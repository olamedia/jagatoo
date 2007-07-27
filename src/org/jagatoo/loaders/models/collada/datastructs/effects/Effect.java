package org.jagatoo.loaders.models.collada.datastructs.effects;

import java.util.List;

/**
 * Contains information relative to the effect element in a COLLADA file.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Effect {
    
    /** The id of this effect */
    private final String id;
    
    /** List of all profiles */
    public List<Profile> profiles = null;
    
    /**
     * Creates a new COLLADAEffect
     * @param id The id it should have
     */
    public Effect(String id) {
        
        this.id = id;
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
}
