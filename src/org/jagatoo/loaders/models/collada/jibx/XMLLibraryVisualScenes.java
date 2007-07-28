package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The library of visual scenes in a COLLADA file.
 * Which means, a "repository" of visual scenes
 * that are contained, and can be instanced with
 * a "scene" XML element in the COLLADA file.
 * Child of COLLADA.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryVisualScenes {
    
    private ArrayList<XMLVisualScene> scenesList = null;
    public HashMap<String, XMLVisualScene> scenes = null;
    
    public void readVisualScenes() {
        scenes = new HashMap<String, XMLVisualScene>();
        for (XMLVisualScene scene : scenesList) {
            scenes.put(scene.id, scene);
        }
        scenesList = null;
    }
    
}
