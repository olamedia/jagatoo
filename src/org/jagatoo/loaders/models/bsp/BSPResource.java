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

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.jagatoo.loaders.IncorrectFormatException;

/**
 * Represents a BSP source file, which is loaded as a resource (through a URL).
 * The resource is first completely loaded and dumped into a byte-array.
 * Then all the methods directly work on this byte-array.
 * This is necessary to to support arbitrary seeking of byte-positions.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
class BSPResource extends BSPFile
{
    private byte[]       byteBuffer = null;
    private FloatBuffer  floatBuffer = null;
    private ByteBuffer   bBuffer = null;
    private IntBuffer    intBuffer = null;
    private ShortBuffer  shortBuffer = null;
    
    private byte[]       in;
    private int          pointer;
    
    private char[]       ID;
    private int          version;
    
    @Override
    public RandomAccessFile getInputFile()
    {
        return( null );
    }
    
    @Override
    public void resetPointer() throws IOException
    {
        pointer = 0;
    }
    
    @Override
    public void seek( int lump ) throws IOException
    {
        pointer = lumps[ lump ].offset;
    }
    
    @Override
    public void skipBytes( int numBytes ) throws IOException
    {
        pointer += numBytes;
    }
    
    @Override
    public byte readByte() throws IOException
    {
        return( in[ pointer++ ] );
    }
    
    @Override
    public char readChar() throws IOException
    {
        return( (char)readByte() );
    }
    
    @Override
    public int readInt() throws IOException
    {
        try
        {
            byteBuffer[ 0 ] = in[ pointer++ ];
            byteBuffer[ 1 ] = in[ pointer++ ];
            byteBuffer[ 2 ] = in[ pointer++ ];
            byteBuffer[ 3 ] = in[ pointer++ ];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
        
        return( intBuffer.get( 0 ) );
    }
    
    @Override
    public short readShort() throws IOException
    {
        try
        {
            byteBuffer[ 0 ] = in[ pointer++ ];
            byteBuffer[ 1 ] = in[ pointer++ ];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
        
        return( shortBuffer.get( 0 ) );
    }
    
    @Override
    public int readUnsignedShort() throws IOException
    {
        byte low = in[ pointer++ ];
        byte high = in[ pointer++ ];
        
        int ushort = ( ( high & 0xFF ) << 8 ) | ( low & 0xFF );
        
        return( ushort );
    }
    
    @Override
    public float readFloat() throws IOException
    {
        try
        {
            byteBuffer[ 0 ] = in[ pointer++ ];
            byteBuffer[ 1 ] = in[ pointer++ ];
            byteBuffer[ 2 ] = in[ pointer++ ];
            byteBuffer[ 3 ] = in[ pointer++ ];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
        
        return( floatBuffer.get( 0 ) );
    }
    
    @Override
    public void readFully( byte[] data ) throws IOException
    {
        for ( int i = pointer; i < pointer + data.length; i++ )
        {
            data[ i - pointer ] = in[ i ];
        }
        
        pointer += data.length;
    }
    
    @Override
    public byte[] readFully( int count ) throws IOException
    {
        byte[] data = new byte[ count ];
        
        readFully( data );
        
        return( data );
    }
    
    @Override
    public void readFully( char[] data, int count ) throws IOException
    {
        for ( int i = 0; i < count; i++ )
        {
            data[ i ] = readChar();
        }
    }
    
    @Override
    public void close() throws IOException
    {
    }
    
    @Override
    public char[] getID()
    {
        return( ID );
    }
    
    @Override
    public int getVersion()
    {
        return( version );
    }
    
    private final byte[] ensureCapacity( byte[] buffer, int cap )
    {
        final int oldCapacity = buffer.length;
        if ( cap > oldCapacity )
        {
            final byte[] oldData = buffer;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            buffer = new byte[ newCapacity ];
            System.arraycopy( oldData, 0, buffer, 0, oldCapacity );
        }
        
        return( buffer );
    }
    
    private byte[] readStreamToByteArray( InputStream in ) throws IOException
    {
        // allocate 5MB
        byte[] buffer = new byte[ 5 * 1048576 ];
        int pos = 0;
        
        int b;
        while ( (b = in.read() ) > -1)
        {
            buffer = ensureCapacity( buffer, pos + 1 );
            buffer[ pos++ ] = (byte)b;
        }
        
        return( buffer );
    }
    
    public BSPResource( InputStream in ) throws IOException, IncorrectFormatException
    {
        super();
        
        this.byteBuffer = new byte[ 4 ];
        this.bBuffer = ByteBuffer.wrap( byteBuffer );
        this.bBuffer.order( ByteOrder.LITTLE_ENDIAN );
        this.floatBuffer = bBuffer.asFloatBuffer();
        this.intBuffer = bBuffer.asIntBuffer();
        this.shortBuffer = bBuffer.asShortBuffer();
        
        this.in = readStreamToByteArray( in );
        this.pointer = 0;
        
        checkHeader();
        
        readDirectory();
    }
    
    public BSPResource(URL resource) throws IOException, IncorrectFormatException
    {
        this( resource.openStream() );
    }
}
