package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A Mesh describes the vertex/normal/color/UV...
 * contained in a Geometry.
 * Child of Geometry.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLMesh {

    public ArrayList<XMLSource> sources = null;
    public XMLVertices vertices = null;
    public XMLTriangles triangles = null;

}
