/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
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
package org.jagatoo.input.impl.lwjgl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.jagatoo.input.render.Cursor;
import org.jagatoo.util.nio.BufferUtils;
import org.lwjgl.LWJGLException;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLCursorConverter
{
    public static void convertCursor( Cursor cursor )
    {
        if ( ( cursor.getCursorObject() != null ) && ( cursor.getCursorObject() instanceof org.lwjgl.input.Cursor ) )
        {
            return;
        }
        
        
        final int width = cursor.getWidth();
        final int height = cursor.getHeight();
        final int hotSpotX = cursor.getHotSpotX();
        final int hotSpotY = cursor.getHotSpotY();
        
        final int numImages = cursor.getImagesCount();
        
        IntBuffer imagesBuffer = ByteBuffer.allocateDirect( width * height * numImages * 4).order( ByteOrder.nativeOrder() ).asIntBuffer();
        imagesBuffer.position( 0 );
        
        for ( int i = 0; i < numImages; i++ )
        {
            for ( int x = 0; x < width; x++ )
            {
                for ( int y = 0; y < height; y++ )
                {
                    //cursor.getImage( i ).getData().getPixel( x, y, iArray )
                    int color = cursor.getImage( i ).getRGB( width - x - 1, y );
                    imagesBuffer.put( color );
                }
            }
        }
        
        imagesBuffer.rewind();
        
        IntBuffer delaysBuffer = BufferUtils.createIntBuffer( numImages );
        for ( int i = 0; i < numImages; i++ )
        {
            delaysBuffer.put( i, cursor.getDelay( i ) );
        }
        
        try
        {
            cursor.setCursorObject( new org.lwjgl.input.Cursor( width, height, hotSpotX, height - hotSpotY - 1, numImages, imagesBuffer, delaysBuffer ) );
        }
        catch ( LWJGLException e )
        {
            throw new Error( e );
        }
    }
}
