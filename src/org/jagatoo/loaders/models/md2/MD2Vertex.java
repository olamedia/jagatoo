/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.md2;

import java.io.IOException;
import java.io.DataInputStream;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Vector3f;

/**
 * A vertex stored with an MD2 file
 * 
 * @author Kevin Glass
 */
public class MD2Vertex
{
    /** The vertex data */
    private Point3f vertex = new Point3f();
    /** The normal - can't use this off hand? */
    private short lightNormal;
    
    /** 
     * Creates new MD2Vertex 
     * 
     * @param in The input stream to read this vertex from
     */
    public MD2Vertex( DataInputStream in, float[] scale, float[] translate ) throws IOException
    {        
        vertex.x = ( in.read() * scale[ 0 ] ) + translate[ 0 ];
        vertex.y = ( in.read() * scale[ 1 ] ) + translate[ 1 ];
        vertex.z = ( in.read() * scale[ 2 ] ) + translate[ 2 ];
        
        lightNormal = (short)in.read();
    }
    
    /**
     * Retrieves the vertex coordinates.
     * 
     * @return The array of values
     */
    public Point3f getFloats()
    {
        return( vertex );
    }
    
    /**
     * Retrieves the vertex normal.
     * 
     * @return The array of values
     */
    public Vector3f getNormal()
    {
        return( MD2Normals.data[ lightNormal ] );
    }
}
