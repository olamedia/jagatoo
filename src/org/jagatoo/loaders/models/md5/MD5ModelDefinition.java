/**
 * Copyright (c) 2006 KProject
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'KProject' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jagatoo.loaders.models.md5;

import org.jagatoo.loaders.models.md5.animation.MD5Animation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kman
 */
public class MD5ModelDefinition
{
    protected String mesh;
    protected String texture;
    protected Map< String, MD5Animation > animations = new HashMap< String, MD5Animation >();
    
    /**
     * Creates a new instance of MD5ModelDefinition
     */
    public MD5ModelDefinition()
    {
    }
    
    public void setMesh( String mesh )
    {
        this.mesh = mesh;
    }
    
    public String getMesh()
    {
        return( mesh );
    }
    
    public void setTexture( String texture )
    {
        this.texture = texture;
    }
    
    public String getTexture()
    {
        return( texture );
    }
    
    public void setAnimations( Map< String, MD5Animation > animations )
    {
        this.animations = animations;
    }
    
    public Map< String, MD5Animation > getAnimations()
    {
        return( animations );
    }
}
