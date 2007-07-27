package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A set of triangles. It's an INDEXED triangle set.
 * There are several input sources and the p list
 * contains the position of the data to take from these
 * input sources to make a triangle.
 *
 * Let's take an example. We have two inputs :
 * VERTEX and NORMAL, with offsets 0 and 1.
 * You'd read the p indices list like that :
 * <code>
 * for(int i = 0; i < p.length; i+=2) {
 *      vertex = vertices.get(p[i+0]);
 *      normal = normals.get(p[i+1]);
 * }
 * </code>
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLTriangles {

    public int count = 0;
    public ArrayList<XMLInput> inputs = null;
    public int[] p = null;

}
