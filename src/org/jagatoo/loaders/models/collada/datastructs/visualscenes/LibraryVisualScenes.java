package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;

/**
 * Visual scenes in a COLLADA File
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenes {

    /** Map of all scenes */
    private final HashMap<String, Scene> scenes;

    /** Map of all skeletons */
    private final HashMap<String, Skeleton> skeletons;
    
    /**
     * Creates a new COLLADALibraryVisualScenes
     */
    public LibraryVisualScenes() {

        this.scenes = new HashMap<String, Scene>();
        this.skeletons = new HashMap<String, Skeleton>();

    }

    /**
     * @return the scenes
     */
    public HashMap<String, Scene> getScenes() {
        return scenes;
    }

    /**
     * @return the skeletons
     */
    public HashMap<String, Skeleton> getSkeletons() {
        return skeletons;
    }

}
