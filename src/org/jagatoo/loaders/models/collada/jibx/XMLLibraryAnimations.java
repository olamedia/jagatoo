package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A library of animations.
 *
 * Child of COLLADA.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryAnimations {

    /**
     * This field is written by JiBX and then parsed by the
     * readAnimations() method and then the animationMap HashMap
     * is written.
     */
    private ArrayList<XMLAnimation> animationsList = null;

    /**
     * A map of all animations, which is filled by the readAnimations()
     * method just after the animations ArrayList has been written.
     * key = ID
     * value = Animation
     */
    public HashMap<String, XMLAnimation> animations = null;

    /**
     * Called just after animations has been read, fill
     * the animationMap.
     */
    public void readAnimations() {
        animations = new HashMap<String, XMLAnimation>();
        for (XMLAnimation animation : animationsList) {
            animations.put(animation.id, animation);
        }
        animationsList = null;
    }

}
