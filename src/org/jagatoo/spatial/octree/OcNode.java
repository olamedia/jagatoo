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
package org.jagatoo.spatial.octree;

import org.openmali.vecmath.Point3f;

import org.jagatoo.spatial.SpatialHandle;
import org.jagatoo.spatial.VertexContainer;
import org.jagatoo.spatial.bounds.BoundingSphere;

/**
 * <p>A node used to define an object stored in the octree.  This is not optimized for
 * triangle level octrees, as the overhead per node is substantial.</p>
 * <p> </p>
 * 
 * <p>Copyright: Copyright (c) 2000,2001</p>
 * <p>Company: Teseract Software, LLP</p>
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class OcNode<T> extends BoundingSphere implements SpatialHandle<T>
{
    private static final long serialVersionUID = -2666370326314869320L;
    
    private T object;
    protected OcNode next;
    private OcCell parent;
    
    public OcCell getParent()
    {
        return( parent );
    }
    
    public T getObject()
    {
        return( object );
    }
    
    /**
     * Removes the node from its spatial container.
     */
    public void remove()
    {
    }
    
    /**
     * Recompute the bounds for the object and place it in the spatial
     * container in the correct place.  This generally causes a removal and
     * reintegration.
     */
    public void recompute()
    {
    }
    
    public OcNode()
    {
    }
    
    public OcNode( float x, float y, float z, float r, T o )
    {
        super( x, y, z, r );
        
        this.object = o;
    }
    
    /**
     * Builds an OcNode with the specified center, radius and object.
     * 
     * @param c
     * @param r
     * @param o
     */
    public OcNode( Point3f c, float r, T o )
    {
        this( c.x, c.y, c.z, r, o );
    }
    
    /**
     * Builds an OcNode for the specified object.  The bounds are determined
     * using the source interface which allows an optimal sphere to be
     * constructed.
     * 
     * @param o
     * @param source
     */
    public OcNode( T o, VertexContainer source )
    {
        super();
        
        compute( source );
        
        object = o;
    }
}
