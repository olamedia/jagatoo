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
     * @param joint
     * @param jointList
     */
    private static void loadJoint( XMLNode node, DaeJoint joint, ArrayList<DaeJoint> jointList )
    {
        jointList.add( joint );
                                                                        
        if ( node.childrenList == null || node.childrenList.isEmpty() || node.type == XMLNode.Type.NODE )
        {
            return;
        }

        for ( XMLNode child : node.childrenList )
        {
            DaeJoint newJoint = new DaeJoint( joint.getFile(), joint, child.id, child.sid, child.name, child.transform );
            joint.addChild( newJoint );
            loadJoint( child, newJoint, jointList );
        }
    }

    /**
     * Loads a whole Skeleton.
     *
     * @param rootNode
     * @param rootJoint
     * @return the skeleton
     */
    public static DaeSkeleton loadSkeleton( XMLNode rootNode, DaeJoint rootJoint )
    {
        ArrayList<DaeJoint> jointList = new ArrayList<DaeJoint>();

        loadJoint( rootNode, rootJoint, jointList );

        return ( new DaeSkeleton( rootJoint, jointList ) );
    }
}
