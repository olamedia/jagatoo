package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.HashMap;

/**
 * Visual scenes in a COLLADA File
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryVisualScenes {
    
    /** Map of all scenes */
    private final HashMap<String, COLLADAScene> scenes;
    
    /**
     * Creates a new COLLADALibraryVisualScenes
     */
    public COLLADALibraryVisualScenes() {
        
        this.scenes = new HashMap<String, COLLADAScene>();
        
    }

    /**
     * @return the scenes
     */
    public HashMap<String, COLLADAScene> getScenes() {
        return scenes;
    }
    
}
