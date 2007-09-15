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
