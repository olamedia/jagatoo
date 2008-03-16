/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.datatypes;

import org.jagatoo.datatypes.util.RepositionListener2i;
import org.openmali.vecmath2.Tuple2i;


/**
 * A Positioned2i class provides getters and setters for the position.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface Positioned2i
{
    /**
     * Adds a new RepositionListener2i. The event is fired from the rendering
     * thread, if necessary.
     * 
     * @param listener
     */
    public void addRepositionListener( RepositionListener2i listener );
    
    /**
     * Removes the given RepositionListener2i from the List, if it was present.
     * 
     * @param listener
     */
    public void removeRepositionListener( RepositionListener2i listener );
    
    /**
     * Sets the position.
     * 
     * @param left
     * @param top
     * 
     * @return true, if the location actually has changed
     */
    public boolean setLocation( int left, int top );
    
    /**
     * Sets the position.
     * 
     * @param location
     * 
     * @return true, if the location actually has changed
     */
    public boolean setLocation( Tuple2i location );
    
    /**
     * @return the position
     */
    public Tuple2i getLocation();
    
    /**
     * @return the left position
     */
    public int getLeft();
    
    /**
     * @return the top position
     */
    public int getTop();
}
