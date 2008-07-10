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
package org.jagatoo.loaders.models.bsp;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.util.streams.StreamUtils;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 * @author Marvin Froehlich (aka Qudus)
 */
public class WADFile
{
    private static final int MAGIC_NUMBER_WAD3 = 0x57414433;
    
    // wad directory, contains info for integrated files
    private static class WADDirectoryEntry 
    {
        public long    offset          = 0;    // - Offset 
        public int     compFileSize    = 0;    // - Compressed File Size 
        public int     uncompFileSize  = 0;    // - Uncompressed File Size 
        public byte    fileType        = 0;    // - File Type 
        public byte    compType        = 0;    // - Compression Type 
        public byte[]  padding         = null; // - Padding 
        public String  fileName        = null; // - Filename (null-terminated)
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return( this.getClass().getSimpleName() + " { " +
                    "name: \"" + fileName + "\"" +
                    "\t | offset: " + offset +
                    "\t | compFileSize: " + compFileSize +
                    "\t | uncompFileSize: " + uncompFileSize +
                    "\t | fileType: " + fileType +
                    "\t | compType: " + compType +
                    "\t | pad1: " + padding[0] + "\t | pad2: " + padding[1] +
                    " }"
                  );
        }
    }
    
    private final URL     wadFile;
    private int           magicNumber;
    private String        wadType;
    
    private final HashMap<String, WADDirectoryEntry> wadDir;
    
    public final String getWadType()
    {
        return( wadType );
    }
    
    public final int getLumpCount()
    {
        return( wadDir.size() );
    }
    
    public final String[] getWADResources()
    {
        String[] result = new String[ wadDir.size() ];
        
        int i = 0;
        for ( String s : wadDir.keySet() )
        {
            result[ i ] = s;
        }
        
        java.util.Arrays.sort( result );
        
        return( result );
    }
    
    public final boolean containsResource( String resName )
    {
        return( wadDir.containsKey( resName.toLowerCase() ) );
    }
    
    public final BufferedInputStream getResourceAsStream( String resName ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            return( null );
        }
        
        InputStream in = wadFile.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        return( (BufferedInputStream)in );
    }
    
    public final void exportResource( String resName, String filename ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            throw new IOException( "The resource was not found in this WAD file." );
        }
        
        InputStream in = wadFile.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( filename ) );
        for ( int i = 0; i < entry.compFileSize; i++ )
        {
            out.write( (byte)in.read() );
        }
        out.close();
        
        in.close();
    }
    
    public final AbstractTexture readTexture( String resName, byte[][] palette, AppearanceFactory appFactory ) throws IOException
    {
        if ( magicNumber == MAGIC_NUMBER_WAD3 )
        {
            WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
            
            if ( entry == null )
            {
                throw new IOException( "The resource was not found in this WAD file." );
            }
            
            InputStream in = wadFile.openStream();
            if ( !( in instanceof BufferedInputStream ) )
            {
                in = new BufferedInputStream( in );
            }
            
            DataInputStream din = new DataInputStream( in );
            
            din.skip( entry.offset );
            
            byte[] name = new byte[ 16 ];
            din.read( name );
            //System.out.println( new String( name ).trim() );
            
            int width = Integer.reverseBytes( din.readInt() );
            int height = Integer.reverseBytes( din.readInt() );
            
            //System.out.println( width );
            //System.out.println( height );
            
            int[] offsets = new int[ 4 ];
            for ( int i = 0; i < 4; i++ )
            {
                offsets[i] = Integer.reverseBytes( din.readInt() );
                //System.out.println( offsets[i] );
            }
            
            int imgDataSize0 = offsets[1] - offsets[0];
            int imgDataSize = imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            
            byte[] imgData = new byte[ imgDataSize ];
            din.read( imgData );
            
            /*
             * Read the palette first...
             */
            int paletteSize = StreamUtils.readSwappedShort( din );
            for ( int i = 0; i < paletteSize; i++ )
            {
                palette[i][0] = (byte)in.read();
                palette[i][1] = (byte)in.read();
                palette[i][2] = (byte)in.read();
            }
            din.close();
            
            din = new DataInputStream( new ByteArrayInputStream( imgData ) );
            
            AbstractTextureImage[] mipmaps = new AbstractTextureImage[ 4 ];
            
            for ( int i = 0; i < 4; i++ )
            {
                //System.out.println( width + ", " + height );
                
                mipmaps[i] = appFactory.createTextureImage( AbstractTextureImage.Format.RGB, width, height );
                ByteBuffer bb = mipmaps[i].getDataBuffer();
                
                /*
                for ( int y = height - 1; y >= 0; y-- )
                {
                    for ( int x = 0; x < width; x++ )
                    {
                        int palIdx = din.read();
                        
                        int offset = y * width + x * 3;
                        bb.put( offset + 0, palette[palIdx][0] );
                        bb.put( offset + 1, palette[palIdx][1] );
                        bb.put( offset + 2, palette[palIdx][2] );
                    }
                }
                
                //bb.flip();
                */
                
                int size = width * height;
                for ( int j = 0; j < size; j++ )
                {
                    int palIdx = din.read();
                    
                    bb.put( palette[palIdx][0] );
                    bb.put( palette[palIdx][1] );
                    bb.put( palette[palIdx][2] );
                }
                
                bb.position( 0 );
                bb.limit( width * height * 3 );
                
                width = width >> 1;
                height = height >> 1;
            }
            
            din.close();
            
            AbstractTexture texture = appFactory.createTexture( mipmaps[0], true );
            //texture.setImage( 1, mipmaps[1] );
            //texture.setImage( 2, mipmaps[2] );
            //texture.setImage( 3, mipmaps[3] );
            
            return( texture );
        }
        
        return( null );
    }
    
    private HashMap<String, WADDirectoryEntry> readWADDirectory( URL wadFile ) throws IOException, IncorrectFormatException, ParsingErrorException
    {      
        try
        {
            DataInputStream in = new DataInputStream( new BufferedInputStream( wadFile.openStream() ) );
            
            // read WAD header
            int magicNumber = in.readInt();
            if ( magicNumber != MAGIC_NUMBER_WAD3 )
            {
                throw new IncorrectFormatException( "This is not a WAD3 file!" );
            }
            
            this.magicNumber = magicNumber;
            this.wadType = "WAD3";
            
            int lumpCount = Integer.reverseBytes( in.readInt() ); // - Number of files
            int dirOffset = Integer.reverseBytes( in.readInt() ); // - Directory offset
            
            /*
            System.out.println( "WadFile: " + wadFile );
            System.out.print( " | lumps: " + lumpCount ); 
            System.out.println( " | offset: " + dirOffset );
            */
            
            HashMap<String, WADDirectoryEntry> wadDir = new HashMap<String, WADDirectoryEntry>( lumpCount );
            
            // read Lump Dir
            in.skipBytes( dirOffset - ( 3 * 4 ) );
            
            byte[] bytes16 = new byte[ 16 ];
            
            for ( int i = 0; i < lumpCount; i++ ) 
            {
                WADDirectoryEntry entry = new WADDirectoryEntry();
                
                entry.offset = Integer.reverseBytes( in.readInt() );
                entry.compFileSize = Integer.reverseBytes( in.readInt() );
                entry.uncompFileSize = Integer.reverseBytes( in.readInt() );
                
                entry.fileType = in.readByte();
                entry.compType = in.readByte();
                entry.padding = new byte[] { in.readByte(), in.readByte() };
                
                in.read( bytes16, 0, 16 );
                entry.fileName = new String( bytes16 ).trim();
                
                wadDir.put( entry.fileName.toLowerCase(), entry );
                
                //System.out.println( entry );
            }
            
            in.close();
            
            return( wadDir );
        }
        catch ( IOException ioe )
        {
            throw ioe;
        }
        catch ( Throwable t )
        {
            throw new ParsingErrorException( t );
        }
    }
    
    public WADFile( URL wadFile ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        super();
        
        this.wadFile = wadFile;
        
        this.wadDir = readWADDirectory( wadFile );
    }
}
