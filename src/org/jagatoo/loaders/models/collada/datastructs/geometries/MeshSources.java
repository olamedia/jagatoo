package org.jagatoo.loaders.models.collada.datastructs.geometries;

import java.util.List;

/**
 * A COLLADA Mesh : it's a collection of vertices and optionnally
 * vertex indices, normals (and indices), colors (and indices),
 * UV coordinates (and indices).
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class MeshSources {
    
    /** Vertices. Format : X,Y,Z May NOT be null */
    public float[] vertices = null;
    
    /** Normals. Format : X,Y,Z. May be null */
    public float[] normals = null;
    
    /** Colors. Format : R,G,B,A. May be null */
    public float[] colors = null;
    
    /** UV coordinates. 2-dimensional arrays because there can be several texture sets. May be null */
    public List<float[]> uvs = null;
    
    /**
     * Create a new COLLADA Mesh
     */
    public MeshSources() {
        
        // Do nothing, we're just a data-structure, you know :)
        
    }
    
}
