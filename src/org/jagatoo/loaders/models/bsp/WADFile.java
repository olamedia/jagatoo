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


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jagatoo.loaders.IncorrectFormatException;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 */
public class WADFile
{
    // wad header
    private String  wadFile     = null;
    private char[]  wadType     = null; //- Header (WAD3) 
    private int     lumpCount   = 0;    //- Number Of Files 
    private int     dirOffset   = 0;    //- Directory Offset 

    private RandomAccessFile  in;
    
    private WADDirectory[] wadDir = null;
    
    // wad directory, contains info for integrated files
    private class WADDirectory 
    {
        private int     offset          = 0;    //- Offset 
        private int     ompFileSize     = 0;    //- Compressed File Size 
        private int     unCompFileSize  = 0;    //- Uncompressed File Size 
        private byte    filetype        = 0;    //- File Type 
        private byte    compType        = 0;    //- Compression Type 
        private byte[]  padding         = null; //- Padding 
        private char    fileName[]      = null;  //- Filename (null)
    }

    public String getWadFile()
    {
        return wadFile;
    }

    public void setWadFile( String wadFile )
    {
        this.wadFile = wadFile;
    }

    public char[] getWadType()
    {
        return wadType;
    }

    public void setWadType( char[] wadType )
    {
        this.wadType = wadType;
    }

    public int getLumpCount()
    {
        return lumpCount;
    }

    public void setLumpCount( int lumpCount )
    {
        this.lumpCount = lumpCount;
    }

    public int getDirOffset()
    {
        return dirOffset;
    }

    public void setDirOffset( int dirOffset )
    {
        this.dirOffset = dirOffset;
    }

    public WADDirectory[] getWadDir()
    {
        return wadDir;
    }

    public void setWadDir( WADDirectory[] wadDir )
    {
        this.wadDir = wadDir;
    }
    
    public WADDirectory[] readDirectory (int lumpCount)
    {
        WADDirectory[] wadDir = new WADDirectory[lumpCount];
        
        return wadDir;
    }
    
    public WADFile( File file ) throws IOException, IncorrectFormatException
    {
        super();
        
        this.wadFile    = new String();
        this.wadType    = new char[4];

        this.in = new RandomAccessFile( file, "r" );        
    }
    
    public WADFile(String fileName) throws IOException, IncorrectFormatException
    {
        this( new File( fileName ) );
    }
}
