package org.jagatoo.loaders.models.collada.datastructs.geometries;

import java.util.List;

/**
 * A COLLADA Mesh : it's a collection of vertices and optionnally
 * vertex indices, normals (and indices), colors (and indices),
 * UV coordinates (and indices).
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Mesh {
    
    /** Mesh sources : vertices, normals, colors, UVs. May NOT be null */
    public MeshSources sources = null;
    
    /** Vertex indices. May NOT be null */
    public int[] vertexIndices = null;
    
    /** Normal indices. May be null */
    public int[] normalIndices = null;
    
    /** Color indices. May be null */
    public int[] colorIndices = null;
    
    /** UV coordinate indices. May be null */
    public List <int[]> uvIndices = null;
    
    /**
     * Create a new COLLADA Mesh
     * @param sources The sources which should be used by this mesh.
     */
    public Mesh(MeshSources sources) {
        
        this.sources = sources;
        
    }

    
}
