/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.md2;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A MD2 model template that can be used to create instances.
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2ModelPrototype
{
    private int loadFlags;
    /** A list of the frames in this model */
    private MD2RenderedFrame[] frames;
    /** The of frames in indexed */
    private Map<String, List<MD2RenderedFrame>> animMap = new HashMap<String, List<MD2RenderedFrame>>();
    /** The appearance for this model */
    private String skinName;
    
    /**
     * Creates new MD2ModelDefinition
     * 
     * @param frames The frames of this model
     */
    public MD2ModelPrototype( int loadFlags, MD2RenderedFrame[] frames, String skinName )
    {
        this.loadFlags = loadFlags;
        this.skinName = skinName;
        this.frames = frames;
        
        for ( int i = 0; i < frames.length; i++ )
        {
            // check as frames may have been filtered out
            if ( frames[ i ] != null )
            {
                String anim = frames[ i ].getAnimName();
                //String frame = frames[ i ].getAnimIndex();
                
                List<MD2RenderedFrame> v = animMap.get( anim );
                if ( v == null )
                {
                    v = new ArrayList<MD2RenderedFrame>();
                    animMap.put( anim, v );
                    v.add( frames[ i ] );
                }
                else
                {
                    v.add( frames[ i ] );
                }
            }
        }
    }
    
    /**
     * @return the the loadFlags, that were used to load this model
     */
    public int getLoadFlags()
    {
        return( loadFlags );
    }
    
    /**
     * Get the skin name assigned to the model
     * 
     * @return The name of the skin applied to the model
     */
    public String getSkinName()
    {
        return( skinName );
    }
    
    /**
     * Get a list of frames with a specified tag
     * 
     * @param name The tag of the animation list to retrieve
     * @return An array list of rendered frames
     */
    public List<MD2RenderedFrame> getAnimation(String name)
    {
        return( animMap.get( name ) );
    }
    
    /**
     * Retrieve a list of animations in this file
     * 
     * @return A list of animation names
     */
    public List<String> getAnimationNames()
    {
        List<String> names = new ArrayList<String>( animMap.size() );
        
        for ( String name: animMap.keySet() )
        {
            names.add( name );
        }
        
        return( names );
    }
    
    /**
     * @return true, if the Model contains at least one animation.
     */
    public boolean hasAnimations()
    {
        return( !animMap.isEmpty() );
    }
    
    /**
     * Check whether the model has vertex normal data
     * 
     * @return Whether the frames have normal data
     */
    public boolean hasNormals()
    {
        if ( ( ( frames == null ) || ( frames.length < 1 ) ) || ( frames[ 0 ].getStripsNorms() == null ) )
            return( false );
        else
            return( true );
    }
}
