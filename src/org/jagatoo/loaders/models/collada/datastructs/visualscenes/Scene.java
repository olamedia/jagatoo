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
import java.util.Iterator;

/**
 * A COLLADA Scene
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Scene
{
    /** The id of the scene */
    private final String id;
    
    /** The name of the scene */
    private String name;
    
    /** A map of all top-level nodes */
    private final HashMap<String, DaeNode> nodes = new HashMap<String, DaeNode>();
    
    /**
     * @return the id.
     */
    public String getId()
    {
        return ( id );
    }
    
    /**
     * @return the name.
     */
    public String getName()
    {
        return ( name );
    }
    
    /**
     * @return the nodes.
     */
    public HashMap<String, DaeNode> getNodes()
    {
        return ( nodes );
    }

    public DaeNode findNode( String id )
    {
        for ( DaeNode node : nodes.values() )
        {
            DaeNode n = findNode( id, node );
            if ( n != null )
            {
                return ( n );
            }

        }

        return ( null );
    }

    private static DaeNode findNode( String id, DaeNode node )
    {
        if ( id.equals( node.getId() ) )
        {
            return ( node );
        }
        for ( DaeNode child : node.getChildren() )
        {
            DaeNode n = findNode( id, child );
            if ( n != null )
            {
                return ( n );
            }
        }
        return ( null );
    }

    /**
     * Creates a new COLLADAScene.
     * 
     * @param id The id of the scene
     * @param name The name of the scene
     */
    public Scene( String id, String name )
    {
        this.id = id;
        this.name = name;
    }
}
