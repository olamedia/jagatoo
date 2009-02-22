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
package org.jagatoo.input.impl.awt;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import org.jagatoo.input.render.Cursor;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class AWTCursorConverter
{
    private static final BufferedImage EMPTY_CURSOR_IMAGE = new BufferedImage( 16, 16, BufferedImage.TYPE_4BYTE_ABGR );
    public static final java.awt.Cursor HIDDEN_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor( EMPTY_CURSOR_IMAGE, new Point( 0, 0 ), "HIDDEN_CURSOR" );
    
    public static void convertCursor( Cursor cursor )
    {
        if ( ( cursor.getCursorObject() != null ) && ( cursor.getCursorObject() instanceof java.awt.Cursor ) )
        {
            return;
        }
        
        
        final int hotSpotX = cursor.getHotSpotX();
        final int hotSpotY = cursor.getHotSpotY();
        /*
        final int width = cursor.getWidth();
        final int height = cursor.getHeight();
        
        final int numImages = cursor.getImagesCount();
        
        for ( int i = 0; i < numImages; i++ )
        {
            for ( int x = 0; x < width; x++ )
            {
                for ( int y = 0; y < height; y++ )
                {
                    int color = cursor.getImage( i ).getRGB( width - x - 1, y );
                    imagesBuffer.put( color );
                }
            }
        }
        
        for ( int i = 0; i < numImages; i++ )
        {
            delaysBuffer.put( i, cursor.getDelay( i ) );
        }
        */
        
        cursor.setCursorObject( Toolkit.getDefaultToolkit().createCustomCursor( cursor.getImage( 0 ), new java.awt.Point( hotSpotX, hotSpotY ), cursor.getName() ) );
    }
}
