package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A Skin. It defines how skeletal animation should be computed.
 * Child of Controller.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSkin {

    public String source = null;

    // Here we instanciate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public XMLMatrix4x4 bindShapeMatrix = new XMLMatrix4x4();

    public ArrayList<XMLSource> sources = null;
    public ArrayList<XMLInput> jointsInputs = null;
    public XMLVertexWeights vertexWeights = null;

}
