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

import org.openmali.vecmath.Tuple3f;

import org.jagatoo.spatial.Visibility;

/**
 * Spatial container holding the OcNodes.
 * This represents a location in space.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class OcCell<T>
{
    private OcCell<T> parent = null;
    private OcCell<T>[] child;
    public float cx;
    public float cy;
    public float cz;
    public float halfSize;
    private OcNode<T> objects = null;
    
    public OcCell getParent()
    {
        return( parent );
    }
    
    public boolean fitsInBox( Tuple3f c, float radius, float cx, float cy, float cz, float HalfSize )
    {
        if ( ( ( c.x - radius ) < ( cx - HalfSize ) ) ||
             ( ( c.x + radius ) > ( cx + HalfSize ) ) ||
             ( ( c.y - radius ) < ( cy - HalfSize ) ) ||
             ( ( c.y + radius ) > ( cy + HalfSize ) ) ||
             ( ( c.z - radius ) < ( cz - HalfSize ) ) ||
             ( ( c.z + radius ) > ( cz + HalfSize ) ) )
        {
            //System.out.println( "" + c + " radius " + radius + " does NOT fit (" + cx + "," + cy + "," + cz + ") +/- " + HalfSize );
            return( false );
        }
        else
        {
            //System.out.println( "" + c + " radius " + radius + " does fit (" + cx + "," + cy + "," + cz + ") +/- " + HalfSize );
            return( true );
        }
    }
    
    /**
     * Tests whether the given object can fit in the box centered at (cx, cz),
     * with side dimensions of HalfSize * 2.
     * 
     * @param o
     * @param cx
     * @param cz
     * @param halfSize
     * 
     * @return true, if the given object fits into the box
     */
    public boolean fitsInBox( OcNode o, float cx, float cy, float cz, float halfSize )
    {
        return( fitsInBox( o.getCenter(), o.getRadius(), cx, cy, cz, halfSize ) );
    }
    
    // Insert the given object into the tree given by qnode.
    void insert( OcTree<T> tree, OcNode<T> o, int depth )
    {
        // Check child nodes to see if object fits in one of them.
        if ( halfSize > tree.minCellSize )
        {
            float QuarterSize = halfSize / 2.0f;
            float offset = QuarterSize;
            
            for ( int j = 0; j < 2; j++ )
            {
                for ( int i = 0; i < 2; i++ )
                {
                    for ( int k = 0; k < 2; k++ )
                    {
                        float cx = this.cx + ((i == 0) ? (-offset) : offset);
                        float cy = this.cy + ((k == 0) ? (-offset) : offset);
                        float cz = this.cz + ((j == 0) ? (-offset) : offset);
                        
                        int index = ((i * 2) + j) + (k * 4);
                        
                        if ( fitsInBox( o, cx, cy, cz, QuarterSize ) )
                        {
                            // Recurse into this node.
                            if (child[ index ] == null)
                            {
                                child[ index ] = new OcCell<T>( tree, this, cx, cy, cz, depth + 1 );
                            }
                            
                            child[ index ].insert( tree, o, depth + 1 );
                            
                            return;
                        }
                    }
                }
            }
        }
        
        // Keep object in this node.
        tree.numObjects++;
        tree.totalDepth += depth;
        o.next = objects;
        objects = o;
    }
    
    public String getName()
    {
        StringBuffer sb = new StringBuffer( 50 );
        
        sb.append( "OcCell (" );
        sb.append( Float.toString( cx ) );
        sb.append( ',' );
        sb.append( Float.toString( cy ) );
        sb.append( ',' );
        sb.append( Float.toString( cz ) );
        sb.append( ") halfsize=" );
        sb.append( Float.toString( halfSize ) );
        
        return( sb.toString() );
    }
    
    /**
     * OcTree traversal.  Steps through the octree and invokes the callback for every
     * object not being culled by the specified culler
     * @param culler
     * @param callback
     */
    void traverse( OcCuller culler, OcCallback callback )
    {
        if ( culler.checkCell( this ) == Visibility.NOT_VISIBLE )
        {
            return;
        }
        
        // Render objects in this node.
        OcNode o = objects;
        
        while ( o != null )
        {
            Visibility v = culler.checkNode( o );
            
            if ( v != Visibility.NOT_VISIBLE )
            {
                if ( callback != null )
                    callback.hit( v, o );
            }
            
            o = o.next;
        }
        
        // Render children.
        for ( int j = 0; j < 8; j++ )
            if ( child[ j ] != null )
            {
                child[ j ].traverse( culler, callback );
            }
    }
    
    public OcCell()
    {
    }
    
    @SuppressWarnings("unchecked")
    public OcCell( OcTree<T> tree, OcCell<T> p, float x, float y, float z, int d )
    {
        parent = p;
        cx = x;
        cy = y;
        cz = z;
        halfSize = tree.worldSize / (2 << d);
        
        child = new OcCell[ 8 ];
        
        for ( int j = 0; j < 8; j++ )
        {
            if ( child[ j ] != null )
            {
                child[ j ] = null;
            }
        }
        
        objects = null;
    }
}
