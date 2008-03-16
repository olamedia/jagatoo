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
package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.exceptions.ColladaLoaderException;
import org.jagatoo.loaders.models.collada.jibx.XMLChannel.ChannelType;

/**
 * A COLLADA animation.
 *
 * Child of LibraryAnimations.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLAnimation {

    public String id = null;
    public String name = null;

    public XMLAsset asset = null;

    public ArrayList<XMLSource> sources = new ArrayList<XMLSource>();
    public ArrayList<XMLSampler> samplers = new ArrayList<XMLSampler>();
    public ArrayList<XMLChannel> channels = new ArrayList<XMLChannel>();


    /**
     * Search a source with the specified id
     * @param id
     * @return the XML source
     */
    public XMLSource getSource( String id ) {
        for (XMLSource source : sources) {
            if( source.id.equals( id ) ) {
                return source;
            }
        }
        throw new ColladaLoaderException( "Could not found source with id " + id );
    }


    /**
     * @return the target bone name for this animation
     */
    public String getTargetBone() {
        return channels.get( 0 ).getTargetBone();
    }

    /**
     * @return a float array with all the times of the key frames
     */
    public float[] getInput() {
        return getSource( samplers.get( 0 ).getInput( "INPUT" ).source ).floatArray.floats;
    }

    /**
     * @return a float array with the values of all the key frames
     */
    public float[] getOutput() {
        return getSource( samplers.get( 0 ).getInput( "OUTPUT" ).source ).floatArray.floats;
    }

    /**
     * Tells if the animation contains transformation key frames or rotation key frames
     */
    public ChannelType getType() {
        return channels.get( 0 ).type;
    }


    /**
     * @return the rotation axis of the animation.
     * It only works if the animation if for rotation
     */
    public KeyFrame.Axis getRotationAxis() {
        return channels.get( 0 ).getRotationAxis();
    }

}
