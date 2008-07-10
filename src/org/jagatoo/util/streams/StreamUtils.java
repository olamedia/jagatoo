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
package org.jagatoo.util.streams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Contains static utility methods for Stream.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class StreamUtils
{
    /**
     * Reads one byte from the InputStream.
     * 
     * @param in
     * 
     * @return the read byte.
     * 
     * @throws IOException
     */
    public static final byte readByte( InputStream in ) throws IOException
    {
        return( (byte)in.read() );
    }
    
    /**
     * Reads one byte from the InputStream.
     * 
     * @param in
     * 
     * @return the read byte.
     * 
     * @throws IOException
     */
    public static final byte readByte( BufferedInputStream in ) throws IOException
    {
        return( (byte)in.read() );
    }
    
    /**
     * Reads one unsigned byte from the InputStream stored in a short-value
     * to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned byte as a short.
     * 
     * @throws IOException
     */
    public static final short readUnsignedByte( InputStream in ) throws IOException
    {
        int b = in.read();
        
        return( (short)( b & 0xFF ) );
    }
    
    /**
     * Reads one unsigned byte from the InputStream stored in a short-value
     * to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned byte as a short.
     * 
     * @throws IOException
     */
    public static final short readUnsignedByte( BufferedInputStream in ) throws IOException
    {
        int b = in.read();
        
        return( (short)( b & 0xFF ) );
    }
    
    /**
     * Reads two bytes from the InputStream stored in a short-value.
     * 
     * @param in
     * 
     * @return the read short.
     * 
     * @throws IOException
     */
    public static final short readShort( InputStream in ) throws IOException
    {
        int s1 = ( in.read() & 0xFF ) << 8;
        int s2 = ( in.read() & 0xFF );
        
        return( (short)( s1 | s2 ) );
    }
    
    /**
     * Reads two bytes from the InputStream stored in a short-value.
     * 
     * @param in
     * 
     * @return the read short.
     * 
     * @throws IOException
     */
    public static final short readShort( BufferedInputStream in ) throws IOException
    {
        int s1 = ( in.read() & 0xFF ) << 8;
        int s2 = ( in.read() & 0xFF );
        
        return( (short)( s1 | s2 ) );
    }
    
    /**
     * Reads two bytes from the InputStream, convertes them to a short
     * and stores them to an int to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned short (as an int).
     * 
     * @throws IOException
     */
    public static final int readUnsignedShort( InputStream in ) throws IOException
    {
        int high = (int)in.read();
        int low = (int)in.read();
        
        return( ( ( high & 0xFF ) << 8 ) | ( low & 0xFF ) );
    }
    
    /**
     * Reads two bytes from the InputStream, convertes them to a short
     * and stores them to an int to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned short (as an int).
     * 
     * @throws IOException
     */
    public static final int readUnsignedShort( BufferedInputStream in ) throws IOException
    {
        int high = (int)in.read();
        int low = (int)in.read();
        
        return( ( ( high & 0xFF ) << 8 ) | ( low & 0xFF ) );
    }
    
    /**
     * Reads two bytes from the InputStream stored in a short-value.
     * 
     * @param in
     * 
     * @return the read short.
     * 
     * @throws IOException
     */
    public static final short readSwappedShort( InputStream in ) throws IOException
    {
        int s2 = ( in.read() & 0xFF );
        int s1 = ( in.read() & 0xFF ) << 8;
        
        return( (short)( s1 | s2 ) );
    }
    
    /**
     * Reads two bytes from the InputStream stored in a short-value.
     * 
     * @param in
     * 
     * @return the read short.
     * 
     * @throws IOException
     */
    public static final short readSwappedShort( BufferedInputStream in ) throws IOException
    {
        int s2 = ( in.read() & 0xFF );
        int s1 = ( in.read() & 0xFF ) << 8;
        
        return( (short)( s1 | s2 ) );
    }
    
    /**
     * Reads two bytes from the InputStream, convertes them to a short
     * and stores them to an int to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned short (as an int).
     * 
     * @throws IOException
     */
    public static final int readSwappedUnsignedShort( InputStream in ) throws IOException
    {
        int low = (int)in.read();
        int high = (int)in.read();
        
        return( ( ( high & 0xFF ) << 8 ) | ( low & 0xFF ) );
    }
    
    /**
     * Reads two bytes from the InputStream, convertes them to a short
     * and stores them to an int to preserve the sign.
     * 
     * @param in
     * 
     * @return the read unsigned short (as an int).
     * 
     * @throws IOException
     */
    public static final int readSwappedUnsignedShort( BufferedInputStream in ) throws IOException
    {
        int low = (int)in.read();
        int high = (int)in.read();
        
        return( ( ( high & 0xFF ) << 8 ) | ( low & 0xFF ) );
    }
    
    /**
     * Reads one (signed) int from the stream.
     * 
     * @param in
     * 
     * @return the read int.
     * 
     * @throws IOException
     */
    public static final int readInt( InputStream in ) throws IOException
    {
        int i4 = ( in.read() & 0xFF ) << 24;
        int i3 = ( in.read() & 0xFF ) << 16;
        int i2 = ( in.read() & 0xFF ) << 8;
        int i1 = ( in.read() & 0xFF );
        
        return( i4 | i3 | i2 | i1 );
    }
    
    /**
     * Reads one (signed) int from the stream.
     * 
     * @param in
     * 
     * @return the read int.
     * 
     * @throws IOException
     */
    public static final int readInt( BufferedInputStream in ) throws IOException
    {
        int i4 = ( in.read() & 0xFF ) << 24;
        int i3 = ( in.read() & 0xFF ) << 16;
        int i2 = ( in.read() & 0xFF ) << 8;
        int i1 = ( in.read() & 0xFF );
        
        return( i4 | i3 | i2 | i1 );
    }
    
    /**
     * Reads one (signed) int from the stream.
     * 
     * @param in
     * 
     * @return the read int.
     * 
     * @throws IOException
     */
    public static final int readSwappedInt( InputStream in ) throws IOException
    {
        int i4 = ( in.read() & 0xFF );
        int i3 = ( in.read() & 0xFF ) << 8;
        int i2 = ( in.read() & 0xFF ) << 16;
        int i1 = ( in.read() & 0xFF ) << 24;
        
        return( i4 | i3 | i2 | i1 );
    }
    
    /**
     * Reads one (signed) int from the stream.
     * 
     * @param in
     * 
     * @return the read int.
     * 
     * @throws IOException
     */
    public static final int readSwappedInt( BufferedInputStream in ) throws IOException
    {
        int i1 = ( in.read() & 0xFF );
        int i2 = ( in.read() & 0xFF ) << 8;
        int i3 = ( in.read() & 0xFF ) << 16;
        int i4 = ( in.read() & 0xFF ) << 24;
        
        return( i4 | i3 | i2 | i1 );
    }
    
    /**
     * Reads bytesToRead bytes from the stream.
     * 
     * @param in
     * @param bytesToRead
     * @param buffer
     * @param bufferOffset
     * 
     * @throws IOException
     */
    public static final void readBytes( InputStream in, int bytesToRead, byte[] buffer, int bufferOffset ) throws IOException
    {
        int bytesRead = 0;
        int read;
        do
        {
            read = in.read( buffer, bufferOffset + bytesRead, bytesToRead );
            bytesRead += read;
            bytesToRead -= read;
        }
        while ( ( bytesToRead > 0 ) && ( read > 0 ) );
    }
    
    /**
     * Reads bytesToRead bytes from the stream.
     * 
     * @param in
     * @param bytesToRead
     * @param buffer
     * @param bufferOffset
     * 
     * @throws IOException
     */
    public static final void readBytes( BufferedInputStream in, int bytesToRead, byte[] buffer, int bufferOffset ) throws IOException
    {
        int bytesRead = 0;
        int read;
        do
        {
            read = in.read( buffer, bufferOffset + bytesRead, bytesToRead );
            bytesRead += read;
            bytesToRead -= read;
        }
        while ( ( bytesToRead > 0 ) && ( read > 0 ) );
    }
    
    /**
     * Builds a byte-array from the given InputStream.<br>
     * The byte-array is created with a size of <code>initialSize</code> and is
     * enlarged on demand.<br>
     * The InputStream is NOT closed at the end.
     * 
     * @param in the InputStream to get data from
     * @param initialSize the initial size of the output byte-array
     * @return the filled and correctly sized byte-array
     * 
     * @throws IOException
     */
    public static final byte[] buildByteArray( InputStream in, int initialSize ) throws IOException
    {
        byte[] buffer = new byte[ initialSize ];
        
        int i = 0;
        int b;
        while ( ( b = in.read() ) != -1 )
        {
            if ( i >= buffer.length )
            {
                byte[] newBuffer = new byte[ (buffer.length * 3) / 2 + 1 ];
                System.arraycopy( buffer, 0, newBuffer, 0, i );
                buffer = newBuffer;
            }
            
            buffer[ i ] = (byte)b;
            
            i++;
        }
        
        final byte[] copy = new byte[ i ];
        System.arraycopy( buffer, 0, copy, 0, Math.min( buffer.length, i ) );
        
        return( copy );
    }
    
    /**
     * This calls {@link #buildByteArray(InputStream, int)} with an initialSize
     * of 1024.
     * 
     * @param in the InputStream to get data from
     * @return the filled and correctly sized byte-array
     * 
     * @throws IOException
     */
    public static final byte[] buildByteArray(InputStream in) throws IOException
    {
        return( buildByteArray( in, 1024 ) );
    }
}
