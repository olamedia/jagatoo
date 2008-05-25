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
package org.jagatoo.loaders.textures.formats;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.jagatoo.image.BufferedImageFactory;
import org.jagatoo.image.SharedBufferedImage;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.loaders.textures.TextureFactory;
import org.jagatoo.util.image.ImageUtility;

/**
 * Reads TGA files from an InputStream.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderTGA implements TextureImageFormatLoader
{
    private static final int HEADER_SIZE = 12;
    
    private static final int HEADER_INVALID = 0;
    private static final int HEADER_UNCOMPRESSED = 1;
    private static final int HEADER_COMPRESSED = 2;
    
    private static final int getUnsignedByte( byte b )
    {
        return( (int)b & 0xFF );
    }
    
    private static void readBuffer( BufferedInputStream in, byte[] buffer ) throws IOException
    {
        int bytesRead = 0;
        int bytesToRead = buffer.length;
        while ( bytesToRead > 0 )
        {
            int read = in.read( buffer, bytesRead, bytesToRead );
            bytesRead += read;
            bytesToRead -= read;
        }
    }
    
    private static void readBuffer( BufferedInputStream in, int bytesPerPixel, boolean acceptAlpha, int bytesToRead, byte[] bb ) throws IOException
    {
        byte[] buffer = new byte[ bytesPerPixel ];
        final boolean copyAlpha = ( bytesPerPixel == 4 ) && acceptAlpha;
        
        int b = 0;
        for ( int i = 0; i < bytesToRead; i += bytesPerPixel )
        {
            int read = in.read( buffer, 0, bytesPerPixel );
            
            if ( read < bytesPerPixel )
                return;
            
            // Don't swap R and B, since we want to use this byte array for a SharedBufferedImage!
            bb[ b++ ] = buffer[ 0 ];
            bb[ b++ ] = buffer[ 1 ];
            bb[ b++ ] = buffer[ 2 ];
            
            if ( copyAlpha )
                bb[ b++ ] = buffer[ 3 ];
        }
    }
    
    private static void readBuffer( BufferedInputStream in, int bytesPerPixel, boolean acceptAlpha, int bytesToRead, ByteBuffer bb ) throws IOException
    {
        byte[] buffer = new byte[ bytesPerPixel ];
        final boolean copyAlpha = ( bytesPerPixel == 4 ) && acceptAlpha;
        
        for ( int i = 0; i < bytesToRead; i += bytesPerPixel )
        {
            int read = in.read( buffer, 0, bytesPerPixel );
            
            if ( read < bytesPerPixel )
                return;
            
            // Swap R and B, because TGA stores them swapped.
            bb.put( buffer[ 2 ] );
            bb.put( buffer[ 1 ] );
            bb.put( buffer[ 0 ] );
            
            if ( copyAlpha )
                bb.put( buffer[ 3 ] );
        }
    }
    
    private static final int compareFormatHeader( BufferedInputStream in ) throws IOException
    {
        int result = HEADER_INVALID;
        
        /*
         * Uncompressed TGA Header
         */
        //private static final byte[] uTGAcompare = new byte[] { 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        
        /*
         * Compressed TGA Header
         */
        //private static final byte[] cTGAcompare = new byte[] { 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        
        if ( (byte)in.read() != (byte)0 )
            return( HEADER_INVALID );
        
        if ( (byte)in.read() != (byte)0 )
            return( HEADER_INVALID );
        
        switch ( (byte)in.read() )
        {
            case (byte)2:
                result = HEADER_UNCOMPRESSED;
                break;
            case (byte)10:
                result = HEADER_COMPRESSED;
                break;
            default:
                //result = HEADER_INVALID;
                return( HEADER_INVALID );
        }
        
        for ( int i = 3; i < HEADER_SIZE; i++ )
        {
            if ( (byte)in.read() != (byte)0 )
                return( HEADER_INVALID );
        }
        
        return( result );
    }
    
    private static void transferScaledBytes( byte[] unscaledData, int bytesPerPixel, ByteBuffer bb, int orgWidth, int orgHeight, int width, int height )
    {
        SharedBufferedImage sbi = BufferedImageFactory.createSharedBufferedImage( orgWidth, orgHeight, bytesPerPixel, null, unscaledData );
        
        SharedBufferedImage sbi_scaled = ImageUtility.scaleImage( sbi, width, height, (bytesPerPixel == 4 ) );
        
        byte[] scaledData = sbi_scaled.getSharedData();
        
        for ( int i = 0; i < scaledData.length; i += bytesPerPixel )
        {
            // Swap R and B.
            bb.put( scaledData[ i + 2 ] );
            bb.put( scaledData[ i + 1 ] );
            bb.put( scaledData[ i + 0 ] );
            
            if ( bytesPerPixel == 4 )
                bb.put( scaledData[ i + 3 ] );
        }
    }
    
    /**
     * Loads an uncompressed TGA (note, much of this code is based on NeHe's)
     * 
     * @param in
     * @param acceptAlpha
     * @param allowStreching
     * @param texFactory
     * 
     * @return the TextureImage or null.
     * 
     * @throws IOException
     */
    private AbstractTextureImage loadUncompressedTGA( BufferedInputStream in, boolean acceptAlpha, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        // TGA Loading code nehe.gamedev.net)
        
        byte[] header = new byte[ 6 ];
        readBuffer( in, header );
        
        // Determine The TGA height (highbyte * 256 + lowbyte)
        int orgHeight = ( getUnsignedByte( header[ 3 ] ) << 8 ) + getUnsignedByte( header[ 2 ] );
        // Determine The TGA width (highbyte * 256 + lowbyte)
        int orgWidth = ( getUnsignedByte( header[ 1 ] ) << 8 ) + getUnsignedByte( header[ 0 ] );
        // Determine the bits per pixel
        int bpp = getUnsignedByte( header[ 4 ] );
        
        // Make sure all information is valid
        if ( ( orgWidth <= 0 ) || ( orgHeight <= 0 ) || ( ( bpp != 24 ) && ( bpp != 32 ) ) )
        {
            throw( new IOException( "Invalid texture information" ) );
        }
        
        // Compute the number of BYTES per pixel
        int bytesPerPixel = ( bpp / 8 );
        // Compute the total amout ofmemory needed to store data
        int imageSize = orgWidth * orgHeight * bytesPerPixel;
        
        int width = ImageUtility.roundUpPower2( orgWidth );
        int height = ImageUtility.roundUpPower2( orgHeight );
        
        AbstractTextureImage image = texFactory.createTextureImage( width, height, orgWidth, orgHeight, ( acceptAlpha ? bytesPerPixel : 3 ) );
        ByteBuffer bb = image.getDataBuffer();
        //bb.position( 0 );
        bb.limit( bb.capacity() );
        
        if ( ( ( width != orgWidth ) || ( height != orgHeight ) ) && allowStreching )
        {
            byte[] imageData = new byte[ orgWidth * orgHeight * ( acceptAlpha ? bytesPerPixel : 3 ) ];
            readBuffer( in, bytesPerPixel, acceptAlpha, imageSize, imageData );
            
            transferScaledBytes( imageData, ( acceptAlpha ? bytesPerPixel : 3 ), bb, orgWidth, orgHeight, width, height );
        }
        else
        {
            readBuffer( in, bytesPerPixel, acceptAlpha, imageSize, bb );
        }
        
        bb.position( 0 );
        bb.limit( width * height * image.getFormat().getPixelSize() );
        
        return( image );
    }
    
    /**
     * Loads COMPRESSED TGAs
     * 
     * @param in
     * @param acceptAlpha
     * @param allowStreching
     * @param texFactory
     * 
     * @return the TextureImage or null.
     * 
     * @throws IOException
     */
    private AbstractTextureImage loadCompressedTGA( BufferedInputStream in, boolean acceptAlpha, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        byte[] header = new byte[ 6 ];
        readBuffer( in, header );
        
        // Determine The TGA height (highbyte * 256 + lowbyte)
        int orgHeight = ( getUnsignedByte( header[ 3 ] ) << 8 ) + getUnsignedByte( header[ 2 ] );
        // Determine The TGA width (highbyte * 256 + lowbyte)
        int orgWidth = ( getUnsignedByte( header[ 1 ] ) << 8 ) + getUnsignedByte( header[ 0 ] );
        // Determine the bits per pixel
        int bpp = getUnsignedByte( header[ 4 ] );
        
        // Make sure all information is valid
        if ( ( orgWidth <= 0 ) || ( orgHeight <= 0 ) || ( ( bpp != 24 ) && ( bpp != 32 ) ) )
        {
            throw( new IOException( "Invalid texture information" ) );
        }
        
        // Compute the number of BYTES per pixel
        int bytesPerPixel = ( bpp / 8 );
        // Compute the total amout ofmemory needed to store data
        //int imageSize = ( bytesPerPixel * imageWidth * imageHeight );
        // Allocate that much memory
        //byte imageData[] = new byte[ imageSize ];
        // Number of pixels in the image
        int pixelCount = orgHeight * orgWidth;
        // Current byte
        // Current pixel being read
        int currentPixel = 0;
        // Storage for 1 pixel
        byte[] colorBuffer = new byte[ bytesPerPixel ];
        
        int width = ImageUtility.roundUpPower2( orgWidth );
        int height = ImageUtility.roundUpPower2( orgHeight );
        
        AbstractTextureImage image = texFactory.createTextureImage( width, height, orgWidth, orgHeight, ( acceptAlpha ? bytesPerPixel : 3 ) );
        ByteBuffer bb = image.getDataBuffer();
        bb.position( 0 );
        bb.limit( bb.capacity() );
        
        final int dstBytesPerPixel = image.getFormat().getPixelSize();
        
        byte[] imageData = null;
        int dstByteOffset = 0;
        if ( ( ( width != orgWidth ) || ( height != orgHeight ) ) && allowStreching )
        {
            imageData = new byte[ orgWidth * orgHeight * ( acceptAlpha ? bytesPerPixel : 3 ) ];
        }
        
        do
        {
            // Storage for "chunk" header
            int chunkHeader = 0;
            try
            {
                chunkHeader = getUnsignedByte( (byte)in.read() );
            }
            catch ( IOException e )
            {
                throw( new IOException( "Could not read RLE header" ) );
            }
            
            boolean repeatColor;
            
            /*
             * If the header is < 128, it means, the that is the number of RAW
             * color packets minus 1.
             */
            if ( chunkHeader < 128 )
            {
                // add 1 to get number of following color values
                chunkHeader++;
                
                repeatColor = false;
            }
            // chunkheader > 128 RLE data, next color repeated chunkheader - 127 times
            else
            {
                // Subtract 127 to get rid of the ID bit
                chunkHeader -= 127;
                
                readBuffer( in, colorBuffer );
                
                repeatColor = true;
            }
            
            for ( int counter = 0; counter < chunkHeader; counter++ )
            {
                if ( !repeatColor )
                {
                    readBuffer( in, colorBuffer );
                }
                
                // write to memory
                
                if ( imageData == null )
                {
                    // Flip R and B color values around in the process.
                    bb.put( colorBuffer[ 2 ] );
                    bb.put( colorBuffer[ 1 ] );
                    bb.put( colorBuffer[ 0 ] );
                    
                    // if its a 32 bpp image
                    if ( dstBytesPerPixel == 4 )
                    {
                        // copy the 4th byte
                        bb.put( colorBuffer[ 3 ] );
                    }
                }
                else
                {
                    System.arraycopy( colorBuffer, 0, imageData, dstByteOffset, dstBytesPerPixel );
                    
                    dstByteOffset += dstBytesPerPixel;
                }
                
                // Increase current pixel by 1
                currentPixel++;
                
                // Make sure we havent read too many pixels
                if ( currentPixel > pixelCount )
                {
                    // if there is too many... Display an error!
                    throw( new IOException( "Too many pixels read" ) );
                }
            }
        }
        while ( currentPixel < pixelCount ); // Loop while there are still pixels left...
        
        if ( imageData != null )
        {
            transferScaledBytes( imageData, dstBytesPerPixel, bb, orgWidth, orgHeight, width, height );
        }
        
        bb.position( 0 );
        bb.limit( width * height * dstBytesPerPixel );
        
        return( image );
    }
    
    /**
     * {@inheritDoc}
     */
    public AbstractTextureImage loadTextureImage( BufferedInputStream in, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        if ( in.available() < HEADER_SIZE )
        {
            return( null );
        }
        
        final int headerType = compareFormatHeader( in );
        
        if ( headerType == HEADER_INVALID )
            return( null );
        
        AbstractTextureImage image = null;
        
        if ( headerType == HEADER_UNCOMPRESSED )
        {
            // If so, jump to Uncompressed TGA loading code
            image = loadUncompressedTGA( in, acceptAlpha, allowStreching, texFactory );
        }
        else if ( headerType == HEADER_COMPRESSED )
        {
            // If so, jump to Compressed TGA loading code
            image = loadCompressedTGA( in, acceptAlpha, allowStreching, texFactory );
        }
        // If header matches neither type
        else
        {
            // throw( new IOException("TGA file be type 2 or type 10 ") );
            return( null );
        }
        
        return( image );
    }
    
    public TextureImageFormatLoaderTGA()
    {
    }
}
