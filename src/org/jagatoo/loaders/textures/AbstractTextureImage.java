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

import java.nio.ByteBuffer;

import org.jagatoo.opengl.OGL;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface AbstractTextureImage
{
    public static enum Format
    {
        /**
         * Texture contains only intensity values.
         */
        INTENSITY( 1, false, false, OGL.GL_INTENSITY ),
        
        /**
         * Texture contains only luminance values.
         */
        LUMINANCE( 1, false, false, OGL.GL_LUMINANCE ),
        
        /**
         * Texture contains only alpha values.
         */
        ALPHA( 1, true, false, OGL.GL_ALPHA ),
        
        /**
         * Texture contains luminance and alpha values.
         */
        LUMINANCE_ALPHA( 2, true, false, OGL.GL_LUMINANCE_ALPHA ),
        
        /**
         * Texture contains 24 bit depth.
         */
        DEPTH( 3, false, false, OGL.GL_DEPTH_COMPONENT ),
        
        /**
         * Texture contains red, green and blue color values.
         */
        RGB( 3, false, false, OGL.GL_RGB ),
        
        /**
         * Texture contains red, green, blue and alpha color values.
         */
        RGBA( 4, true, false, OGL.GL_RGBA ),
        
        /**
         * compressed texture format. Uses S3TC_DXT1 compression.
         */
        RGBA_DXT1( -1, true, true, OGL.GL_RGBA ),
        
        /**
         * compressed texture format. Uses S3TC_DXT3 compression.
         */
        RGBA_DXT3( -1, true, true, OGL.GL_RGBA ),
        
        /**
         * compressed texture format. Uses S3TC_DXT5 compression.
         */
        RGBA_DXT5( -1, true, true, OGL.GL_RGBA ),
        
        /**
         * compressed texture format. Uses S3TC_DXT1 compression.
         */
        RGB_DXT1( -1, false, true, OGL.GL_RGB );
        
        private final int pixelSize;
        private final boolean hasAlpha;
        private final boolean isCompressed;
        private final int glValue;
        
        public final int getPixelSize()
        {
            return( pixelSize );
        }
        
        public final boolean hasAlpha()
        {
            return( hasAlpha );
        }
        
        public final boolean isCompressed()
        {
            return( isCompressed );
        }
        
        public final int toOpenGL()
        {
            return( glValue );
        }
        
        private Format( int pixelSize, boolean hasAlpha, boolean isCompressed, int glValue )
        {
            this.pixelSize = pixelSize;
            this.hasAlpha = hasAlpha;
            this.isCompressed = isCompressed;
            this.glValue = glValue;
        }
    }
    
    public static enum InternalFormat
    {
        /**
         * Internal format hint.<br>
         * each pixel contains three eight bit channels, one each for
         * red, green and blue.
         */
        RGB( false, false, OGL.GL_RGB ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains four eight bit channels, one each for
         * red, green, blue and alpha.
         */
        RGBA( true, false, OGL.GL_RGBA ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains three eight bit channels, one each for
         * red, green and blue.
         */
        RGB8( false, false, OGL.GL_RGB8 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains four eight bit channels, one each for
         * red, green, blue and alpha.
         */
        RGBA8( true, false, OGL.GL_RGBA8 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains three five bit channels, one each for
         * red, green and blue.
         */
        RGB5( false, false, OGL.GL_RGB5 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains three five bit channels, one each for
         * red, green and blue. Also a one bit channel for alpha.
         */
        RGB5_A1( true, false, OGL.GL_RGB5_A1 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains three four bit channels, one each for
         * red, green, blue and alpha.
         */
        RGB4( true, false, OGL.GL_RGB4 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains four four bit channels, one each for
         * red, green, blue and alpha.
         */
        RGBA4( true, false, OGL.GL_RGBA4 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains luminance and alpha.
         */
        LUM_ALPHA( true, false, OGL.GL_LUMINANCE_ALPHA ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains two four bit channels, one each for
         * luminance and alpha.
         */
        LUM4_ALPHA4( true, false, OGL.GL_LUMINANCE4_ALPHA4 ),
        
        /**
         * each pixel contains two eight bit channels, one each for
         * luminance and alpha.
         */
        LUM8_ALPHA8( true, false, OGL.GL_LUMINANCE8_ALPHA8 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains two three bit channels, one each for
         * red and green, and a two bit channel for blue.
         */
        R3_G3_B2( false, false, OGL.GL_R3_G3_B2 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 16 bit depth.
         */
        DEPTH16( false, false, OGL.GL_DEPTH_COMPONENT16 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 24 bit depth.
         */
        DEPTH24( false, false, OGL.GL_DEPTH_COMPONENT24 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 32 bit depth.
         */
        DEPTH32( false, false, OGL.GL_DEPTH_COMPONENT32 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains only luminance.
         */
        LUMINANCE( false, false, OGL.GL_LUMINANCE ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 4 bit luminance.
         */
        LUMINANCE4( false, false, OGL.GL_LUMINANCE4 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 8 bit luminance.
         */
        LUMINANCE8( false, false, OGL.GL_LUMINANCE8 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains only intensity.
         */
        INTENSITY( false, false, OGL.GL_INTENSITY ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 4 bit intensity.
         */
        INTENSITY4( false, false, OGL.GL_INTENSITY4 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 8 bit intensity.
         */
        INTENSITY8( false, false, OGL.GL_INTENSITY8 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains only alpha.
         */
        ALPHA( true, false, OGL.GL_ALPHA ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 4 bit alpha.
         */
        ALPHA4( true, false, OGL.GL_ALPHA4 ),
        
        /**
         * Internal format hint.<br>
         * each pixel contains 8 bit alpha.
         */
        ALPHA8( true, false, OGL.GL_ALPHA8 ),
        
        /**
         * Internal format hint.<br>
         * compressed texture format. Uses S3TC_DXT1 compression.
         */
        RGB_DXT1( false, true, OGL.GL_COMPRESSED_RGB_S3TC_DXT1_EXT ),
        
        /**
         * Internal format hint.<br>
         * compressed texture format. Uses S3TC_DXT1 compression.
         */
        RGBA_DXT1( true, true, OGL.GL_COMPRESSED_RGBA_S3TC_DXT1_EXT ),
        
        /**
         * Internal format hint.<br>
         * compressed texture format. Uses S3TC_DXT3 compression.
         */
        RGBA_DXT3( true, true, OGL.GL_COMPRESSED_RGBA_S3TC_DXT3_EXT ),
        
        /**
         * Internal format hint.<br>
         * compressed texture format. Uses S3TC_DXT5 compression.
         */
        RGBA_DXT5( true, true, OGL.GL_COMPRESSED_RGBA_S3TC_DXT5_EXT ),
        ;
        
        private final boolean hasAlpha;
        private final boolean isCompressed;
        private final int glValue;
        
        public final boolean hasAlpha()
        {
            return( hasAlpha );
        }
        
        public final boolean isCompressed()
        {
            return( isCompressed );
        }
        
        public final int toOpenGL()
        {
            return( glValue );
        }
        
        public static final InternalFormat getFallbackInternalFormat( Format format )
        {
            switch ( format )
            {
                case RGB:
                    return ( InternalFormat.RGB );
                case RGBA:
                    return ( InternalFormat.RGBA );
            }
            
            return( ( format == Format.DEPTH ) ? null : InternalFormat.RGBA );
        }
        
        private InternalFormat( boolean hasAlpha, boolean isCompressed, int glValue )
        {
            this.hasAlpha = hasAlpha;
            this.isCompressed = isCompressed;
            this.glValue = glValue;
        }
    }
    
    /**
     * @return the image's internal-format.
     */
    public InternalFormat getInternalFormat();
    
    /**
     * @return the image's format.
     */
    public Format getFormat();
    
    /**
     * @return the image's actual width.
     */
    public int getWidth();
    
    /**
     * @return the image's actual height.
     */
    public int getHeight();
    
    /**
     * @return the image's pixel size in bytes (1, 2, 3, 4).
     */
    public int getPixelSize();
    
    /**
     * @return The image's ByteBuffer, that holds the image data
     * in R/RG/RGB/RGBA format.
     */
    public ByteBuffer getDataBuffer();
}
