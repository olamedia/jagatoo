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
package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.jagatoo.loaders.models.collada.Transform;
import org.jagatoo.loaders.models.collada.stax.XMLChannel;
import org.openmali.vecmath2.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A transform.
 * Used to specify the position, orientation, and scale or matrix
 * of a COLLADA node.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADATransform
{
    private final static HashMap<Class<?>, XMLChannel.ChannelType> classToType = new HashMap<Class<?>, XMLChannel.ChannelType>( 5 );

    static
    {
        classToType.put( Vector3f.class, XMLChannel.ChannelType.TRANSLATE );
        classToType.put( AxisAngle3f.class, XMLChannel.ChannelType.ROTATE );
        classToType.put( Quaternion4f.class, XMLChannel.ChannelType.ROTATE );
        classToType.put( Tuple3f.class, XMLChannel.ChannelType.SCALE );
        classToType.put( Matrix4f.class, XMLChannel.ChannelType.MATRIX );
    }

    private final HashMap<String, Object> transforms = new HashMap<String, Object>( 5 );

    private Vector3f translation = new Vector3f( 0f, 0f, 0f );
    private final ArrayList<AxisAngle3f> rotations = new ArrayList<AxisAngle3f>( 3 );
    private Tuple3f scale = new Tuple3f( 1f, 1f, 1f );
    private Matrix4f matrix = null;

    public static XMLChannel.ChannelType getTransformType( Class<?> clazz )
    {
        return ( classToType.get( clazz ) );
    }

    /**
     * @return conversion of this transform version to Transform
     */
    public Transform toTransform()
    {
        if ( isMatrixTransform() )
        {
            return ( new Transform( matrix ) );
        }

        Quaternion4f rotation = new Quaternion4f();
        if ( rotations.isEmpty() )
        {
            rotation = new Quaternion4f( Quaternion4f.IDENTITY );
        }
        else
        {
            rotation.set( rotations.get( 0 ) );
        }
        for ( int i = 1; i < rotations.size(); i++ )
        {
            Quaternion4f q = new Quaternion4f();
            rotation.mul( q.set( rotations.get( i ) ) );
            rotation.normalize();
        }

        return ( new Transform( translation, rotation, scale ) );
    }

    public final void put( String sid, Object o )
    {
        XMLChannel.ChannelType type = getTransformType( o.getClass() );
        if ( type == null )
        {
            throw new Error( "Unknown class:" + o.getClass() );
        }
        switch ( type )
        {
            case TRANSLATE:
                translation = ( Vector3f ) o;
                break;
            case ROTATE:
                rotations.add( ( AxisAngle3f ) o );
                break;
            case SCALE:
                scale = ( Tuple3f ) o;
                break;
            case MATRIX:
                matrix = ( Matrix4f ) o;
                break;
        }

        if( sid != null )
        {
            transforms.put( sid, o );
        }
    }

    public final Object get( String sid )
    {
        return ( transforms.get( sid ) );
    }

    public final int getElementOrder( Object targetValue )
    {
        if ( targetValue instanceof AxisAngle3f )
        {
            return ( rotations.indexOf( ( AxisAngle3f ) targetValue ) );
        }

        return ( -1 );
    }

    public final boolean isMatrixTransform()
    {
        return ( matrix != null );
    }
}
