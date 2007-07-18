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
public class LibraryVisualScenes {

    private ArrayList<VisualScene> scenesList = null;
    public HashMap<String, VisualScene> scenes = null;

    public void readVisualScenes() {
        scenes = new HashMap<String, VisualScene>();
        for (VisualScene scene : scenesList) {
            scenes.put(scene.id, scene);
        }
        scenesList = null;
    }

}
