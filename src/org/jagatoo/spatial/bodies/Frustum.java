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
package org.jagatoo.spatial.bodies;

import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Vector3f;

/**
 * A viewing frustum.
 * 
 * @author cas
 * @author Marvin Froehlich (aka Qudus)
 */
public class Frustum
{
    public static float error = 0.0f;
    
    private final Plane[] planes = new Plane[ 6 ];
    private final Matrix4f matrix = new Matrix4f();
    
    public Plane getPlane( int i )
    {
        assert ( i >= 0 && i < 6 );
        
        return( planes[ i ] );
    }
    
    public Matrix4f getMatrix()
    {
        return( matrix );
    }
    
    /**
     * Quick check to see if an orthogonal bounding box is inside the frustum
     */
    public final Classifier.Classification quickClassify( Box box )
    {
        for ( int i = 0; i < 6; i++ )
        {
            final Plane p = planes[ i ];
            if ( p.distanceTo( box.getLowerX(), box.getLowerY(), box.getLowerZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getUpperX(), box.getLowerY(), box.getLowerZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getLowerX(), box.getUpperY(), box.getLowerZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getUpperX(), box.getUpperY(), box.getLowerZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getLowerX(), box.getLowerY(), box.getUpperZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getUpperX(), box.getLowerY(), box.getUpperZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getLowerX(), box.getUpperY(), box.getUpperZ() ) > 0.0f )
                continue;
            if ( p.distanceTo( box.getUpperX(), box.getUpperY(), box.getUpperZ() ) > 0.0f )
                continue;
            
            return( Classifier.Classification.OUTSIDE );
        }
        
        return( Classifier.Classification.SPANNING );
        // We make no attempt to determine whether it's fully inside or not.
    }
    
    /**
     * Intersect the frustum with a plane. The result is returned in a set of points which
     * make a quadrilateral. If the frustum does not intersect the plane then the function
     * @return false and the points are left untouched.
     *
     * The array of points passed in must have a size equal to 4.
     */
    public final boolean intersects( Plane p, Vector3f[] quad )
    {
        return( false );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer( 512 );
        
        sb.append( "Frustum[ " );
        sb.append( "Left: " );
        sb.append( planes[ 0 ] );
        sb.append( ", Right: " );
        sb.append( planes[ 1 ] );
        sb.append( ", Bottom:" );
        sb.append( planes[ 2 ] );
        sb.append( ", Top:" );
        sb.append( planes[ 3 ] );
        sb.append( ", Far:" );
        sb.append( planes[ 4 ] );
        sb.append( ", Near:" );
        sb.append( planes[ 5 ] );
        sb.append( " ]" );
        
        return( sb.toString() );
    }
    
    /**
     * Extract the frustum from the incoming projections and modelview matrices.
     */
    public final void compute( Matrix4f proj, Matrix4f modl )
    {
        matrix.set( proj );
        matrix.mul( proj, modl );
        
        // Now get the frustum's 6 clipping planes
        
        // Extract the numbers for the RIGHT plane
        planes[ 0 ].set( matrix.m03 - matrix.m00,
                        matrix.m13 - matrix.m10,
                        matrix.m23 - matrix.m20,
                        matrix.m33 - matrix.m30
                      );
        
        // Extract the numbers for the LEFT plane
        planes[ 1 ].set( matrix.m03 + matrix.m00,
                        matrix.m13 + matrix.m10,
                        matrix.m23 + matrix.m20,
                        matrix.m33 + matrix.m30
                      );
        
        // Extract the BOTTOM plane
        planes[ 2 ].set( matrix.m03 + matrix.m01,
                        matrix.m13 + matrix.m11,
                        matrix.m23 + matrix.m21,
                        matrix.m33 + matrix.m31
                      );
        
        /// Extract the TOP plane
        planes[ 3 ].set( matrix.m03 - matrix.m01,
                        matrix.m13 - matrix.m11,
                        matrix.m23 - matrix.m21,
                        matrix.m33 - matrix.m31
                      );
        
        // Extract the FAR plane
        planes[ 4 ].set( matrix.m03 - matrix.m02,
                        matrix.m13 - matrix.m12,
                        matrix.m23 - matrix.m22,
                        matrix.m33 - matrix.m32
                      );
        
        // Extract the NEAR plane
        planes[ 5 ].set( matrix.m03 + matrix.m02,
                        matrix.m13 + matrix.m12,
                        matrix.m23 + matrix.m22,
                        matrix.m33 + matrix.m32
                      );
    }
    
    /**
     * Frustum constructor comment.
     */
    public Frustum()
    {
        super();
        
        for ( int i = 0; i < 6; i++ )
            planes[ i ] = new Plane();
    }
}
