package org.jagatoo.loaders.models.collada.datastructs.effects;

import java.util.List;

/**
 * Contains information relative to the effect element in a COLLADA file.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAEffect {
    
    /** The id of this effect */
    private final String id;
    
    /** List of all profiles */
    public List<COLLADAProfile> profiles = null;
    
    /**
     * Creates a new COLLADAEffect
     * @param id The id it should have
     */
    public COLLADAEffect(String id) {
        
        this.id = id;
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
}
