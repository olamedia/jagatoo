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
import org.jagatoo.util.heaps.VectorHeap;
import org.openmali.FastMath;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * A spherical bounding volume. It has two associated values:
 * the center point and the radius of the sphere.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class BoundingSphere extends Sphere implements Bounds
{
	private static final long serialVersionUID = 4408387188891021551L;
    
    // a temporary vertex list abstractor
    private VertexList vertexList = new VertexList();
    
    /**
     * {@inheritDoc}
     */
    public boolean intersects( Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        return( IntersectionFactory.sphereIntersectsRay( this, rayOrigin, rayDirection, intersection ) );
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
            return( IntersectionFactory.sphereIntersectsBox( this, (Box)bo ) );
        }
        else if ( bo instanceof Sphere )
        {
            return( IntersectionFactory.sphereIntersectsSphere( this, (Sphere)bo ) );
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
    
    private Point3f tmpP = new Point3f();
    
    /**
     * {@inheritDoc}
     */
    public void transform( Matrix4f trans )
    {
        tmpP.set( getCenter() );
        trans.transform( tmpP );
        setCenter( tmpP );
        
        // now scale the radius
        setRadius( getRadius() * trans.getScale() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void transform( Bounds bounds, Matrix4f trans )
    {
        // this can be a non-optimal but correct
        set( bounds );
        transform( trans );
    }
    
    /**
     * {@inheritDoc}
     */
    public void set( Bounds boundsObject )
    {
        if ( boundsObject instanceof Sphere )
        {
            Sphere s = (Sphere)boundsObject;
            setCenter( s.getCenter() );
            setRadius( s.getRadius() );
        }
        else if ( boundsObject instanceof Box )
        {
            Box b2 = (Box)boundsObject;
            Point3f lower = b2.getLower();
            Point3f upper = b2.getUpper();
            this.setCenter( (upper.x + lower.x) / 2f, (upper.y + lower.y) / 2f, (upper.z + lower.z) / 2f );
            this.setRadius( lower.distance( upper ) / 2f );
        }
        else if ( boundsObject instanceof ConvexHull )
        {
            throw( new Error( "ConvexHull not supported yet" ) );
        }
        else
        {
            throw( new Error( "unknown bounds type: " + boundsObject ) );
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
     * Calculates a tight bounding sphere over a set of points in 3D.
     * This method calculates the AABB and calculates the bounding sphere
     * from it.<br>
     * This is somewhat chaper than computing through distances.
     * 
     * @param source the Vertex-source
     * @param sphere the sphere to put values to
     */
    @SuppressWarnings("unused")
    private static final void computeByAABB( final VertexContainer source, final Sphere sphere )
    {
        final int numVerts = source.getVertexCount();
        
        Point3f xmin = PointHeap.alloc();
        Point3f xmax = PointHeap.alloc();
        Point3f ymin = PointHeap.alloc();
        Point3f ymax = PointHeap.alloc();
        Point3f zmin = PointHeap.alloc();
        Point3f zmax = PointHeap.alloc();
        
        // FIRST PASS: find 6 minima/maxima points
        xmin.set( +Float.MAX_VALUE, +Float.MAX_VALUE, +Float.MAX_VALUE );
        xmax.set( -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE );
        ymin.set( +Float.MAX_VALUE, +Float.MAX_VALUE, +Float.MAX_VALUE) ;
        ymax.set( -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE );
        zmin.set( +Float.MAX_VALUE, +Float.MAX_VALUE, +Float.MAX_VALUE );
        zmax.set( -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE );
        
        Point3f vertex = PointHeap.alloc();
        
        for ( int i = 0; i < numVerts; i++ )
        {
            source.getVertex( i, vertex );
            
            //System.out.println( vertex );
            
            if ( vertex.x < xmin.x )
            {
                xmin.set( vertex );
            }
            
            if ( vertex.x > xmax.x )
            {
                xmax.set( vertex );
            }
            
            if ( vertex.y < ymin.y )
            {
                ymin.set( vertex );
            }
            
            if ( vertex.y > ymax.y )
            {
                ymax.set( vertex );
            }
            
            if ( vertex.z < zmin.z )
            {
                zmin.set( vertex );
            }
            
            if ( vertex.z > zmax.z )
            {
                zmax.set( vertex );
            }
        }
        
        //System.out.println( xmin + ", " + xmax );
        //System.out.println( ymin + ", " + ymax );
        
        sphere.setCenter( xmin.x + (xmax.x - xmin.x) * 0.5f,
                          ymin.y + (ymax.y - ymin.y) * 0.5f,
                          zmin.z + (zmax.z - zmin.z) * 0.5f );
        
        Vector3f radiusVec = VectorHeap.alloc();
        Point3f  min       = PointHeap.alloc();
        Point3f  max       = PointHeap.alloc();
        
        min.set( xmin.x, ymin.y, zmin.z );
        max.set( xmax.x, ymax.y, zmax.z );
        
        radiusVec.sub( max, min );
        
        sphere.setRadius( radiusVec.length() * 0.5f );
        
        PointHeap.free( max );
        PointHeap.free( min );
        VectorHeap.free( radiusVec );
        
        PointHeap.free( vertex );
        
        PointHeap.free( xmin );
        PointHeap.free( xmax );
        
        PointHeap.free( ymin );
        PointHeap.free( ymax );
        
        PointHeap.free( zmin );
        PointHeap.free( zmax );
    }
    
    /**
     * Calculates a tight bounding sphere over a set of points in 3D.
     * This first computes the "average vertex", which is the sphere's center.
     * Then it searches the maximum (squared) distance of all vertices to the
     * center. The sphere's radius is the sqrt of this max squared distance.
     * 
     * @param source the Vertex-source
     * @param sphere
     */
    private static final void computeExactly( final VertexContainer source, final Sphere sphere )
    {
        final int numVerts = source.getVertexCount();
        
        Point3f vertex = PointHeap.alloc();
        
        /*
         * FIRST PASS:
         * 
         * find the "average vertex", which is the sphere's center...
         */
        Point3f avgVertex = PointHeap.alloc();
        Point3f sum = PointHeap.alloc();
        
        avgVertex.set( 0.0f, 0.0f, 0.0f );
        sum.set( 0.0f, 0.0f, 0.0f );
        
        for ( int i = 0; i < numVerts; i++ )
        {
            source.getVertex( i, vertex );
            
            sum.add( vertex );
            
            // apply a part-result to avoid float-overflows
            if ( i % 100 == 0 )
            {
                sum.x /= numVerts;
                sum.y /= numVerts;
                sum.z /= numVerts;
                
                avgVertex.add( sum );
                sum.set( 0.0f, 0.0f, 0.0f );
            }
        }
        
        sum.x /= numVerts;
        sum.y /= numVerts;
        sum.z /= numVerts;
        
        avgVertex.add( sum );
        sum.set( 0.0f, 0.0f, 0.0f );
        
        sphere.setCenter( avgVertex );
        
        PointHeap.free( sum );
        PointHeap.free( avgVertex );
        
        
        /*
         * SECOND PASS:
         * 
         * Find the maximum (squared) distance of all vertices to the center...
         */
        final Point3f center = sphere.getCenter();
        float maxDist_sq = 0.0f;
        
        for ( int i = 0; i < numVerts; i++ )
        {
            source.getVertex( i, vertex );
            
            float dist_sq = vertex.distanceSquared( center );
            if ( dist_sq > maxDist_sq )
                maxDist_sq = dist_sq;
        }
        
        sphere.setRadius( FastMath.sqrt( maxDist_sq ) );
        
        PointHeap.free( vertex );
    }
    
    /**
     * Calculates a tight bounding sphere over a set of points in 3D.
     * 
     * @param source the Vertex-source
     */
    public void compute( final VertexContainer source )
    {
        computeExactly( source, this );
    }
    
    /**
     * Routine to calculate tight bounding sphere over a set of points in 3D.
     * This contains the routine find_bounding_sphere(),
     * the struct definition, and the globals used for parameters.
     * The abs() of all coordinates must be < Float.MAX_VALUE.<br>
     * <br>
     * Code written by Jack Ritter and Lyle Rains.
     * 
     * @param coords the Vertices
     */
    public void compute( final List<Tuple3f> coords )
    {
        vertexList.set( coords );
        
        compute( vertexList );
    }
    
    /**
     * Routine to calculate tight bounding sphere over a set of points in 3D.
     * This contains the routine find_bounding_sphere(),
     * the struct definition, and the globals used for parameters.
     * The abs() of all coordinates must be < Float.MAX_VALUE.<br>
     * <br>
     * Code written by Jack Ritter and Lyle Rains.
     * 
     * @param coords the Vertices
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
     * Constructs a new BoundingShpere object.
     * 
     * @param centerX
     * @param centerY
     * @param centerZ
     * @param radius
     */
    public BoundingSphere( float centerX, float centerY, float centerZ, float radius )
    {
        super( centerX, centerY, centerZ, radius );
    }
    
    /**
     * Constructs a new BoundingShpere object.
     */
    public BoundingSphere( Tuple3f center, float radius )
    {
        this( center.x, center.y, center.z, radius );
    }
    
    /**
     * Constructs a new BoundingShpere object.
     */
    public BoundingSphere()
    {
        this( 0f, 0f, 0f, 0f );
    }
    
    /**
     * Constructs a new BoundingShpere object.
     */
    public BoundingSphere( Bounds bo )
    {
        this();
        
        set( bo );
    }
    
    /**
     * Constructs a new BoundingShpere object.
     */
    public BoundingSphere( Bounds[] bos )
    {
        this();
        
        set( bos );
    }
    
    /**
     * Routine to calculate tight bounding sphere over a set of points in 3D.
     * This contains the routine find_bounding_sphere(),
     * the struct definition, and the globals used for parameters.
     * The abs() of all coordinates must be < Float.MAX_VALUE.
     * 
     * Code written by Jack Ritter and Lyle Rains.<br>
     * <br>
     * @param source the Vertex-source
     * 
     * @return the BoundingSphere
     */
    public static BoundingSphere newBoundingSphere( final VertexContainer source )
    {
        BoundingSphere bs = new BoundingSphere();
        
        bs.compute( source );
        
        return( bs );
    }
    
    /**
     * Routine to calculate tight bounding sphere over a set of points in 3D.
     * This contains the routine find_bounding_sphere(),
     * the struct definition, and the globals used for parameters.
     * The abs() of all coordinates must be < Float.MAX_VALUE.
     * 
     * Code written by Jack Ritter and Lyle Rains.<br>
     * <br>
     * @param coords the vertex list
     * 
     * @return the BoundingSphere
     */
    public static BoundingSphere newBoundingSphere( final List<Tuple3f> coords )
    {
        BoundingSphere bs = new BoundingSphere();
        
        bs.compute( coords );
        
        return( bs );
    }
    
    /**
     * Routine to calculate tight bounding sphere over a set of points in 3D.
     * This contains the routine find_bounding_sphere(),
     * the struct definition, and the globals used for parameters.
     * The abs() of all coordinates must be < Float.MAX_VALUE.
     * 
     * Code written by Jack Ritter and Lyle Rains.<br>
     * <br>
     * @param coords the vertex list
     * 
     * @return the BoundingSphere
     */
    public static BoundingSphere newBoundingSphere( final Tuple3f[] coords )
    {
        BoundingSphere bs = new BoundingSphere();
        
        bs.compute( coords );
        
        return( bs );
    }
}
