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
package org.jagatoo.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.jagatoo.datatypes.util.ResizeListener2i;
import org.openmali.vecmath2.Point2i;
import org.openmali.vecmath2.Tuple2i;


/**
 * A basic 2-dimensional unpositioned rectangle.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Kevin Finley (aka Horati)
 */
public class Dim2i implements Sized2i
{
    private Tuple2i size;
    private List<ResizeListener2i> resizeListeners = new ArrayList<ResizeListener2i>();
    private boolean isDirty = true;
    
    /**
     * {@inheritDoc}
     * Notification takes place in the thread that called this.setSize(...). 
     */
    public void addResizeListener( ResizeListener2i listener )
    {
        this.resizeListeners.add( listener );
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeResizeListener( ResizeListener2i listener )
    {
        this.resizeListeners.remove( listener );
    }
    
    protected void fireResizeEvent( int oldWidth, int oldHeight, int newWidth, int newHeight )
    {
        for ( int i = 0; i < resizeListeners.size(); i++ )
        {
            resizeListeners.get( i ).onObjectResized( this, oldWidth, oldHeight, newWidth, newHeight );
        }
    }
    
    /**
     * @return true, if this object has been modified since the last setClean() call.
     * 
     * @see #setClean()
     */
    public boolean isDirty()
    {
        return( isDirty );
    }
    
    /**
     * Marks this object as not dirty.
     */
    public void setClean()
    {
        this.isDirty = false;
    }
    
    /**
     * Sets the rectangle's size.
     * 
     * @param width
     * @param height
     * 
     * @return true, if the size actually has changed
     */
    public boolean setSize( int width, int height )
    {
        final int oldWidth = getWidth();
        final int oldHeight = getHeight();
        
        if ( ( oldWidth != width ) || ( oldHeight != height ) )
        {
            this.size.set( width, height );
            
            isDirty = true;
            
            fireResizeEvent( oldWidth, oldHeight, width, height );
            
            return( true );
        }
        
        return( false );
    }
    
    /**
     * Sets the rectangle's size.
     * 
     * @param size
     * 
     * @return true, if the size actually has changed
     */
    public boolean setSize( Tuple2i size )
    {
        return( setSize( size.getX(), size.getY() ) );
    }
    
    /**
     * @return the upper-left corner's coordinates
     */
    public Tuple2i getSize()
    {
        return( size );
    }
    
    /**
     * @return the rectangle's width
     */
    public int getWidth()
    {
        return( size.getX() );
    }
    
    /**
     * @return the rectangle's height
     */
    public int getHeight()
    {
        return( size.getY() );
    }
    
    /**
     * {@inheritDoc}
     */
    public float getAspect()
    {
        if ( getHeight() != 0 )
            return( (float)getWidth() / (float)getHeight() );
        
        return( 0f );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param width the rectangle's width
     * @param height the rectangle's height
     */
    public void set( int width, int height )
    {
        setSize( width, height );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param size
     */
    public void set( Sized2i size )
    {
        setSize( size.getSize() );
    }
    
    public boolean equals( Sized2i rect )
    {
        if ( size != rect.getSize() )
            return( false );
        
        return( true );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o )
    {
        if (o == null)
            return( false );
        
        if ( !( o instanceof Sized2i ) )
            return( false );
        
        return( equals( (Sized2i)o ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( "dimension( width = " + getWidth() + ", height = " + getHeight() + ", aspect = " + getAspect() + " )" );
    }
    
    /**
     * Creates a new 2-dimensional unpositioned rectangle.
     * 
     * @param width the rectangle's width
     * @param height the rectangle's height
     */
    public Dim2i( int width, int height )
    {
        this.size = new Point2i( width, height );
    }
    
    /**
     * Creates a new 2-dimensional unpositioned rectangle and copies the template's coordinates.
     * 
     * @param template
     */
    public Dim2i( Sized2i template )
    {
        this( template.getWidth(), template.getHeight() );
    }
    
    /**
     * Creates a new 2-dimensional unpositioned rectangle with zero position and size.
     */
    public Dim2i()
    {
        this( 0, 0 );
    }
}
