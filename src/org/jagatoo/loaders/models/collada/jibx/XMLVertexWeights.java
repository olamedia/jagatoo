package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * The Vertex weights information for Skeletal animation.
 * Child of Skin.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLVertexWeights {
    
    public ArrayList<XMLInput> inputs = null;
    public XMLIntArray vcount = new XMLIntArray();
    public XMLIntArray v = new XMLIntArray();
    
}
