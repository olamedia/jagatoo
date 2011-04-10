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

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Axis;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.stax.XMLAnimation;
import org.jagatoo.loaders.models.collada.stax.XMLChannel.ChannelType;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryAnimations;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Quaternion4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Library animations loader.
 *
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryAnimationsLoader
{
    private static final int MAX_ANIMATIONS_PER_JOINT = 9; //( 3 rots 3 trans 3 scales)

    /**
     * Loads the library of animations.
     *
     * @param colladaFile
     * @param libAnim
     */
    public static void loadLibraryAnimations( AssetFolder colladaFile, XMLLibraryAnimations libAnim )
    {
        Collection<XMLAnimation> anims = libAnim.animations.values();
        JAGTLog.debug( "There ", ( anims.size() > 1 ? "are" : "is" ), " ", anims.size(), " animation", ( anims.size() > 1 ? "s" : "" ), " in this file." );

        HashMap<String, COLLADAAction> colAnims = colladaFile.getLibraryAnimations().getAnimations();

        /*
        * for( all Skeletons ) {
        *
        * But we must know what skeleton belongs each joint
        *
        */

        for ( DaeSkeleton skeleton : colladaFile.getLibraryVisualsScenes().getSkeletons().values() )
        {
            //create new Action
            skeleton.resetIterator();
            JAGTLog.debug( "Creating new COLLADAAction with ID of ", skeleton.getRootJoint().getName(), "-action." );
            COLLADAAction currAction = new COLLADAAction( skeleton.getRootJoint().getName() + "-action" );
            currAction.setSkeleton( skeleton );
            // loop through each joint
            int animCount;
            for ( DaeJoint joint : currAction.getSkeleton() )
            {
                JAGTLog.debug( "Loading animations for joint ", joint.getName() );
                JAGTLog.increaseIndentation();
                // search the animations for each joint, max 9 ( 3 rots 3 trans 3 scales) //max 4 ( 3 rots and 1 trans )
                animCount = 0;
                ArrayList<AnimationChannel> translations = new ArrayList<AnimationChannel>();
                HashMap<Enum<?>, AnimationChannel> rotations = new HashMap<Enum<?>, AnimationChannel>();
                ArrayList<AnimationChannel> scales = new ArrayList<AnimationChannel>();
                for ( XMLAnimation animation : anims )
                {
                    if ( animCount < MAX_ANIMATIONS_PER_JOINT && animation.getTargetJoint().equals( joint.getName() ) )
                    {
                        JAGTLog.debug( "Loading animation ", animation.name, " of type ", animation.getType(),
                                ( animation.getType() == ChannelType.ROTATE ? ( " and of axis " + animation.getRotationAxis() ) : "" ) );
                        if ( animation.getType() == null )
                        {
                            animation.channels.get( 0 ).type = ChannelType.SCALE;
                        }

                        int stride = animation.getSource( animation.samplers.get( 0 ).getInput( "OUTPUT" ).source ).techniqueCommon.accessor.stride;
                        switch ( animation.getType() )
                        {
                            case TRANSLATE:
                                // it's a translation key frame
                                JAGTLog.debug( "Translation key frame..." );
                                translations.add( new AnimationChannel( joint,
                                        animation.getInput(),
                                        animation.getOutput(),
                                        null, //todo
                                        ChannelType.TRANSLATE,
                                        stride ) );
                                break;
                            case ROTATE:
                                // it's a rotation key frame
                                rotations.put( animation.getRotationAxis(),
                                        new AnimationChannel( joint,
                                                animation.getInput(),
                                                animation.getOutput(),
                                                animation.getRotationAxis(),
                                                ChannelType.ROTATE,
                                                stride ) );
                                break;
                            case SCALE:
                                // it's a scale key frame
                                scales.add( new AnimationChannel( joint,
                                        animation.getInput(),
                                        animation.getOutput(),
                                        null, //todo
                                        ChannelType.SCALE,
                                        stride ) );
                                break;
                        } // end of animation type switch
                        // update total anims for the joint
                        animCount++;
                    } //end of joint validation if loop
                } // end of animation iterator
                currAction.putTranslations( joint, mergeTranslations( joint, translations ) );
                currAction.putRotations( joint, mergeRotations( joint, rotations ) );
                currAction.putScales( joint, mergeScales( joint, scales ) );
            } // end of joint iterator

            // add current Action
            colAnims.put( currAction.getId(), currAction );
            JAGTLog.decreaseIndentation();
        } // end of skeleton iterator

        for ( Controller c : colladaFile.getLibraryControllers().getControllers().values() )
        {
            if ( c instanceof SkeletalController )
            {
                ( ( SkeletalController ) c ).libAnims = colladaFile.getLibraryAnimations();
            }
        }
    }

    private static AnimationChannel mergeTranslations( DaeJoint joint, ArrayList<AnimationChannel> translations )
    {
        //todo channel target full handling
        if ( translations.size() == 0 )
        {
            return new AnimationChannel( joint, new float[0], new float[0], null, ChannelType.TRANSLATE, 1 );
        }
        return ( translations.get( 0 ) );
    }

    private static AnimationChannel mergeRotations( DaeJoint joint, HashMap<Enum<?>, AnimationChannel> rotations )
    {
        if ( rotations.size() == 0 )
        {
            return new AnimationChannel( joint, new float[0], new float[0], null, ChannelType.ROTATE, 1 );
        }
        // ArrayList<AnimationData> merged = new ArrayList<AnimationData>( 1 );
        AnimationChannel result = null;
        if ( rotations.size() == 3 )  //todo
        {
            AnimationChannel rX = rotations.get( Axis.X );
            AnimationChannel rY = rotations.get( Axis.Y );
            AnimationChannel rZ = rotations.get( Axis.Z );

            if ( Arrays.equals( rX.getTimeline(), rY.getTimeline() ) &&
                    Arrays.equals( rY.getTimeline(), rZ.getTimeline() ) )
            {
//                Quaternion4f[] ql = new Quaternion4f[rX.getRotations().length];
//                for ( int i = 0; i < rX.getRotations().length; i++ )
//                {
//                    Quaternion4f q = mergeQuaternions( rX.getRotations()[ i ], rY.getRotations()[ i ], rZ.getRotations()[ i ] );
//                    ql[ i ] = q;
//                }

                result = new AnimationChannel( rX, rY.getOutput(), rZ.getOutput() );
            }
            else
            {
                return ( rotations.values().iterator().next() );
            }
        }
        else
        {
            return ( rotations.values().iterator().next() );
        }

        return ( result );
    }

    private static Quaternion4f mergeQuaternions( Quaternion4f q1, Quaternion4f q2, Quaternion4f q3 )
    {
//        Tuple3f t1 = Rotations.toEuler( q1 );
//        Tuple3f t2 = Rotations.toEuler( q2 );
//        Tuple3f t3 = Rotations.toEuler( q3 );

//        return Rotations.toQuaternion( t1.getX(), t2.getY(), t3.getZ() );
        Quaternion4f result = new Quaternion4f();
        result.mul( q1, q2 );

        result.mul( q3, result );
        result.normalize();

        return ( result );
    }

    private static AnimationChannel mergeScales( DaeJoint joint, ArrayList<AnimationChannel> scales )
    {
        //todo channel target full handling
        if ( scales.size() == 0 )
        {
            return new AnimationChannel( joint, new float[0], new float[0], null, ChannelType.SCALE, 1 );
        }
        return ( scales.get( 0 ) );
    }
}
