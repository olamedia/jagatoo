package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A Mesh describes the vertex/normal/color/UV...
 * contained in a Geometry.
 * Child of Geometry.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class Mesh {

    public ArrayList<Source> sources = null;
    public Vertices vertices = null;
    public Triangles triangles = null;

}
