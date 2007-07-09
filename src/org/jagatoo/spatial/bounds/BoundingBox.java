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
package org.jagatoo.spatial.bounds;

import java.util.List;

import org.jagatoo.datatypes.Ray3f;
import org.jagatoo.spatial.VertexContainer;
import org.jagatoo.spatial.VertexList;
import org.jagatoo.spatial.bodies.Body;
import org.jagatoo.spatial.bodies.Box;
import org.jagatoo.spatial.bodies.ConvexHull;
import org.jagatoo.spatial.bodies.IntersectionFactory;
import org.jagatoo.spatial.bodies.Sphere;
import org.jagatoo.util.errorhandling.UnsupportedFunction;
import org.jagatoo.util.heaps.PointHeap;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * Axis aligned bounding box volumes
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class BoundingBox extends Box implements Bounds
{
	private static final long serialVersionUID = 6353321413525340703L;
    
    // a temporary vertex list abstractor
    private VertexList vertexList = new VertexList();
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        return( IntersectionFactory.boxIntersectsRay( getLower(), getUpper(), rayOrigin, rayDirection, intersection ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Ray3f ray, Tuple3f intersection )
    {
        return( intersects( ray.getOrigin(), ray.getDirection(), intersection ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Point3f rayOrigin, Vector3f rayDirection )
    {
        return( intersects( rayOrigin, rayDirection, null ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Ray3f ray )
    {
        return( intersects( ray, null ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Bounds bo )
    {
        if ( bo instanceof Box )
        {
            return( IntersectionFactory.boxIntersectsBox( (Box)bo, this ) );
        }
        else if ( bo instanceof Sphere )
        {
            return( IntersectionFactory.sphereIntersectsBox( (Sphere)bo, this ) );
        }
        else if ( bo instanceof ConvexHull )
        {
            throw( new Error( "ConvexHull not supported yet" ) );
        }
        else
        {
            throw( new Error( "unknown Bounds type" ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Bounds[] bos )
    {
        for ( int i = 0; i < bos.length; i++ )
        {
            if ( intersects( bos[ i ] ) )
                return( true );
        }
        
        return( false );
    }
    
    /**
     * {@inheritDoc}
     */
    public Bounds closestIntersection( Bounds[] boundsObjects )
    {
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void transform( Matrix4f trans )
    {
        // brute force translation, lossy if any rotation is present
        final Tuple3f l = getLower();
        final Tuple3f u = getUpper();
        final float sx = l.x;
        final float sy = l.y;
        final float sz = l.z;
        final float ex = u.x;
        final float ey = u.y;
        final float ez = u.z;
        
        Point3f p = PointHeap.alloc();
        
        p.set( sx, sy, sz); trans.transform( p ); setLower( p ); setUpper( p );
        p.set( sx, sy, ez); trans.transform( p ); combine( p );
        p.set( sx, ey, sz); trans.transform( p ); combine( p );
        p.set( sx, ey, ez); trans.transform( p ); combine( p );
        
        p.set( ex, sy, sz); trans.transform( p ); combine( p );
        p.set( ex, sy, ez); trans.transform( p ); combine( p );
        p.set( ex, ey, sz); trans.transform( p ); combine( p );
        p.set( ex, ey, ez); trans.transform( p ); combine( p );
        
        PointHeap.free( p );
    }
    
    /**
     * {@inheritDoc}
     */
    public void transform( Bounds bounds, Matrix4f trans )
    {
        // certainly non-optimal, optimize later
        set( bounds );
        transform( trans );
    }
    
    /**
     * {@inheritDoc}
     */
    public void set( Bounds bo )
    {
        if ( bo instanceof Box )
        {
            BoundingBox b = (BoundingBox)bo;
            setLower( b.getLower() );
            setUpper( b.getUpper() );
        }
        else if ( bo instanceof Sphere )
        {
            Sphere s = (Sphere)bo;
            setLower( s.getCenterX() - s.getRadius(), s.getCenterY() - s.getRadius(), s.getCenterZ() - s.getRadius() );
            setUpper( s.getCenterX() + s.getRadius(), s.getCenterY() + s.getRadius(), s.getCenterZ() + s.getRadius() );
        }
        else if ( bo instanceof ConvexHull )
        {
            throw( new Error( "ConvexHull not supported yet" ) );
        } 
        else
        {
            throw( new Error( "unknown bounds type" ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void set( Bounds[] bos )
    {
        if ( bos.length > 0 )
            this.set( bos[ 0 ] );
        
        for ( int i = 1; i < bos.length; i++ )
            combine( (Body)bos[ i ] );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param source the vectex-source
     */
    private final void computeAABB( final VertexContainer source )
    {
        final int n = source.getVertexCount();
        
        setLower( Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY );
        setUpper( Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY );
        
        Point3f coord = PointHeap.alloc();
        
        for ( int i = 0; i < n; i ++ )
        {
            /*
            if ( source.getVertexCount() != n )
            {
                throw( new Error( "We started with size " + n + " and now it is " + source.getVertexCount() ) );
            }
            */
            
            source.getVertex( i, coord );
            
            if ( coord.x < getLowerX() )
                setLowerX( coord.x );
            else if ( coord.x > getUpperX() )
                setUpperX( coord.x );
            
            if ( coord.y < getLowerY() )
                setLowerY( coord.y );
            else if ( coord.y > getUpperY() )
                setUpperY( coord.y );
            
            if ( coord.z < getLowerZ() )
                setLowerZ( coord.z );
            else if ( coord.z > getUpperZ() )
                setUpperZ( coord.z );
        }
        
        PointHeap.free( coord );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param source the vectex-source
     */
    public void compute( final VertexContainer source )
    {
        computeAABB( source );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param coords a List of coordinates
     */
    public void compute( final List<Tuple3f> coords )
    {
        vertexList.set( coords );
        
        compute( vertexList );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param coords a List of coordinates
     */
    public void compute( final Tuple3f[] coords )
    {
        vertexList.set( coords );
        
        compute( vertexList );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( "Bounding" + super.toString() );
    }
    
    /**
     * Create a new empty bounding box
     */
    public BoundingBox()
    {
    }
    
    /**
     * Create a new bounding box with lower and upper
     * corners specified
     */
    public BoundingBox( Tuple3f lower, Tuple3f upper )
    {
        super( lower, upper );
    }
    
    /**
     * Create a new bounding box enclosing bounds bo
     */
    public BoundingBox( Bounds bo )
    {
        set( bo );
    }
    
    /**
     * Create a new bounding box enclosing bounds bos
     */
    public BoundingBox( Bounds[] bos )
    {
        set( bos );
    } 
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param source the vertex source
     * 
     * @return a Box
     */
    public static BoundingBox newAABB( final VertexContainer source )
    {
        BoundingBox bb = new BoundingBox();
        
        bb.compute( source );
        
        return( bb );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param coords a List of coordinates (Vector3fs)
     * 
     * @return a Box
     */
    public static BoundingBox newAABB( final List<Tuple3f> coords )
    {
        BoundingBox bb = new BoundingBox();
        
        bb.compute( coords );
        
        return( bb );
    }
    
    /**
     * Computes the AABB of a set of coordinates.
     * 
     * @param coords a List of coordinates (Vector3fs)
     * 
     * @return a Box
     */
    public static BoundingBox newAABB( final Tuple3f[] coords )
    {
        BoundingBox bb = new BoundingBox();
        
        bb.compute( coords );
        
        return( bb );
    }
}
