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

import java.util.ArrayList;

import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.stax.XMLNode;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Vector3f;

/**
 * A loader for skeletons.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class SkeletonLoader
{
    /**
     * Loads a Joint/Bone, and its children.
     *
     * @param node
     * @param upVector
     * @param joint
     * @param jointList
     */
    private static void loadJoint( XMLNode node, Vector3f upVector, DaeJoint joint, ArrayList<DaeJoint> jointList )
    {
        jointList.add( joint );

        JAGTLog.debug( "=====================================" );
        JAGTLog.debug( "[[Joint]] " + joint.getName() );
        JAGTLog.debug( "bind matrix = ", node.matrix.matrix4f );
        if ( node.childrenList == null || node.childrenList.isEmpty() )
        {
            return;
        }

        for ( XMLNode child : node.childrenList )
        {
            //todo here can be base node
            DaeJoint newJoint = new DaeJoint( child.sid, child.name, child.matrix.matrix4f );
            joint.addChild( newJoint );
            loadJoint( child, upVector, newJoint, jointList );
        }
    }

    /**
     * Loads a whole Skeleton.
     *
     * @param rootNode
     * @param upVector
     * @param matrix
     * @return the skeleton
     */
    public static DaeSkeleton loadSkeleton( XMLNode rootNode, Vector3f upVector, Matrix4f matrix )
    {
        final Matrix4f localToWorld = new Matrix4f( );
        // Get the localToWorld matrix
        localToWorld.mul(matrix, rootNode.matrix.matrix4f );
        // Create the root joint
        DaeJoint rootJoint = new DaeJoint( rootNode.sid, rootNode.name, localToWorld );

        ArrayList<DaeJoint> jointList = new ArrayList<DaeJoint>();

        loadJoint( rootNode, upVector, rootJoint, jointList );

        return ( new DaeSkeleton( rootJoint, jointList ) );
    }
}
