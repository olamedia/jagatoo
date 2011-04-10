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
import java.util.HashMap;
import java.util.Iterator;

/**
 * A Skeleton. It contains a root joint, which can have several children
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class DaeSkeleton implements Iterable<DaeJoint>
{
    /** The root joint : there's only one (for simple implementation) */
    private DaeJoint rootJoint;
    
    private final HashMap<String, DaeJoint> jointMap = new HashMap<String, DaeJoint>();
    /**
     * Iterator for easy managment of the joints
     */
    private SkeletonIterator iterator = new SkeletonIterator(this);

    /**
     * @return the rootJoint
     */
    public final DaeJoint getRootJoint()
    {
        return ( rootJoint );
    }
    
    public final DaeJoint getJointBySourceId( String sourceId )
    {
        return ( jointMap.get( sourceId ) );
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<DaeJoint> iterator()
    {
    	if ( this.iterator == null )
    	{
    		this.iterator = new SkeletonIterator( this );
    	}
    	
    	if ( !this.iterator.hasNext() )
    	{
    		resetIterator();
    	}
    	
        return ( this.iterator );
    }
    
    public void resetIterator()
    {
    	this.iterator = new SkeletonIterator( this );
    	this.iterator.reset();
    }

    /**
     * Creates a new Skeleton.
     * 
     * @param rootJoint
     *                The root joint
     * @param jointList
     */
    public DaeSkeleton( DaeJoint rootJoint, ArrayList<DaeJoint> jointList )
    {
        this.rootJoint = rootJoint;
        
        for ( int i = 0; i < jointList.size(); i++ )
        {
            final DaeJoint joint = jointList.get( i );
            
            this.jointMap.put( joint.getSourceId(), joint );
        }
    }
}
