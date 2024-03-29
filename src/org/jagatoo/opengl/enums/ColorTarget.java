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
package org.jagatoo.opengl.enums;

import org.jagatoo.opengl.OGL;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum ColorTarget
{
    /**
     * Disables use of per-vertex colors for replacement of any of the material color.
     */
    NONE( OGL.GL_NONE ),
    
    /**
     * Specifies that per-vertex colors (if any) or color from
     * ColoringAttributes replace the ambient material color
     */
    AMBIENT( OGL.GL_AMBIENT ),
    
    /**
     * Specifies that per-vertex colors (if any) or color from
     * ColoringAttributes replace the emissive material color
     */
    EMISSIVE( OGL.GL_EMISSION ),
    
    /**
     * Specifies that per-vertex colors (if any) or color from
     * ColoringAttributes replace the diffuse material color
     */
    DIFFUSE( OGL.GL_DIFFUSE ),
    
    /**
     * Specifies that per-vertex colors (if any) or color from
     * ColoringAttributes replace the specular material color
     */
    SPECULAR( OGL.GL_SPECULAR ),
    
    /**
     * Specifies that per-vertex colors (if any) or color from
     * ColoringAttributes replace both the ambient and the diffuse material
     * color
     */
    AMBIENT_AND_DIFFUSE( OGL.GL_AMBIENT_AND_DIFFUSE ),
    ;
    
    private final int glValue;
    
    public final int toOpenGL()
    {
        return ( glValue );
    }
    
    private ColorTarget( int glValue )
    {
        this.glValue = glValue;
    }
}
