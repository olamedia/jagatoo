/**
 * Copyright (c) 2007-2010, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.test.util.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jagatoo.image.BufferedImageFactory;
import org.jagatoo.util.nio.BufferUtils;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class DirectBufferedImageClipTest extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private BufferedImage bi = null;
    
    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        
        if ( bi == null )
        {
            //bi = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
            bi = BufferedImageFactory.createDirectBufferedImage( getWidth(), getHeight(), true, null, BufferUtils.createByteBuffer( getWidth() * getHeight() * 4 ) );
            //bi = BufferedImageFactory.createSharedBufferedImage( getWidth(), getHeight(), 4, null, null );
            //bi = DirectBufferedImage.make( DirectBufferedImage.Type.DIRECT_RGBA, getWidth(), getHeight() );
        }
        
        try
        {
            Graphics2D g2 = bi.createGraphics();
            
            g2.setColor( Color.BLACK );
            
            //g2.setClip( 240, 120, 50, 30 );
            g2.setClip( 1, 0, getWidth(), getHeight() );
            g2.drawRect( 240, 120, 50, 30 );
            g2.drawString( "hallo", 250, 135 );
            
            g.drawImage( bi, 0, 0, null );
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
        }
    }
    
    public DirectBufferedImageClipTest()
    {
        super();
    }
    
    public static void main( String[] args )
    {
        JFrame f = new JFrame( DirectBufferedImageClipTest.class.getSimpleName() );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        f.setBounds( 100, 100, 800, 600 );
        
        f.setContentPane( new DirectBufferedImageClipTest() );
        
        f.setVisible( true );
    }
}
