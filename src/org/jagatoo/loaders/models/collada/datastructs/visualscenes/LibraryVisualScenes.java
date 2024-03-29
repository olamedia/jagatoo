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
package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;

/**
 * Visual scenes in a COLLADA File
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenes
{
    /** Map of all scenes */
    private final HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    
    /** Map of all skeletons */
    private final HashMap<String, DaeSkeleton> skeletons = new HashMap<String, DaeSkeleton>();

    /** Map */
    private final HashMap<String, String> controllerIdToRootJointId = new HashMap<String, String>();

    /**
     * @return the scenes
     */
    public final HashMap<String, Scene> getScenes()
    {
        return ( scenes );
    }
    
    /**
     * @return the skeletons
     */
    public final HashMap<String, DaeSkeleton> getSkeletons()
    {
        return ( skeletons );
    }

    public HashMap<String, String> getControllerIdToRootJointId()
    {
        return ( controllerIdToRootJointId );
    }

    /**
     * Creates a new COLLADALibraryVisualScenes
     */
    public LibraryVisualScenes()
    {
    }
}
