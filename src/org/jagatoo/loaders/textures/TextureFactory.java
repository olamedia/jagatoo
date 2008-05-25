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

/**
 * The {@link TextureFactory} creates instances of {@link AbstractTextureImage}.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class TextureFactory
{
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * @param dataSize
     * @param format
     * @param internalFormat
     * 
     * @return the new AbstractTextureImage.
     */
    protected abstract AbstractTextureImage createTextureImageImpl( int width, int height, int orgWidth, int orgHeight, int pixelSize, int dataSize, AbstractTextureImage.InternalFormat internalFormat, AbstractTextureImage.Format format );
    
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * @param dataSizeHint if -1, the data-size is auto-calculated.
     * @param format
     * @param internalFormat
     * 
     * @return the new AbstractTextureImage.
     */
    public final AbstractTextureImage createTextureImage( int width, int height, int orgWidth, int orgHeight, int pixelSize, int dataSizeHint, AbstractTextureImage.InternalFormat internalFormat, AbstractTextureImage.Format format )
    {
        if ( ( width < 1 ) || ( height < 1 ) || ( orgWidth < 1 ) || ( orgHeight < 1 ) )
            throw new IllegalArgumentException( "Illegal size " + width + "x" + height + ", " + orgWidth + "x" + orgHeight );
        
        if ( pixelSize >= 8 )
        {
            pixelSize /= 8;
        }
        
        int dataSize = dataSizeHint;
        
        if ( dataSizeHint < 0 )
        {
            if ( pixelSize < 0 )
            {
                throw new IllegalArgumentException( "You cannot set both pixelSize AND dataSizeHint to -1." );
            }
            
            dataSize = width * height * pixelSize;
        }
        
        return( createTextureImageImpl( width, height, orgWidth, orgHeight, pixelSize, dataSize, internalFormat, format ) );
    }
    
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * @param format
     * @param internalFormat
     * 
     * @return the new AbstractTextureImage.
     */
    public final AbstractTextureImage createTextureImage( int width, int height, int orgWidth, int orgHeight, int pixelSize, AbstractTextureImage.InternalFormat internalFormat, AbstractTextureImage.Format format )
    {
        return( createTextureImage( width, height, orgWidth, orgHeight, pixelSize, -1, internalFormat, format ) );
    }
    
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * @param format
     * 
     * @return the new AbstractTextureImage.
     */
    public final AbstractTextureImage createTextureImage( int width, int height, int orgWidth, int orgHeight, int pixelSize, AbstractTextureImage.Format format )
    {
        return( createTextureImage( width, height, orgWidth, orgHeight, pixelSize, AbstractTextureImage.InternalFormat.getFallbackInternalFormat( format ), format ) );
    }
    
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * @param withAlphaChannel
     * 
     * @return the new AbstractTextureImage.
     */
    public final AbstractTextureImage createTextureImage( int width, int height, int orgWidth, int orgHeight, int pixelSize, boolean withAlphaChannel )
    {
        AbstractTextureImage.Format format = withAlphaChannel ? AbstractTextureImage.Format.RGBA : AbstractTextureImage.Format.RGB;
        
        return( createTextureImage( width, height, orgWidth, orgHeight, pixelSize, AbstractTextureImage.InternalFormat.getFallbackInternalFormat( format ), format ) );
    }
    
    /**
     * Creates an instance of {@link AbstractTextureImage} with an initialized
     * {@link ByteBuffer}, which is returned by the {@link AbstractTextureImage#getDataBuffer()} method.
     * 
     * @param width
     * @param height
     * @param orgWidth
     * @param orgHeight
     * @param pixelSize
     * 
     * @return the new AbstractTextureImage.
     */
    public final AbstractTextureImage createTextureImage( int width, int height, int orgWidth, int orgHeight, int pixelSize )
    {
        if ( pixelSize >= 8 )
            pixelSize /= 8;
        
        final AbstractTextureImage.Format format;
        switch ( pixelSize )
        {
            case 1:
                format = AbstractTextureImage.Format.DEPTH;
                break;
            case 2:
                format = AbstractTextureImage.Format.LUMINANCE_ALPHA;
                break;
            case 3:
                format = AbstractTextureImage.Format.RGB;
                break;
            case 4:
                format = AbstractTextureImage.Format.RGBA;
                break;
            default:
                throw new Error( "Unsupported pixel-size: " + pixelSize );
        }
        
        return( createTextureImage( width, height, orgWidth, orgHeight, pixelSize, format ) );
    }
    
    /**
     * Creates a new {@link AbstractTexture} instance.
     * 
     * @param type
     * @param format
     * 
     * @return the new texture.
     */
    protected abstract AbstractTexture createTextureImpl( AbstractTexture.Type type, AbstractTexture.Format format );
    
    /**
     * Creates a new {@link AbstractTexture} instance.
     * 
     * @param type
     * @param format
     * 
     * @return the new texture.
     */
    public final AbstractTexture createTexture( AbstractTexture.Type type, AbstractTexture.Format format )
    {
        return( createTextureImpl( type, format ) );
    }
    
    /**
     * Creates a new 2D-{@link AbstractTexture} instance.
     * 
     * @param format
     * 
     * @return the new texture.
     */
    public final AbstractTexture createTexture( AbstractTexture.Format format )
    {
        return( createTexture( AbstractTexture.Type.TEXTURE_2D, format ) );
    }
    
    /**
     * Creates a new 2D-{@link AbstractTexture} instance.
     * 
     * @param format
     * 
     * @return the new texture.
     */
    public final AbstractTexture createTexture( AbstractTextureImage.Format imageFormat )
    {
        return( createTexture( AbstractTexture.Type.TEXTURE_2D, AbstractTexture.Format.getFormat( imageFormat ) ) );
    }
    
    /**
     * Creates a new 2D-{@link AbstractTexture} instance and chooses
     * {@link AbstractTexture.Format#RGB} or {@link AbstractTexture.Format#RGBA}
     * depending on the withAlphachannel parameter.
     * 
     * @param withAlphaChannel
     * 
     * @return the new texture.
     */
    public final AbstractTexture createTexture( boolean withAlphaChannel )
    {
        return( createTexture( AbstractTexture.Type.TEXTURE_2D, withAlphaChannel ? AbstractTexture.Format.RGBA : AbstractTexture.Format.RGB ) );
    }
}
