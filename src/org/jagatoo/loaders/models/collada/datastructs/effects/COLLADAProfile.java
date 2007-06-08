package org.jagatoo.loaders.models.collada.datastructs.effects;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.images.COLLADASurface;

/**
 * A COLLADA "profile".
 * 
 * A profile is "a way to achieve a graphic effect".
 * 
 * There are several profile types, e.g. COMMON (static rendering
 * pipeline), GLSL and CG (shader rendering pipeline)
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class COLLADAProfile {
    
    /** Surfaces used in this profile. Key = id, Value = surface */
    public HashMap<String, COLLADASurface> surfaces;
    
    /**
     * Creates a new COLLADA profile
     */
    public COLLADAProfile() {
        
        surfaces = new HashMap<String, COLLADASurface>();
        
    }
    
}
