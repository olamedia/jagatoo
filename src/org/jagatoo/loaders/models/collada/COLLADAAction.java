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
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.DaeNode;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;
import org.openmali.vecmath2.Tuple3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A COLLADA "Action", or "Animation" or "Animation clip" or "Animation strip",
 * whatever you call it : it's a "piece of movement" you can play on your model.
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class COLLADAAction
{
    /**
     * The identifier of this animation. It should be unique in the same model,
     * ex. you cannot have two animations that have the same ID for the same
     * model
     */
    private final String id;

    /**
     * Skeleton for this action
     */
    private DaeSkeleton skeleton;

    /**
     * Translation key frames, per joint (actually for root)
     */
    private HashMap<DaeNode, ArrayList<AnimationChannel>> translations = new HashMap<DaeNode, ArrayList<AnimationChannel>>();
    /**
     * Rotation key frames, per joint.
     */
    private HashMap<DaeNode, ArrayList<AnimationChannel>> rotations = new HashMap<DaeNode, ArrayList<AnimationChannel>>();
    /**
     * Scale key frames, per joint.
     */
    private HashMap<DaeNode, ArrayList<AnimationChannel>> scales = new HashMap<DaeNode, ArrayList<AnimationChannel>>();
    /**
     * Scale key frames, per joint.
     */
    private HashMap<DaeNode, AnimationChannel> matrices = new HashMap<DaeNode, AnimationChannel>();
    private boolean prepared = false;


    /**
     * Creates a new COLLADAAction.
     *
     * @param id the id of this COLLADA Action
     */
    public COLLADAAction( String id )
    {
        this.id = id;
    }

    /**
     * @return the ID String of this COLLADA Action.
     */
    public final String getId()
    {
        return ( id );
    }

    /**
     * @param skeleton the skeleton to set
     */
    public final void setSkeleton( DaeSkeleton skeleton )
    {
        if ( this.skeleton != null )
        {
            throw new Error( "skeleton is already set" );
        }
        this.skeleton = skeleton;
    }

    /**
     * @return the skeleton
     */
    public final DaeSkeleton getSkeleton()
    {
        return ( skeleton );
    }

    /**
     * Loops through each joint of the skeleton and completes their temp key frames arrays.
     */
    public void prepareJoints()
    {
        if ( prepared )
        {
            return;
        }
        if ( skeleton == null )
        {
            return;
        }
        prepared = true;
        skeleton.resetIterator();
        for ( DaeJoint joint : skeleton )
        {
            if ( joint.getCOLLADATransform().isMatrixTransform() )
            {
                AnimationChannel m = matrices.get( joint );
                if ( m == null )
                {
                    m = AnimationChannel.createEmpty( joint, Matrix4f.class );
                }
                joint.setMatrices( m );

                matrices.remove( joint ); //cleanup
            }
            else
            {
                List<AnimationChannel> l = translations.get( joint );
                AnimationChannel t = l == null ?
                        AnimationChannel.createEmpty( joint, Vector3f.class ) :
                        AnimationChannel.merge( joint, l, Vector3f.class );
                joint.setTranslations( t );

                l = rotations.get( joint );
                t = l == null ? AnimationChannel.createEmpty( joint, Quaternion4f.class ) :
                        AnimationChannel.merge( joint, l, Quaternion4f.class );
                joint.setRotations( t );

                l = scales.get( joint );
                t = l == null ? AnimationChannel.createEmpty( joint, Tuple3f.class ) :
                                AnimationChannel.merge( joint, l, Tuple3f.class );
                joint.setScales( t );

                translations.remove( joint ); //cleanup
                rotations.remove( joint );
                scales.remove( joint );
            }
        }
    }

    public void putTranslations( DaeNode joint, AnimationChannel translations )
    {
        ArrayList<AnimationChannel> l = this.translations.get( joint );
        if ( l == null )
        {
            l = new ArrayList<AnimationChannel>( 1 );
            this.translations.put( joint, l );
        }
        if ( translations.getOrd() >= l.size() )
        {
            int delta = translations.getOrd() + 1 - l.size();
            for ( int i = 0; i < delta; i++ ) //ensure size
            {
                l.add( AnimationChannel.createEmpty( ( DaeJoint ) joint, Vector3f.class ));
            }
        }
        l.set( translations.getOrd(), translations );
    }

    public void putRotations( DaeNode joint, AnimationChannel rotations )
    {
        ArrayList<AnimationChannel> l = this.rotations.get( joint );
        if ( l == null )
        {
            l = new ArrayList<AnimationChannel>( 3 );
            this.rotations.put( joint, l );
        }
        if ( rotations.getOrd() >= l.size() )
        {
            int delta = rotations.getOrd() + 1 - l.size();
            for ( int i = 0; i < delta; i++ ) //ensure size
            {
                l.add( AnimationChannel.createEmpty( ( DaeJoint ) joint, Quaternion4f.class ));
            }
        }
        l.set( rotations.getOrd(), rotations );
    }

    public void putScales( DaeNode joint, AnimationChannel scales )
    {
        ArrayList<AnimationChannel> l = this.scales.get( joint );
        if ( l == null )
        {
            l = new ArrayList<AnimationChannel>( 1 );
            this.scales.put( joint, l );
        }
        if ( scales.getOrd() >= l.size() )
        {
            int delta = scales.getOrd() + 1 - l.size();
            for ( int i = 0; i < delta; i++ ) //ensure size
            {
                l.add( AnimationChannel.createEmpty( ( DaeJoint ) joint, Tuple3f.class ));
            }
        }
        l.set( scales.getOrd(), scales );
    }

    public void putMatrices( DaeNode joint, AnimationChannel matrices )
    {
        this.matrices.put( joint, matrices );
    }
}
