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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 * @author Marvin Froehlich (aka Qudus)
 */
public class WADFile
{
    // wad directory, contains info for integrated files
    private static class WADDirectoryEntry 
    {
        public long    offset          = 0;    // - Offset 
        public int     compFileSize    = 0;    // - Compressed File Size 
        public int     unCompFileSize  = 0;    // - Uncompressed File Size 
        public byte    fileType        = 0;    // - File Type 
        public byte    compType        = 0;    // - Compression Type 
        public byte[]  padding         = null; // - Padding 
        public String  fileName        = null; // - Filename (null)
    }
    
    private final URL           wadFile;
    
    private final HashMap<String, WADDirectoryEntry> wadDir;
    
    public final String getWadType()
    {
        return( "WAD3" );
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
        
        in.skip( 3 * 4 );
        in.skip( entry.offset );
        
        return( (BufferedInputStream)in );
    }
    
    private static HashMap<String, WADDirectoryEntry> readWADDirectory( URL wadFile ) throws IOException, IncorrectFormatException, ParsingErrorException
    {      
        try
        {
            DataInputStream in = new DataInputStream( new BufferedInputStream( wadFile.openStream() ) );
            
            // read WAD header
            int magicNumber = in.readInt(); // "WAD3", 0x57414433
            if ( magicNumber != 0x57414433 )
            {
                throw new IncorrectFormatException( "This is not a WAD3 file!" );
            }
            
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
                entry.unCompFileSize = Integer.reverseBytes( in.readInt() );
                
                entry.fileType = in.readByte();
                entry.compType = in.readByte();
                entry.padding = new byte[] { in.readByte(), in.readByte() };
                
                in.read( bytes16, 0, 16 );
                entry.fileName = new String( bytes16 ).trim();
                
                wadDir.put( entry.fileName.toLowerCase(), entry );
                
                /*
                System.out.print( i + "\t| offset: " + entry.offset );
                System.out.print( "\t| CompFileSize: " + entry.compFileSize );
                System.out.print( " | UnCompFileSize: " + entry.unCompFileSize );
                
                System.out.print( " | FileType: " + entry.fileType );
                System.out.print( " | CompType: " + entry.compType );
                System.out.print( " | Pad1: " + entry.padding[0] + " | Pad2: " + entry.padding[1] );
                
                System.out.println( " | name: " + entry.fileName );
                */               
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
