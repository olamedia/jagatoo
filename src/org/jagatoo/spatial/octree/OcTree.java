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
import org.openmali.vecmath.Tuple3f;

import org.jagatoo.spatial.*;

/**
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class OcTree<T>
{
    private class GetWithinSphere implements OcCallback
    {
        private SpatialCallback callback;
        
        public GetWithinSphere( SpatialCallback callback )
        {
            this.callback = callback;
        }
        
        /**
         * Called when the octree traversal picks a node.
         * 
         * @param visibility  NO_CLIP, SOME_CLIP, NOT_VISIBLE
         * @param node The node picked by the OcCuller
         */
        public void hit( Visibility visibility, OcNode node )
        {
            callback.hit( node );
        }
        
        /**
         * Called when the traversal is complete.  This can be used to sort the nodes
         * or perform any additional post processing.
         */
        public void done()
        {
        }
    }
    
    protected int worldSize;
    protected int minCellSize;
    protected long numObjects = 0;
    protected long totalDepth = 0;
    private OcCell<T> root;
    
    public OcTree( int worldSize, int minCellSize )
    {
        this.worldSize = worldSize;
        this.minCellSize = minCellSize;
        root = new OcCell<T>( this, null, 0, 0, 0, 0 );
    }
    
    public SpatialHandle insert( float x, float y, float z, float r, T o )
    {
        OcNode<T> node = new OcNode<T>( x, y, z, r, o );
        root.insert( this, node, 0 );
        
        return( node );
    }
    
    public int getAverageDepth()
    {
        if ( numObjects == 0 )
        {
            return( 0 );
        }
        else
        {
            return( (int)( totalDepth / numObjects ) );
        }
    }
    
    public int getNumObjects()
    {
        return( (int)numObjects );
    }
    
    /**
     * Builds an OcNode with the specified center, radius and object.
     * 
     * @param c
     * @param r
     * @param o
     */
    public SpatialHandle insert( Point3f c, float r, T o )
    {
        OcNode<T> node = new OcNode<T>( c, r, o );
        root.insert( this, node, 0 );
        
        return( node );
    }
    
    /**
     * Builds an OcNode for the specified object.  The bounds are determined
     * using the source interface which allows an optimal sphere to be
     * constructed.
     * 
     * @param o
     * @param source
     */
    public SpatialHandle insert( T o, VertexContainer source )
    {
        OcNode<T> node = new OcNode<T>( o, source );
        root.insert( this, node, 0 );
        
        return( node );
    }
    
    /**
     * Finds all the spatial objectcs which are within the specified sphere.
     * 
     * @param center  Center of the sphere to search
     * @param radius Radius of the sphere to search
     * @param callback User supplied routine to call when we find an object.
     */
    public void findWithinSphere( Tuple3f center, float radius, SpatialCallback callback )
    {
        root.traverse( new OcRadiusCuller( center, radius ), new GetWithinSphere( callback ) );
    }
    
    public void traverse( OcCuller culler, OcCallback callback )
    {
        root.traverse( culler, callback );
    }
    
    public void findWithinSphere( Tuple3f center, float radius, FrustumInterface frustum, SpatialCallback callback )
    {
        root.traverse( new OcRadiusFrustumCuller( center, radius, frustum ), new GetWithinSphere( callback ) );
    }
}
