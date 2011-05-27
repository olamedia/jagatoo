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

import org.openmali.vecmath2.*;

/**
 *
 */
public class Transform
{
    private Vector3f translation;
    private Quaternion4f rotation;
    private Tuple3f scale;

    private Matrix4f matrix;

    private boolean isMatrix;

    public Vector3f getTranslation( Vector3f translation )
    {
        if ( this.translation == null )
        {
            setTranslationRotationScale();
        }
        if ( translation != null )
        {
            translation.set( this.translation );
            return ( translation );
        }
        return ( this.translation );
    }

    public Quaternion4f getRotation( Quaternion4f rotation )
    {
        if ( this.rotation == null )
        {
            setTranslationRotationScale();
        }
        if ( rotation != null )
        {
            rotation.set( this.rotation );
            return ( rotation );
        }
        return ( this.rotation );
    }

    public Tuple3f getScale( Tuple3f scale )
    {
        if ( this.scale == null )
        {
            setTranslationRotationScale();
        }
        if ( scale != null )
        {
            scale.set( this.scale );
            return ( scale );
        }
        return ( this.scale );
    }

    public Matrix4f getMatrix4f( Matrix4f matrix )
    {
        if ( this.matrix == null )
        {
            setMatrix();
        }
        if ( matrix != null )
        {
            matrix.set( this.matrix );
            return ( matrix );
        }
        return ( this.matrix );
    }

    private void setMatrix()
    {
        if ( this.translation == null || this.rotation == null || this.scale == null )
        {
            throw new IllegalStateException();
        }

        matrix = new Matrix4f();

        MathUtils.compose( translation, rotation, scale, matrix );
    }

    private void setTranslationRotationScale()
    {
        if ( matrix == null )
        {
            throw new IllegalStateException();
        }

        translation = new Vector3f();
        rotation = new Quaternion4f();
        scale = new Tuple3f();

        if ( !MathUtils.decompose( matrix, translation, rotation, scale ) )
        {
            throw new Error( "Bad matrix." );
        }
    }

    /**
     * Sets the value of this transform to the result of multiplying the two transform
     * argument together.
     *
     * @param m
     * @param t
     * @param out
     * @return itself
     */
    public Matrix4f mul( Matrix4f m, Transform t, Matrix4f out )
    {
        t.getMatrix4f( out );
        out.mul( m/* , out */ );

        return ( out );
    }

    public final void invert( Matrix4f matrix )
    {
        getMatrix4f( matrix );
        matrix.invert();
    }

    public boolean isMatrixTransform()
    {
        return ( isMatrix );
    }

    /**
     * Sets the value of this transform to the result of multiplying the two transform
     * arguments together.
     *
     * @param t1
     * @param t2
     * @return itself
     */
    public Transform mul( Transform t1, Transform t2 )
    {
        if ( t1.isMatrix && t2.isMatrix )
        {
            Matrix4f m = new Matrix4f();
            set( m.mul( t1.matrix, t2.matrix ) );
        }
        else if ( !t1.isMatrix && !t2.isMatrix )
        {
            Vector3f t = new Vector3f();
            Quaternion4f r = new Quaternion4f();
            Tuple3f s = new Tuple3f();

            Matrix3f m1 = new Matrix3f();
            Matrix3f m2 = new Matrix3f();
            m1.set( t1.rotation );
            m2.set( t2.rotation );

            Matrix3f tmp = new Matrix3f();
            tmp.setIdentity();
            tmp.m00( t1.scale.getX() );
            tmp.m11( t1.scale.getY() );
            tmp.m22( t1.scale.getZ() );
            m1.mul( tmp );

            tmp.setIdentity();
            tmp.m00( t2.scale.getX() );
            tmp.m11( t2.scale.getY() );
            tmp.m22( t2.scale.getZ() );
            m2.mul( tmp );

            m2.mul( m1, m2 );
            s.set( t1.scale );
            s.mul( t2.scale.getX(), t2.scale.getY(), t2.scale.getZ() );
            t.set( t2.translation );

            m1.transform( t );
            t.add( t1.translation );

            tmp.setIdentity();
            tmp.m00( s.getX() );
            tmp.m11( s.getY() );
            tmp.m22( s.getZ() );
            m2.mul( tmp.invert(), m2 );
            r.set( m2 ).normalize();
            set( t, r, s );
        }
        else
        {
            Matrix4f m = new Matrix4f();
            set( m.mul( t1.getMatrix4f( null ), t2.getMatrix4f( null ) ) );
        }

        return ( this );
    }

    private void set( Vector3f t, Quaternion4f r, Tuple3f s )
    {
        translation = t;
        rotation = r;
        scale = s;
        isMatrix = false;

        matrix = null;
    }

    private void set( Matrix4f m )
    {
        matrix = m;
        isMatrix = true;

        translation = null;
        rotation = null;
        scale = null;
    }

    @Override
    public String toString()
    {
        return ( "Transform{" +
                ( isMatrix ?
                        "\nmatrix=\n" + matrix :
                        "\ntranslation=" + translation +
                                ", \nrotation=" + rotation +
                                ", \nscale=" + scale
                )
                +
                '}'
        );
    }

    public Transform( Matrix4f matrix )
    {
        this.matrix = matrix;
        this.isMatrix = true;
    }

    public Transform( Vector3f translation, Quaternion4f rotation, Tuple3f scale )
    {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
        this.isMatrix = false;
    }
}
