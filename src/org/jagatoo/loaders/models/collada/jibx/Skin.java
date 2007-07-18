package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A Skin. It defines how skeletal animation should be computed.
 * Child of Controller.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class Skin {

    public String source = null;

    // Here we instanciate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public Matrix4x4 bindShapeMatrix = new Matrix4x4();

    public ArrayList<Source> sources = null;
    public ArrayList<Input> jointsInputs = null;
    public VertexWeights vertexWeights = null;

}
