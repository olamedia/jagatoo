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

import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.stax.XMLChannel;
import org.openmali.FastMath;
import org.openmali.vecmath2.AxisAngle3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 */
public class AnimationChannel
{
    private final DaeJoint joint;
    private final float[] timeline;

    private Vector3f[] translations = null;
    private Quaternion4f[] rotations = null;
    private Tuple3f[] scales = null;

    private final Enum<?> element;
    private final XMLChannel.ChannelType type;
    private final float[] output;

    public DaeJoint getJoint()
    {
        return ( joint );
    }

    public float[] getTimeline()
    {
        return ( timeline );
    }

    public Vector3f[] getTranslations()
    {
        return ( translations );
    }

    public Quaternion4f[] getRotations()
    {
        return ( rotations );
    }

    public Tuple3f[] getScales()
    {
        return ( scales );
    }

    public Object getElement()
    {
        return ( element );
    }

    public XMLChannel.ChannelType getType()
    {
        return ( type );
    }

    @SuppressWarnings( "unchecked" )
    private <T> T[] createValues( float[] output, final int stride, Class<T> clazz )
    {
        ArrayList<T> l = new ArrayList<T>();
        float[] tuple = new float[stride];
        int pos = 0;
        if ( output.length > 0 )
        {
            for ( int i = 0; ; i++ )
            {
                if ( pos == stride )
                {
                    pos = 0;
                    if ( clazz == Vector3f.class )
                    {
                        l.add( (T) createVector3f( tuple ) );
                    }
                    else if ( clazz == Quaternion4f.class )
                    {
                        Quaternion4f q = createQuaternion4f( tuple );
                        l.add( (T) q );
                    }
                    else if ( clazz == Tuple3f.class )
                    {
                        l.add( (T) createTuple3f( tuple ) );
                    }
                }
                if ( i < output.length )
                {
                    if ( pos < stride )
                    {
                        tuple[ pos++ ] = output[ i ];
                    }
                }
                else
                {
                    break;
                }
            }
        }

        return ( l.toArray( (T[]) Array.newInstance( clazz, l.size() ) ) );
    }

    private Vector3f createVector3f( float[] tuple )
    {
        if ( tuple.length == 3 )  //todo
        {
            return ( new Vector3f( tuple[ 0 ], tuple[ 1 ], tuple[ 2 ] ) );
        }

        return ( null );
    }

    private Quaternion4f createQuaternion4f( float[] tuple )
    {
        if ( tuple.length == 1 )  //todo
        {
            float radians = FastMath.toRad( tuple[ 0 ] );
            Quaternion4f q = new Quaternion4f();
            AxisAngle3f aa = new AxisAngle3f( 0f, 0f, 0f, radians );
            aa.setValue( element.ordinal(), 1f );
            q.set( aa );

            return ( q );
        }

        return ( null );
    }

    private Tuple3f createTuple3f( float[] tuple )
    {
        if ( tuple.length == 3 )  //todo
        {
            return ( new Tuple3f( tuple[ 0 ], tuple[ 1 ], tuple[ 2 ] ) );
        }

        return ( null );
    }

    private static Quaternion4f createQuaternion( float aX, float aY, float aZ )
    {
        float rX = FastMath.toRad( aX );
        float rY = FastMath.toRad( aY );
        float rZ = FastMath.toRad( aZ );

        Quaternion4f q = MathUtils.eulerToQuaternion( rX, rY, rZ );
        return ( q );
    }

    public float[] getOutput()
    {
        return ( output );
    }

    private static Quaternion4f[] createRotations( float[] outputX, float[] outputY, float[] outputZ )
    {
        ArrayList<Quaternion4f> l = new ArrayList<Quaternion4f>( outputX.length );
        for ( int i = 0; i < outputX.length; i++ )
        {
            l.add( createQuaternion( outputX[ i ], outputY[ i ], outputZ[ i ] ) );

        }
        return ( l.toArray( new Quaternion4f[outputX.length] ) );
    }

    public AnimationChannel( DaeJoint joint, float[] timeline, float[] output, Enum<?> element, XMLChannel.ChannelType type, int stride )
    {
        this.joint = joint;
        this.timeline = timeline;

        this.output = output;
        this.element = element;
        this.type = type;
        switch ( type )
        {
            case TRANSLATE:
                translations = createValues( output, stride, Vector3f.class );
                break;

            case ROTATE:
                //   rotations = createValues( output, stride, Quaternion4f.class );
                break;

            case SCALE:
                scales = createValues( output, stride, Tuple3f.class );
                break;
        }
    }

    public AnimationChannel( AnimationChannel r, Quaternion4f[] ql )
    {
        joint = r.getJoint();
        timeline = r.getTimeline();
        output = null;
        element = null;
        type = r.getType();
        rotations = ql;
    }


    public AnimationChannel( AnimationChannel rX, float[] outputY, float[] outputZ )
    {
        joint = rX.getJoint();
        timeline = rX.getTimeline();
        output = rX.output;
        element = null;
        type = rX.getType();
        switch ( type )
        {
            case TRANSLATE:
                //translations = createValues( output, stride, Vector3f.class );
                break;

            case ROTATE:
                rotations = createRotations( output, outputY, outputZ );
                break;

            case SCALE:
                //scales = createValues( output, stride, Tuple3f.class );
                break;
        }
    }
}
