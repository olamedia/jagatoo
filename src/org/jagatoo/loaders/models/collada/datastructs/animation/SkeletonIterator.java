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
import java.util.Iterator;
import java.util.List;

/**
 * Iterator for an easy management of a {@link DaeSkeleton}'s {@link DaeJoint}s.
 * 
 * @author Matias Leone
 */
public class SkeletonIterator implements Iterator<DaeJoint>
{
	private final DaeSkeleton skeleton;
	private final ArrayList<DaeJoint> joints = new ArrayList<DaeJoint>();
	private int currentIndex;

    /**
	 * {@inheritDoc}
	 */
	public boolean hasNext()
	{
		return ( ( currentIndex + 1 ) < joints.size() );
	}
	
    /**
     * {@inheritDoc}
     */
	public DaeJoint next()
	{
		return ( joints.get( ++currentIndex ) );
	}
	
    /**
     * {@inheritDoc}
     */
	public void remove()
	{
		throw new UnsupportedOperationException( "Can not remove a joint" );
	}
    
    /**
     * Fills the joint list with a depth first criteria.
     * 
     * @param joint
     * @param jointList
     */
    private void fillJointList( DaeJoint joint, List<DaeJoint> jointList )
    {
        jointList.add( joint );
        
        for ( int i = 0; i < joint.numChildren(); i++ )
        {
            fillJointList( joint.getChild( i ), jointList );
        }
    }

    /**
     * Reset the iterator to it's initial position
     */
    public void reset()
    {
        DaeJoint joint = this.skeleton.getRootJoint();
        joints.clear();
        fillJointList( joint, joints );
        currentIndex = -1;
    }

    /**
     * Creates an iterator for the joints of the skeleton
     * @param skeleton
     */
    public SkeletonIterator( DaeSkeleton skeleton )
    {
        this.skeleton = skeleton;
    }
}
