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
package org.jagatoo.util.math;

import org.openmali.FastMath;
import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * Util class for Maths.
 *
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class MatrixUtils
{
    /**
     * Converts a Matrix3f to a Tuple3f with Euler angles.
     *
     * @param matrix the Matrix3f to be converted
     */
    public static void matrixToEuler( Matrix3f matrix, Tuple3f euler )
    {
        if ( matrix.m10 == 1.0f )
        {
            euler.x = 0.0f;
            euler.y = FastMath.atan2( matrix.m02, matrix.m22 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
        else if ( matrix.m10 == -1.0f )
        {
            euler.x = 0.0f;
            euler.y = FastMath.atan2( matrix.m02, matrix.m22 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
        else
        {
            euler.x = FastMath.atan2( -matrix.m12, matrix.m11 );
            euler.y = FastMath.atan2( -matrix.m20, matrix.m00 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
    }

    /**
     * Converts a Matrix3f to a Vector3f with Euler angles.
     *
     * @param matrix the Matrix3f to be converted
     *
     * @return the Vector3f containing the euler angles
     */
    public static Vector3f matrixToEuler( Matrix3f matrix )
    {
        Vector3f euler = new Vector3f();

        matrixToEuler( matrix, euler );

        return( euler );
    }

    /**
     * Converts a Matrix4f to a Tuple3f with Euler angles.
     *
     * @param matrix the Matrix4f to be converted
     */
    public static void matrixToEuler( Matrix4f matrix, Tuple3f euler )
    {
        if ( matrix.m10 == 1.0f )
        {
            euler.x = 0.0f;
            euler.y = FastMath.atan2( matrix.m02, matrix.m22 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
        else if ( matrix.m10 == -1.0f )
        {
            euler.x = 0.0f;
            euler.y = FastMath.atan2( matrix.m02, matrix.m22 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
        else
        {
            euler.x = FastMath.atan2( -matrix.m12, matrix.m11 );
            euler.y = FastMath.atan2( -matrix.m20, matrix.m00 );
            euler.z = FastMath.asin( -matrix.m10 );
        }
    }

    /**
     * Converts a Matrix4f to a Vector3f with Euler angles.
     *
     * @param matrix the Matrix4f to be converted
     *
     * @return the Vector3f containing the euler angles
     */
    public static Vector3f matrixToEuler( Matrix4f matrix )
    {
        Vector3f euler = new Vector3f();

        matrixToEuler( matrix, euler );

        return( euler );
    }

    /**
     * Converts Euler angles to a Matrix3f.
     *
     * @param eulerX the x-Euler-angle
     * @param eulerY the y-Euler-angle
     * @param eulerZ the z-Euler-angle
     * @param matrix the Matrix3f instance to write rotational values to
     */
    public static void eulerToMatrix3f( float eulerX, float eulerY, float eulerZ, Matrix3f matrix )
    {
        final float sx = FastMath.sin( eulerX );
        final float sy = FastMath.sin( eulerY );
        final float sz = FastMath.sin( eulerZ );
        final float cx = FastMath.cos( eulerX );
        final float cy = FastMath.cos( eulerY );
        final float cz = FastMath.cos( eulerZ );

        matrix.setElement( 0, 0, cy * cz );
        matrix.setElement( 0, 1, -( cx * sz ) + ( sx * sy * cz ) );
        matrix.setElement( 0, 2, ( sx * sz ) + ( cx * sy * cz ) );
        matrix.setElement( 1, 0, cy * sz );
        matrix.setElement( 1, 1, ( cx * cz ) + ( sx * sy * sz ) );
        matrix.setElement( 1, 2, -( sx * cz ) + ( cx * sy * sz ) );
        matrix.setElement( 2, 0, -sy );
        matrix.setElement( 2, 1, sx * cy );
        matrix.setElement( 2, 2, cx * cy );
    }

    /**
     * Converts Euler angles to a Matrix3f.
     *
     * @param euler the Tuple3f containing all three Euler angles
     * @param matrix the Matrix3f instance to write rotational values to
     */
    public static void eulerToMatrix3f( Tuple3f euler, Matrix3f matrix )
    {
        eulerToMatrix3f( euler.x, euler.y, euler.z, matrix );
    }

    /**
     * Converts Euler angles to a Matrix3f.
     *
     * @param eulerX the x-Euler-angle
     * @param eulerY the y-Euler-angle
     * @param eulerZ the z-Euler-angle
     *
     * @return the new Matrix3f instance reflecting the rotation
     */
    public static Matrix3f eulerToMatrix3f( float eulerX, float eulerY, float eulerZ )
    {
        Matrix3f matrix = new Matrix3f();

        eulerToMatrix3f( eulerX, eulerY, eulerZ, matrix );

        return( matrix );
    }

    /**
     * Converts Euler angles to a Matrix3f.
     *
     * @param euler the Tuple3f containing all three Euler angles
     *
     * @return the new Matrix3f instance reflecting the rotation
     */
    public static Matrix3f eulerToMatrix3f( Tuple3f euler )
    {
        Matrix3f matrix = new Matrix3f();

        eulerToMatrix3f( euler.x, euler.y, euler.z, matrix );

        return( matrix );
    }

    /**
     * Converts Euler angles to a Matrix4f.
     *
     * @param eulerX the x-Euler-angle
     * @param eulerY the y-Euler-angle
     * @param eulerZ the z-Euler-angle
     * @param matrix the Matrix4f instance to write rotational values to
     */
    public static void eulerToMatrix4f( float eulerX, float eulerY, float eulerZ, Matrix4f matrix )
    {
        final float sx = FastMath.sin( eulerX );
        final float sy = FastMath.sin( eulerY );
        final float sz = FastMath.sin( eulerZ );
        final float cx = FastMath.cos( eulerX );
        final float cy = FastMath.cos( eulerY );
        final float cz = FastMath.cos( eulerZ );

        matrix.setElement( 0, 0, cy * cz );
        matrix.setElement( 0, 1, -( cx * sz ) + ( sx * sy * cz ) );
        matrix.setElement( 0, 2, ( sx * sz) + (cx * sy * cz ) );
        matrix.setElement( 1, 0, cy * sz );
        matrix.setElement( 1, 1, ( cx * cz ) + ( sx * sy * sz ) );
        matrix.setElement( 1, 2, -( sx * cz ) + ( cx * sy * sz ) );
        matrix.setElement( 2, 0, -sy );
        matrix.setElement( 2, 1, sx * cy );
        matrix.setElement( 2, 2, cx * cy );
        matrix.setElement( 3, 3, 1 );
    }

    /**
     * Converts Euler angles to a Matrix4f.
     *
     * @param euler the Tuple3f containing all three Euler angles
     * @param matrix the Matrix4f instance to write rotational values to
     */
    public static void eulerToMatrix4f( Tuple3f euler, Matrix4f matrix )
    {
        eulerToMatrix4f( euler.x, euler.y, euler.z, matrix );
    }

    /**
     * Converts Euler angles to a Matrix4f.
     *
     * @param euler the Tuple3f containing all three Euler angles
     *
     * @return the new Matrix4f instance reflecting the rotation
     */
    public static Matrix4f eulerToMatrix4f( Tuple3f euler )
    {
        Matrix4f matrix = new Matrix4f();

        eulerToMatrix4f( euler.x, euler.y, euler.z, matrix );

        return( matrix );
    }

    /**
     * Converts Euler angles to a Matrix4f.
     *
     * @param eulerX the x-Euler-angle
     * @param eulerY the y-Euler-angle
     * @param eulerZ the z-Euler-angle
     *
     * @return the new Matrix4f instance reflecting the rotation
     */
    public static Matrix4f eulerToMatrix4f( float eulerX, float eulerY, float eulerZ )
    {
        Matrix4f matrix = new Matrix4f();

        eulerToMatrix4f( eulerX, eulerY, eulerZ, matrix );

        return( matrix );
    }
}
