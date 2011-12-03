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
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.DaeNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Scene;
import org.jagatoo.loaders.models.collada.stax.XMLAnimation;
import org.jagatoo.loaders.models.collada.stax.XMLChannel;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryAnimations;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Library animations loader.
 *
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryAnimationsLoader
{
    /**
     * Loads the library of animations.
     *
     * @param colladaFile
     * @param libAnim
     */
    public static void loadLibraryAnimations( AssetFolder colladaFile, XMLLibraryAnimations libAnim )
    {
        HashMap<String, COLLADAAction> colAnims = colladaFile.getLibraryAnimations().getAnimations();
        Scene scene = colladaFile.getLibraryVisualsScenes().getScenes().values().iterator().next();
        HashSet<DaeSkeleton> animatedSkels = new HashSet<DaeSkeleton>();
        HashMap<DaeJoint, DaeSkeleton> rootToSkeleton = new HashMap<DaeJoint, DaeSkeleton>();
        for ( DaeSkeleton skeleton : colladaFile.getLibraryVisualsScenes().getSkeletons().values() )
        {
            rootToSkeleton.put( skeleton.getRootJoint(), skeleton );
        }
        COLLADAAction action = null;
        for ( XMLAnimation anim : libAnim.animations.values() )
        {
            AnimationChannel ac = null;
            DaeJoint root = null;
            DaeSkeleton skeleton = null;
            DaeNode target = null;
            for ( int c = 0; c < anim.channels.size(); c++ )
            {
                XMLChannel channel = anim.channels.get( c );
                if ( target == null )
                {
                    target = scene.findNode( channel.getTargetNodeId() );
                }
                if ( target instanceof DaeJoint )
                {
                    Object targetValue = target.getCOLLADATransform().get( channel.getTransElemSid() );
                    if ( targetValue == null )
                    {
                        break; //an error or extra/technique attr.
                    }
                    if ( root == null )
                    {
                        root = DaeJoint.findRoot( ( DaeJoint ) target );
                        skeleton = rootToSkeleton.get( root );
                        if ( animatedSkels.add( skeleton ) ) //currently 1 anim. group (action) per skeleton
                        {
                            skeleton.resetIterator();
                            if ( action == null )
                            {
                                String id = skeleton.getRootJoint().getId();
                                action = new COLLADAAction( id + "-action" );
                                action.setSkeleton( skeleton );
                                colAnims.put( action.getId(), action );
                            }
                        }
                    }
                    float[] timeline = anim.getInput( c );
                    float[] output = anim.getOutput( c );
                    int stride = anim.getSource( anim.samplers.get( c ).getInput( "OUTPUT" ).source ).techniqueCommon.accessor.stride;
                    int i = channel.getTransElemIndexI();
                    int j = channel.getTransElemIndexJ();
                    int ord = target.getCOLLADATransform().getElementOrder( targetValue );
                    if ( ac == null )
                    {
                        ac = AnimationChannel.create( ( DaeJoint ) target, timeline, output, stride, i, j, targetValue, ord );
                    }
                    else
                    {
                        ac.update( timeline, output, stride, i, j, targetValue );
                    }
                }
                else
                {
                    //todo
                }
            } //channels loop
            if ( ac != null )
            {
                switch ( ac.getType() )
                {
                    case TRANSLATE:
                        action.putTranslations( target, ac );
                        break;
                    case ROTATE:
                        action.putRotations( target, ac );
                        break;
                    case SCALE:
                        action.putScales( target, ac );
                        break;
                    case MATRIX:
                        action.putMatrices( target, ac );
                        break;
                    default:
                        throw new Error( "Unknown channel type: " + ac.getType() );
                }
            }
        }//animations loop

        for ( COLLADAAction ca : colAnims.values() )
        {
            ca.prepareJoints();
        }

        for ( Controller c : colladaFile.getLibraryControllers().getControllers().values() )
        {
            if ( c instanceof SkeletalController )
            {
                ( ( SkeletalController ) c ).libAnims = colladaFile.getLibraryAnimations();
            }
        }
    }
}
