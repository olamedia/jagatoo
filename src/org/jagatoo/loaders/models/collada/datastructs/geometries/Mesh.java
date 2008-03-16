/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
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

    /**
     * @return a copy of this mesh
     */
    public Mesh copy() {

        Mesh newMesh = new Mesh(sources.copy());
        newMesh.vertexIndices = vertexIndices;
        newMesh.normalIndices = normalIndices;
        newMesh.colorIndices = colorIndices;
        newMesh.uvIndices = uvIndices;
        return null;

    }


}
