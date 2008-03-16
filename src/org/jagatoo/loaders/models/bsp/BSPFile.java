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
/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.models.bsp.lumps.BSPDirectory;
import org.jagatoo.loaders.models.bsp.lumps.BSPLump;

/**
 * Represents a BSP source file.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
class BSPFile extends BSPDirectory
{
    private byte[]            byteBuffer   = null;
    private FloatBuffer       floatBuffer  = null;
    private ByteBuffer        bBuffer      = null;
    private IntBuffer         intBuffer    = null;
    
    private RandomAccessFile  in;
    
    private char[]            ID;
    private int               version;
    
    public RandomAccessFile getInputFile()
    {
        return( in );
    }
    
    public void seek(int lump) throws IOException
    {
        in.seek( lumps[ lump ].offset );
    }
    
    public byte readByte() throws IOException
    {
        return( in.readByte() );
    }
    
    public char readChar() throws IOException
    {
        return( (char)readByte() );
    }
    
    public int readInt() throws IOException
    {
        in.read( byteBuffer );
        
        return( intBuffer.get( 0 ) );
    }
    
    public float readFloat() throws IOException
    {
        in.read(byteBuffer);
        
        return( floatBuffer.get( 0 ) );
    }
    
    public void readFully( byte[] data ) throws IOException
    {
        in.readFully( data );
    }
    
    public byte[] readFully( int count ) throws IOException
    {
        byte[] data = new byte[ count ];
        
        in.readFully( data );
        
        return( data );
    }
    
    @Override
    protected void readDirectory() throws IOException
    {
        this.lumps = new BSPLump[ 17 ];
        
        for ( int i = 0; i < 17; i++ )
        {
            lumps[ i ] = new BSPLump();
            lumps[ i ].offset = readInt();
            lumps[ i ].length = readInt();
        }
    }
    
    public void close() throws IOException
    {
        in.close();
    }
    
    public char[] getID()
    {
        return( ID );
    }
    
    public int getVersion()
    {
        return( version );
    }
    
    public BSPFile( File file ) throws IOException, IncorrectFormatException
    {
        super();
        
        this.byteBuffer = new byte[ 4 ];
        this.bBuffer = ByteBuffer.wrap( byteBuffer );
        this.bBuffer.order( ByteOrder.LITTLE_ENDIAN );
        this.floatBuffer = bBuffer.asFloatBuffer();
        this.intBuffer = bBuffer.asIntBuffer();
        
        this.in = new RandomAccessFile( file, "r" );
        
        this.ID = new char[ 4 ];
        this.ID[ 0 ] = readChar();
        this.ID[ 1 ] = readChar();
        this.ID[ 2 ] = readChar();
        this.ID[ 3 ] = readChar();
        
        this.version = readInt();
        if ( version != 0x2e )
            throw( new IncorrectFormatException( "Invalid Quake 3 BSP file" ) );
        
        readDirectory();
    }
    
    public BSPFile(String filename) throws IOException, IncorrectFormatException
    {
        this( new File( filename ) );
    }
    
    protected BSPFile() {}
}
