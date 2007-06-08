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
package org.jagatoo.loaders.models.md2;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * The frames from within the file.
 * 
 * @author Kevin Glass
 */
public class MD2Frames
{
    /** The frames read */
    private MD2Frame[] frames;
    
    /** 
     * Creates new MD2Frames. 
     * 
     * @param b The bytes to read as this frame
     * @param header The header information to assist in the read
     */
    public MD2Frames( byte[] b, MD2Header header ) throws IOException
    {       
        DataInputStream in = new DataInputStream( new ByteArrayInputStream( b ) );
        read( in, header );
        in.close();
    }
    
    /**
     * Gets a specified frame.
     * 
     * @param i The index of the frame to retrieve
     * @return The requested frame
     */
    public MD2Frame getFrame( int i )
    {
        return( frames[ i ] );
    }
    
    /**
     * Reads in the collection of frames.
     * 
     * @param in The input stream to read the frames from
     * @param header The header to assist the read
     */
    public void read( DataInputStream in, MD2Header header ) throws IOException
    {
        in.skip( header.getFramesOffset() );
        frames = new MD2Frame[ header.getFrameCount() ];
        
        for ( int i = 0; i < header.getFrameCount(); i++ )
        {
            frames[ i ] = new MD2Frame( in, header );
        }
    }
}
