package org.jagatoo.loaders.models.collada.datastructs.effects;

import java.util.HashMap;

/**
 * This class is the equivalent of LibraryEffects in a
 * COLLADA file. It contains every Effect and relevant information
 * about it.
 * 
 * Note : for now only profile_COMMON is supported
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryEffects {

    /** Map of all geoms : key is id of the effect, value is the id of the source mesh */
    private final HashMap<String, Effect> effects;
    
    /**
     * Create a new COLLADALibraryEffects
     */
    public LibraryEffects() {
        
        effects = new HashMap<String, Effect>();
        
    }

    /**
     * @return the effects in this COLLADALibraryEffects
     */
    public HashMap<String, Effect> getEffects() {
        return effects;
    }
    
}
