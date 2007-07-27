package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.HashMap;

/**
 * Visual scenes in a COLLADA File
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenes {
    
    /** Map of all scenes */
    private final HashMap<String, Scene> scenes;
    
    /**
     * Creates a new COLLADALibraryVisualScenes
     */
    public LibraryVisualScenes() {
        
        this.scenes = new HashMap<String, Scene>();
        
    }

    /**
     * @return the scenes
     */
    public HashMap<String, Scene> getScenes() {
        return scenes;
    }
    
}
