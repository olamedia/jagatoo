package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A COLLADA animation.
 *
 * Child of LibraryAnimations.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLAnimation {

    public String id = null;
    public String name = null;

    public XMLAsset asset = null;

    public ArrayList<XMLSource> sources = null;
    public ArrayList<XMLSampler> samplers = null;
    public ArrayList<XMLChannel> channels = null;

}
