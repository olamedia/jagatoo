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
package org.jagatoo.test.util.render;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;

import com.sun.opengl.util.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;

public abstract class JOGLBase implements GLEventListener
{
    private final Frame frame;
    private final GLCanvas canvas;
    private long nanoTime0 = System.nanoTime();
    
    public final Frame getFrame()
    {
        return( frame );
    }
    
    public final GLCanvas getCanvas()
    {
        return( canvas );
    }
    
    protected void unavailableExtension( String message )
    {
        JOptionPane.showMessageDialog( null, message, "Unavailable extension", JOptionPane.ERROR_MESSAGE );
        throw( new GLException( message ) );
    }
    
    protected void checkExtension( GL gl, String extensionName )
    {
        if ( !gl.isExtensionAvailable( extensionName ) )
        {
            String message = "Unable to initialize " + extensionName + " OpenGL extension";
            unavailableExtension( message );
        }
    }
    
    public void init( GLAutoDrawable drawable )
    {
        // Use debug pipeline
        //drawable.setGL( new DebugGL( drawable.getGL() ) );
        
        GL gl = drawable.getGL();
        //System.err.println( "INIT GL IS: " + gl.getClass().getName() );
        
        // Enable VSync
        gl.setSwapInterval( 1 );
        
        // Setup the drawing area and shading mode
        gl.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
        gl.glShadeModel( GL.GL_SMOOTH ); // try setting this to GL_FLAT and see what happens.
    }
    
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if ( height <= 0 ) // avoid a divide by zero error!
                height = 1;
        final float h = (float)width / (float)height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GL.GL_PROJECTION );
        gl.glLoadIdentity();
        glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
        gl.glMatrixMode( GL.GL_MODELVIEW );
        gl.glLoadIdentity();
    }
    
    protected abstract void render( GL gl, int canvasWidth, int canvasHeight, long nanoTime );
    
    public void display( GLAutoDrawable drawable )
    {
        GL gl = drawable.getGL();
        
        // Clear the drawing area
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        
        render( gl, drawable.getWidth(), drawable.getHeight(), System.nanoTime() - nanoTime0 );
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    public void displayChanged( GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged )
    {}
    
    private final Animator animator;
    
    public void quit()
    {
        if ( frame.isDisplayable() )
        {
            frame.dispose();
        }
        
        // Run this on another thread than the AWT event queue to
        // make sure the call to Animator.stop() completes before
        // exiting
        new Thread( new Runnable()
        {
            public void run()
            {
                animator.stop();
                System.exit( 0 );
            }
        } ).start();
    }
    
    private void startLoop()
    {
        nanoTime0 = System.nanoTime();
        
        while ( animator.isAnimating() )
        {
            canvas.display();
            
            try
            {
                Thread.sleep( 10L );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
    }
    
    protected void startLoop( boolean newThread )
    {
        if ( newThread )
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    startLoop();
                }
            }.start();
        }
        else
        {
            startLoop();
        }
    }
    
    public JOGLBase( String title, int width, int height )
    {
        this.frame = new Frame( title );
        frame.setLayout( null );
        frame.setSize( width, height );
        
        this.canvas = new GLCanvas();
        canvas.setSize( width, height );
        canvas.addGLEventListener( this );
        frame.add( canvas );
        
        this.animator = new Animator( canvas );
        frame.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                quit();
            }
        } );
        // Center frame
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
        canvas.requestFocus();
        animator.start();
        
        Thread.yield();
        
        final Dimension frameSize;
        Insets insets = frame.getInsets();
        canvas.setLocation( insets.left, insets.top );
        frameSize = new Dimension( width + insets.left + insets.right, height + insets.top + insets.bottom );
        frame.setSize( frameSize );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Point upperLeft = new Point( ( screenSize.width - frameSize.width ) / 2, ( screenSize.height - frameSize.height ) / 2 );
        frame.setLocation( upperLeft );
        
        Thread.yield();
        
        nanoTime0 = System.nanoTime();
    }
}
