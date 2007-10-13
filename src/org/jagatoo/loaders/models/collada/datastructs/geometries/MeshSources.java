package org.jagatoo.loaders.models.collada.datastructs.geometries;

import java.util.ArrayList;
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

    /**
     * @return a copy of these sources
     */
    public MeshSources copy() {

        MeshSources newMS = new MeshSources();

        newMS.vertices = new float[this.vertices.length];
        System.arraycopy(this.vertices, 0, newMS.vertices, 0, this.vertices.length);

        if(newMS.normals != null) {
            newMS.normals = new float[this.normals.length];
            System.arraycopy(this.normals, 0, newMS.normals, 0, this.normals.length);
        }

        if(newMS.colors != null) {
            newMS.colors = new float[this.colors.length];
            System.arraycopy(this.colors, 0, newMS.colors, 0, this.colors.length);
        }

        if(newMS.uvs != null) {
            newMS.uvs = new ArrayList<float[]>(this.uvs.size());
            for (float[] uv : this.uvs) {
                float[] newUv = new float[uv.length];
                System.arraycopy(newUv, 0, uv, 0, uv.length);
                newMS.uvs.add(newUv);
            }
            System.arraycopy(this.normals, 0, newMS.normals, 0, this.normals.length);
        }

        return null;

    }

}
