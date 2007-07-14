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
    
    public Matrix4f getMatrix()
    {
        return( matrix );
    }
    
    public void setPlane( int i, Plane plane )
    {
        assert ( i >= 0 && i < 6 );
        
        planes[ i ].set( plane );
    }
    
    public Plane getPlane( int i )
    {
        assert ( i >= 0 && i < 6 );
        
        return( planes[ i ] );
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
     * Extract the frustum from the incoming projections and modelview matrices.
     */
    public final void compute( Matrix4f proj, Matrix4f modl )
    {
        //matrix.set( proj );
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
     * Extract the frustum from the incoming projections and modelview matrices.
     */
    public final Matrix4f computeInverse( Matrix4f proj ) // assume MODEL_VIEW_MATRIX = IDENTITY
    {
        matrix.m00 = ( planes[ 1 ].getA() - planes[ 0 ].getA() ) / 2.0f;
        matrix.m01 = ( planes[ 2 ].getA() - planes[ 3 ].getA() ) / 2.0f;
        matrix.m02 = ( planes[ 5 ].getA() - planes[ 4 ].getA() ) / 2.0f;
        matrix.m03 = planes[ 0 ].getA() + matrix.m00;
        
        matrix.m10 = ( planes[ 1 ].getB() - planes[ 0 ].getB() ) / 2.0f;
        matrix.m11 = ( planes[ 2 ].getB() - planes[ 3 ].getB() ) / 2.0f;
        matrix.m12 = ( planes[ 5 ].getB() - planes[ 4 ].getB() ) / 2.0f;
        matrix.m13 = planes[ 0 ].getB() + matrix.m10;
        
        matrix.m20 = ( planes[ 1 ].getC() - planes[ 0 ].getC() ) / 2.0f;
        matrix.m21 = ( planes[ 2 ].getC() - planes[ 3 ].getC() ) / 2.0f;
        matrix.m22 = ( planes[ 5 ].getC() - planes[ 4 ].getC() ) / 2.0f;
        matrix.m23 = planes[ 0 ].getC() + matrix.m20;
        
        matrix.m30 = ( planes[ 1 ].getD() - planes[ 0 ].getD() ) / 2.0f;
        matrix.m31 = ( planes[ 2 ].getD() - planes[ 3 ].getD() ) / 2.0f;
        matrix.m32 = ( planes[ 5 ].getD() - planes[ 4 ].getD() ) / 2.0f;
        matrix.m33 = planes[ 0 ].getD() + matrix.m30;
        
        if ( proj != null )
            proj.set( matrix );
        
        return( matrix );
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
     * Frustum constructor comment.
     */
    public Frustum()
    {
        super();
        
        for ( int i = 0; i < 6; i++ )
            this.planes[ i ] = new Plane();
    }
}
