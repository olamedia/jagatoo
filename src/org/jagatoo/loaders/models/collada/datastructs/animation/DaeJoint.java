/**
 * Copyright (c) 2007-2010, JAGaToo Project Group all rights reserved.
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

/**
 * A Joint (of a Skeleton)
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class DaeJoint
{
    /** The sub-id of this joint */
    private final String sid;
    
    /** The name of this joint */
    private final String name;

    /** The bind matrix */
    public final Matrix4f bindMatrix;

    /** Children (optional) */
    private ArrayList<DaeJoint> children;

    private AnimationChannel translations;
    private AnimationChannel rotations;
    private AnimationChannel scales;

    private short index;

    public short getIndex()
    {
        return index;
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
     * @return the name of this joint.
     */
    public final String getName() 
    {
        return ( name );
    }

    /**
     * Adds a child joint.
     * 
     * @param joint
     */
    public void addChild( DaeJoint joint )
    {
        if ( children == null )
        {
            children = new ArrayList<DaeJoint>();
        }
        
        children.add( joint );
    }
    
    /**
     * Removes a child joint.
     * 
     * @param joint
     */
    public void removeChild( DaeJoint joint )
    {
        if ( children != null )
        {
            children.remove( joint );
        }
    }
    
    /**
     * @return the number of children of this joint.
     */
    public final int numChildren()
    {
        return ( ( children == null ) ? 0 : children.size() );
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
        return ( ( children == null ) ? null : children.get( i ) );
    }

    public void setTranslations( AnimationChannel translations )
    {
        this.translations = translations;
    }

    public AnimationChannel getTranslations()
    {
        return translations;
    }

    public void setRotations( AnimationChannel rotations )
    {
        this.rotations = rotations;
    }

    public AnimationChannel getRotations()
    {
        return rotations;
    }

    public void setScales( AnimationChannel scales )
    {
        this.scales = scales;
    }

    public AnimationChannel getScales()
    {
        return scales;
    }

    @Override
    public String toString()
    {
        return ( name );
    }
    
    /**
     * Create a new Joint
     * 
     * @param sid
     * @param name
     *                The name of this joint
     * @param matrix joint bind pose matrix
     */
    public DaeJoint( String sid, String name, Matrix4f matrix )
    {
        this.sid = sid;
        this.name = name;
        
        this.bindMatrix = matrix;
    }
}
