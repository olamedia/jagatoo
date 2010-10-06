/**
 * Copyright (c) 2007-2010, JAGaToo Project Group all rights reserved.
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
public enum BlendFunction
{
    ZERO( OGL.GL_ZERO ),
    ONE( OGL.GL_ONE ),
    SRC_ALPHA( OGL.GL_SRC_ALPHA ),
    ONE_MINUS_SRC_ALPHA( OGL.GL_ONE_MINUS_SRC_ALPHA ),
    DST_COLOR( OGL.GL_DST_COLOR ),
    ONE_MINUS_DST_COLOR( OGL.GL_ONE_MINUS_DST_COLOR ),
    DST_ALPHA( OGL.GL_DST_ALPHA ),
    ONE_MINUS_DST_ALPHA( OGL.GL_ONE_MINUS_DST_ALPHA ),
    SRC_ALPHA_SATURATE( OGL.GL_SRC_ALPHA_SATURATE ),
    SRC_COLOR( OGL.GL_SRC_COLOR ),
    ONE_MINUS_SRC_COLOR( OGL.GL_ONE_MINUS_SRC_COLOR ),
    CONSTANT_COLOR( OGL.GL_CONSTANT_COLOR ),
    ONE_MINUS_CONSTANT_COLOR( OGL.GL_ONE_MINUS_CONSTANT_COLOR ),
    CONSTANT_ALPHA( OGL.GL_CONSTANT_ALPHA ),
    ONE_MINUS_CONSTANT_ALPHA( OGL.GL_ONE_MINUS_CONSTANT_ALPHA ),
    
    /*
    BLEND_CONSTANT_COLOR_EXT(  ),
    BLEND_ONE_MINUS_CONSTANT_COLOR_EXT(  ),
    BLEND_CONSTANT_ALPHA_EXT(  ),
    BLEND_ONE_MINUS_CONSTANT_ALPHA_EXT(  ),
    */
    ;
    
    private final int glValue;
    
    public final int toOpenGL()
    {
        return ( glValue );
    }
    
    private BlendFunction( int glValue )
    {
        this.glValue = glValue;
    }
}
