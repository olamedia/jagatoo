/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.loaders.models.collada;

import org.openmali.FastMath;
import org.openmali.vecmath2.*;

/**
 *
 */
public class MathUtils
{
    public static void compose( Vector3f translation, Quaternion4f rotation, Tuple3f scale, Matrix4f transform )
    {
        Matrix4f tmp = Matrix4f.fromPool();

        transform.set( translation );

        tmp.set( rotation );
        transform.mul( tmp );

        tmp.setIdentity();
        tmp.m00( scale.getX() );
        tmp.m11( scale.getY() );
        tmp.m22( scale.getZ() );
        transform.mul( tmp );

        Matrix4f.toPool( tmp );
    }

    public static boolean decompose( Matrix4f transform, Vector3f translation, Quaternion4f rotation, Tuple3f scale )
    {
        Vector4f persp = Vector4f.fromPool();
        Tuple3f shear = Tuple3f.fromPool();

        boolean result = decompose( transform, translation, rotation, scale, persp, shear );

        Vector4f.toPool(persp);
        Tuple3f.fromPool(shear);

        return ( result );
    }

    /**
     * based on unmatrix.c - given a 4x4 non-degenerate transformation matrix,
     * decompose it into standard operations.
     * <p/>
     * Author:	Spencer W. Thomas
     * University of Michigan
     * <p/>
     * Returns true upon success, false if the matrix is singular.
     *
     * @param transform
     * @param translation
     * @param rotation
     * @param scale
     * @param persp
     * @param shear
     * @return true upon success, false if the matrix is singular.
     */
    public static boolean decompose( Matrix4f transform, Vector3f translation, Quaternion4f rotation, Tuple3f scale, Vector4f persp, Tuple3f shear )
    {
        Matrix4f locmat = new Matrix4f( transform );
        // Normalize the matrix.
        if ( locmat.m33() == 0 )
        {
            return ( false );
        }
        final float m33 = locmat.m33();
        for ( int i = 0; i < 4; i++ )
        {
            for ( int j = 0; j < 4; j++ )
            {
                locmat.set( i, j, locmat.get( i, j ) / m33 );
            }
        }

        Matrix4f invpmat = new Matrix4f();
        Matrix4f tinvpmat = new Matrix4f();

        // locmat is used to solve for perspective, but it also provides
        // an easy way to test for singularity of the upper 3x3 component.
        Matrix4f pmat = locmat;
        for ( int i = 0; i < 3; i++ )
        {
            pmat.set( 3, i, 0f );
        }
        pmat.m33( 1f );
        if ( pmat.determinant() == 0.0 )
        {
            return ( false );
        }

        // First, isolate perspective.  This is the messiest.
        if ( locmat.m30() != 0 || locmat.m31() != 0 || locmat.m32() != 0 )
        {
            // prhs is the right hand side of the equation.
            Vector4f prhs = new Vector4f();

            prhs.setX( locmat.m30() );
            prhs.setY( locmat.m31() );
            prhs.setZ( locmat.m32() );
            prhs.setW( locmat.m33() );
            // Solve the equation by inverting pmat and multiplying
            // prhs by the inverse.  (This is the easiest way, not
            // necessarily the best.)
            pmat.invert( invpmat );
            invpmat.transpose( tinvpmat );
            tinvpmat.transform( prhs, persp );

            // Clear the perspective partition.
            locmat.m30( 0 );
            locmat.m31( 0 );
            locmat.m32( 0 );
            locmat.m33( 1 );
        }
        else        // No perspective.
        {
            persp.setX( 0 );
            persp.setY( 0 );
            persp.setZ( 0 );
            persp.setW( 0 );
        }
        // Next take care of translation (easy).
        for ( int i = 0; i < 3; i++ )
        {
            translation.setValue( i, locmat.get( i, 3 ) );
            locmat.set( i, 3, 0f );
        }

        Vector3f[] row = new Vector3f[3];
        for ( int i = 0; i < row.length; i++ )
        {
            row[ i ] = new Vector3f();
        }
        // Now get scale and shear.
        for ( int i = 0; i < 3; i++ )
        {
            row[ i ].setX( locmat.get( 0, i ) );
            row[ i ].setY( locmat.get( 1, i ) );
            row[ i ].setZ( locmat.get( 2, i ) );
        }

        // Compute X scale factor and normalize first row.
        scale.setX( row[ 0 ].length() );
        row[ 0 ].normalize();

        // Compute XY shear factor and make 2nd row orthogonal to 1st.
        shear.setValue( 0, row[ 0 ].dot( row[ 1 ] ) );
        combine( row[ 1 ], row[ 0 ], row[ 1 ], 1.0f, -shear.getValue( 0 ) );

        // Now, compute Y scale and normalize 2nd row.
        scale.setY( row[ 1 ].length() );
        row[ 1 ].normalize();
        shear.setValue( 0, shear.getValue( 0 ) / scale.getY() );

        // Compute XZ and YZ shears, orthogonalize 3rd row.
        shear.setValue( 1, row[ 0 ].dot( row[ 2 ] ) );
        combine( row[ 2 ], row[ 0 ], row[ 2 ], 1.0f, -shear.getValue( 1 ) );
        shear.setValue( 2, row[ 1 ].dot( row[ 2 ] ) );
        combine( row[ 2 ], row[ 1 ], row[ 2 ], 1.0f, -shear.getValue( 2 ) );

        // Next, get Z scale and normalize 3rd row.
        scale.setZ( row[ 2 ].length() );
        row[ 2 ].normalize();
        shear.setValue( 1, shear.getValue( 1 ) / scale.getZ() );
        shear.setValue( 2, shear.getValue( 2 ) / scale.getZ() );
        // At this point, the matrix (in rows[]) is orthonormal.
        // Check for a coordinate system flip.  If the determinant
        // is -1, then negate the matrix and the scaling factors.
        Vector3f pdum3 = new Vector3f();
        pdum3.cross( row[ 1 ], row[ 2 ] );
        if ( row[ 0 ].dot( pdum3 ) < 0 )
        {
            scale.negate();
            row[ 0 ].negate();
            row[ 1 ].negate();
            row[ 2 ].negate();
        }
        // Now, get the rotations out, as described in the gem.
        Tuple3f rots = new Tuple3f();
        rots.setY( FastMath.asin( -row[ 0 ].getZ() ) );
        if ( FastMath.cos( rots.getY() ) != 0 )
        {
            rots.setX( FastMath.atan2( row[ 1 ].getZ(), row[ 2 ].getZ() ) );
            rots.setZ( FastMath.atan2( row[ 0 ].getY(), row[ 0 ].getX() ) );
        }
        else
        {
            //rots.setX( FastMath.atan2( row[ 1 ].getX(), row[ 1 ].getY() ) );
            rots.setX( FastMath.atan2( -row[ 2 ].getX(), row[ 1 ].getY() ) );
            rots.setZ( 0 );
        }

        Rotations.toQuaternion( rots, rotation );
        // All done!
        return ( true );
    }

    private static void combine( Tuple3f a, Tuple3f b, Tuple3f result, float ascl, float bscl )
    {
        result.setX( ( ascl * a.getX() ) + ( bscl * b.getX() ) );
        result.setY( ( ascl * a.getY() ) + ( bscl * b.getY() ) );
        result.setZ( ( ascl * a.getZ() ) + ( bscl * b.getZ() ) );
    }

    /**
     * Converts Euler angles to a Quaternion4f.
     *
     * @param eulerX the x-Euler-angle
     * @param eulerY the y-Euler-angle
     * @param eulerZ the z-Euler-angle
     * @return the Quaternion4f instance to write rotational values to
     */
    public static Quaternion4f eulerToQuaternion( float eulerX, float eulerY, float eulerZ )
    {
        double sx = Math.sin( eulerX / 2 );
        double sy = Math.sin( eulerY / 2 );
        double sz = Math.sin( eulerZ / 2 );
        double cx = Math.cos( eulerX / 2 );
        double cy = Math.cos( eulerY / 2 );
        double cz = Math.cos( eulerZ / 2 );
        double cycz = cy * cz;
        double sysz = sy * sz;
        double d = cycz * cx - sysz * sx;
        double a = cycz * sx + sysz * cx;
        double b = sy * cz * cx + cy * sz * sx;
        double c = cy * sz * cx - sy * cz * sx;

        Quaternion4f q = new Quaternion4f( ( float ) a, ( float ) b, ( float ) c, ( float ) d );
        q.normalize();

        return ( q );
    }

    private MathUtils()
    {
    }
}
