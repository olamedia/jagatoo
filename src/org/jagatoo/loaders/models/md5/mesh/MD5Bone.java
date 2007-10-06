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
package org.jagatoo.loaders.models.md5.mesh;

import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;

/**
 * @author kman
 */
public class MD5Bone
{
    public int boneId;
    public String name;
    public int parentId;
    public Vector3f translation = new Vector3f();
    public Quaternion4f rotation = new Quaternion4f();
    
    /**
     * Creates a new instance of MD5Bone
     */
    public MD5Bone()
    {
    }
    
    public void setBoneId( int boneId )
    {
        this.boneId = boneId;
    }
    
    public int getBoneId()
    {
        return( boneId );
    }
    
    public void setName( String name )
    {
        this.name = name;
    }
    
    public String getName()
    {
        return( name );
    }
    
    public void setParentId( int parentId )
    {
        this.parentId = parentId;
    }
    
    public int getParentId()
    {
        return( parentId );
    }
    
    public boolean isRoot()
    {
        return( parentId == -1 );
    }
    
    public void setTranslation( Vector3f translation )
    {
        this.translation = translation;
    }
    
    public Vector3f getTranslation()
    {
        return( translation );
    }
    
    public void setRotation( Quaternion4f rotation )
    {
        this.rotation = rotation;
    }
    
    public Quaternion4f getRotation()
    {
        return( rotation );
    }
}
