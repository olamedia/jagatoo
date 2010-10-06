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

/**
 * The {@link TextureFormat} is a simple enum, that holds the different,
 * possible formats of a texture.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum TextureFormat
{
    /**
     * An opaque colored texture without alpha channel. 
     */
    RGB( false ),
    
    /**
     * A transparent colored texture with alpha channel. 
     */
    RGBA( true ),
    
    /**
     * Texture type with only luminance (not RGB, no alpha channel).
     */
    LUMINANCE( false ),
    
    /**
     * Texture type with depth information.
     */
    DEPTH( false );
    
    private final boolean hasAlpha;
    
    public final boolean hasAlpha()
    {
        return ( hasAlpha );
    }
    
    public static final TextureFormat getFormat( TextureImageFormat tiFormat )
    {
        switch ( tiFormat )
        {
            case RGB:
                return ( TextureFormat.RGB );
                
            case DEPTH:
                return ( TextureFormat.DEPTH );
                
            case LUMINANCE:
                return ( TextureFormat.LUMINANCE );
                
            default:
                return ( TextureFormat.RGBA );
        }
    }
    
    public final TextureImageFormat getDefaultTextureImageFormat()
    {
        switch ( this )
        {
            case DEPTH:
                return ( TextureImageFormat.DEPTH );
                
            case LUMINANCE:
                return ( TextureImageFormat.LUMINANCE );
                
            case RGB:
                return ( TextureImageFormat.RGB );
                
            case RGBA:
                return ( TextureImageFormat.RGBA );
                
            default:
                throw new Error( "Unsupported Format for conversion: " + this );
        }
    }
    
    private TextureFormat( boolean hasAlpha )
    {
        this.hasAlpha = hasAlpha;
    }
}
