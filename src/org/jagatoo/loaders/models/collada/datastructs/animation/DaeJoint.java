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
package org.jagatoo.loaders.models.collada.datastructs.animation;

import java.util.ArrayList;

import org.openmali.vecmath2.Matrix4f;
import org.jagatoo.loaders.models.collada.AnimationChannel;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.DaeNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADATransform;
import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;

/**
 * A Joint (of a Skeleton)
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class DaeJoint extends DaeNode
{
    /** The sub-id of this joint */
    private final String sid;

    private AnimationChannel translations;
    private AnimationChannel rotations;
    private AnimationChannel scales;

    private AnimationChannel matrices;

    private short index;

    public short getIndex()
    {
        return ( index );
    }

    public void setIndex( short index )
    {
        this.index = index;
    }

    public final String getSourceId()
    {
        return ( sid );
    }

    /**
     * Removes a child joint.
     * 
     * @param joint
     */
    public void removeChild( DaeJoint joint )
    {
        children.remove( joint );
    }
    
    /**
     * @return the number of children of this joint.
     */
    public final int numChildren()
    {
        return ( children.size() );
    }
    
    /**
     * Gets a joint by index.
     * 
     * @param i
     *                The index of the joint you want to get.
     * @return The joint :)
     */
    public final DaeJoint getChild( int i )
    {
        return ( ( DaeJoint ) children.get( i ) );
    }

    public void setTranslations( AnimationChannel translations )
    {
        this.translations = translations;
    }

    public AnimationChannel getTranslations()
    {
        return ( translations );
    }

    public void setRotations( AnimationChannel rotations )
    {
        this.rotations = rotations;
    }

    public AnimationChannel getRotations()
    {
        return ( rotations );
    }

    public void setScales( AnimationChannel scales )
    {
        this.scales = scales;
    }

    public AnimationChannel getScales()
    {
        return ( scales );
    }

    public static DaeJoint findRoot( DaeJoint joint )
    {
        if ( isRoot( joint ) )
        {
            return ( joint );
        }
        return ( findRoot( ( DaeJoint ) joint.getParentNode() ) );
    }

    public static boolean isRoot( DaeJoint joint )
    {
        DaeNode parent = joint.getParentNode();
        return ( parent == null || parent.getClass() == DaeNode.class );
    }

    public void setMatrices( AnimationChannel m )
    {
        this.matrices = m;
    }

    public AnimationChannel getMatrices()
    {
        return ( matrices );
    }

    /**
     * Creates a new DaeJoint.
     *
     * @param file       the COLLADA file this node belongs to
     * @param parentNode
     * @param id         The id of this Node
     * @param sid
     * @param name       The name of this Node
     * @param transform  The transform of this Node
     */
    public DaeJoint( AssetFolder file, DaeNode parentNode, String id, String sid, String name, COLLADATransform transform )
    {
        super( file, parentNode, id, name, transform );
        this.sid = ( sid == null ) ? id : sid;
    }
}
