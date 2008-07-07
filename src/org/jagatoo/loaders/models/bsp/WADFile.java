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


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.jagatoo.loaders.IncorrectFormatException;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 */
public class WADFile
{
    // wad directory, contains info for integrated files
    private class WADDirectory 
    {
        public int     offset          = 0;    // - Offset 
        public int     compFileSize    = 0;    // - Compressed File Size 
        public int     unCompFileSize  = 0;    // - Uncompressed File Size 
        public byte    filetype        = 0;    // - File Type 
        public byte    compType        = 0;    // - Compression Type 
        public byte[]  padding         = null; // - Padding 
        public char[]  fileName        = null; // - Filename (null)
    }
    
    // wad header
    private String  wadFile     = null;
    private char[]  wadType     = null; // - Header (WAD3) 
    private int     lumpCount   = 0;    // - Number Of Files 
    private int     dirOffset   = 0;    // - Directory Offset 
    
    private RandomAccessFile  in;
    
    private WADDirectory[] wadDir = null;
    
    public void setWadFile( String wadFile )
    {
        this.wadFile = wadFile;
    }
    
    public final String getWadFile()
    {
        return( wadFile );
    }
    
    public void setWadType( char[] wadType )
    {
        this.wadType = wadType;
    }
    
    public final char[] getWadType()
    {
        return( wadType );
    }
    
    public void setLumpCount( int lumpCount )
    {
        this.lumpCount = lumpCount;
    }
    
    public final int getLumpCount()
    {
        return( lumpCount );
    }
    
    public void setDirOffset( int dirOffset )
    {
        this.dirOffset = dirOffset;
    }
    
    public final int getDirOffset()
    {
        return( dirOffset );
    }
    
    public void setWadDir( WADDirectory[] wadDir )
    {
        this.wadDir = wadDir;
    }
    
    public final WADDirectory[] getWadDir()
    {
        return( wadDir );
    }
    
    protected static void readWad( String fileName ) throws IOException
    {      
        try
        {
            //String fileName = "I:\\Half-Life\\valve\\halflife.wad";
            FileInputStream fstream = new FileInputStream( fileName );
            DataInputStream in = new DataInputStream( fstream );
            
            // read WAD header
            byte[] nameArray = new byte[ 4 ];
            in.readFully( nameArray );
            
            int lumpCount = 0;
            int dirOffset = 0;
            
            lumpCount = Integer.reverseBytes( in.readInt() );
            dirOffset = Integer.reverseBytes( in.readInt() );
            
            System.out.println( "WadFile: " + fileName );
            System.out.print( "desc: " + new String( nameArray ) );
            System.out.print( " | lumps: " + lumpCount ); 
            System.out.println( " | offset: " + dirOffset );
            
            // read Lump Dir
            in.skipBytes( dirOffset - ( 3 * 4 ) );
            
            for ( int i = 0; i < lumpCount; i++ ) 
            {
                System.out.print( i + "\t| offset: " + Integer.reverseBytes( in.readInt() ) );
                System.out.print( "\t| CompFileSize: " + Integer.reverseBytes( in.readInt() ) );
                System.out.print( " | UnCompFileSize: " + Integer.reverseBytes( in.readInt() ) );
                
                System.out.print( " | FileType: " + in.readByte() );
                System.out.print( " | CompType: " + in.readByte() );
                System.out.print( " | Pad1: " + in.readByte() + " | Pad2: " + in.readByte() );
                
                byte[] texName = new byte[ 16 ];
                in.read( texName );
                System.out.println( " | name: " + new String( texName ).trim() );               
            }
            
            in.close();
        } 
        catch ( Exception e )
        {
            System.err.println( "File input error" );
        }
    }
    
    public WADDirectory[] readDirectory( int lumpCount )
    {
        WADDirectory[] wadDir = new WADDirectory[ lumpCount ];
        
        return( wadDir );
    }
    
    public WADFile( File file ) throws IOException, IncorrectFormatException
    {
        super();
        
        this.wadFile    = new String();
        this.wadType    = new char[4];

        this.in = new RandomAccessFile( file, "r" );        
    }
    
    public WADFile( String fileName ) throws IOException, IncorrectFormatException
    {
        this( new File( fileName ) );
    }
}
