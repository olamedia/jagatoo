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
    public static final byte[] buildByteArray(InputStream in, int initialSize) throws IOException
    {
        byte[] buffer = new byte[ initialSize ];
        
        int i = 0;
        int b;
        while ((b = in.read()) != -1)
        {
            if (i >= buffer.length)
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
