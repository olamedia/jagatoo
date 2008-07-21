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
package org.jagatoo.loaders.textures;

import org.jagatoo.datatypes.NamableObject;
import org.jagatoo.opengl.OGL;

/**
 * Abstraction of a Texture.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface AbstractTexture extends NamableObject
{
    public static enum Type
    {
        TEXTURE_1D( OGL.GL_TEXTURE_1D ),
        TEXTURE_2D( OGL.GL_TEXTURE_2D ),
        TEXTURE_3D( OGL.GL_TEXTURE_3D ),
        TEXTURE_CUBE_MAP( OGL.GL_TEXTURE_CUBE_MAP ),
        ;
        
        private final int glValue;
        
        public final int toOpenGL()
        {
            return( glValue );
        }
        
        private Type( int glValue )
        {
            this.glValue = glValue;
        }
    }
    
    public static enum Format
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
            return( hasAlpha );
        }
        
        public static final Format getFormat( org.jagatoo.loaders.textures.AbstractTextureImage.Format tiFormat )
        {
            switch ( tiFormat )
            {
                case RGB:
                    return( org.jagatoo.loaders.textures.AbstractTexture.Format.RGB );
                    
                case DEPTH:
                    return( org.jagatoo.loaders.textures.AbstractTexture.Format.DEPTH );
                    
                case LUMINANCE:
                    return( org.jagatoo.loaders.textures.AbstractTexture.Format.LUMINANCE );
                    
                default:
                    return( org.jagatoo.loaders.textures.AbstractTexture.Format.RGBA );
            }
        }
        
        public final AbstractTextureImage.Format getDefaultTextureImageFormat()
        {
            switch ( this )
            {
                case DEPTH:
                    return( org.jagatoo.loaders.textures.AbstractTextureImage.Format.DEPTH );
                    
                case LUMINANCE:
                    return( org.jagatoo.loaders.textures.AbstractTextureImage.Format.LUMINANCE );
                    
                case RGB:
                    return( org.jagatoo.loaders.textures.AbstractTextureImage.Format.RGB );
                    
                case RGBA:
                    return( org.jagatoo.loaders.textures.AbstractTextureImage.Format.RGBA );
                    
                default:
                    throw new Error( "Unsupported Format for conversion: " + this );
            }
        }
        
        private Format( boolean hasAlpha )
        {
            this.hasAlpha = hasAlpha;
        }
    }
    
    /**
     * Sets the key-String, by which this texture has been cached in the TextureLoader.
     */
    public void setCacheKey( String key );
    
    /**
     * @return the key-String, by which this texture has been cached in the TextureLoader.
     */
    public String getCacheKey();
    
    /**
     * Sets the Texture's name (the String, that has been used to load it through TextureLoader).
     */
    public void setName( String name );
    
    /**
     * @return the Texture's name (the String, that has been used to load it through TextureLoader).
     */
    public String getName();
    
    /**
     * @return the Texture's {@link Type}.
     */
    public Type getType();
    
    /**
     * @return the Texture's actual width.
     */
    public int getWidth();
    
    /**
     * @return the Texture's actual height.
     */
    public int getHeight();
    
    /**
     * @return the Texture's {@link Format}.
     */
    public Format getFormat();
    
    public void setImage( int level, AbstractTextureImage image );
    
    public AbstractTextureImage getImage( int level );
    
    public int getImagesCount();
}
