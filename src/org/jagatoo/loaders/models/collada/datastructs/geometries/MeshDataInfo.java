package org.jagatoo.loaders.models.collada.datastructs.geometries;

import java.util.List;

/**
 * This is all the data needed to load correctly a mesh
 * from COLLADA. Used in COLLADALoader.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class MeshDataInfo {
    
    /** Vertex data offset. */
    public int vertexOffset = -1;
    
    /** Normal data offset. -1 means there is no normal data */
    public int normalOffset = -1;
    
    /** Color data offset. -1 means there is no normal data */
    public int colorOffset = -1;
    
    /** UV data offset. null means there is no normal data. It's a list because we can have several vertex "sets" */
    public List<Integer> uvOffsets = null;
    
    /** The Maximum offset. It should be equal to the bigger offset (vertex, normal, color or uv) + 1 */
    public int maxOffset = -1;
    
}
