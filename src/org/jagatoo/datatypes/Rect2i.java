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
package org.jagatoo.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.jagatoo.datatypes.util.RepositionListener2i;
import org.jagatoo.datatypes.util.ResizeListener2i;
import org.openmali.vecmath2.Point2i;
import org.openmali.vecmath2.Tuple2i;


/**
 * A basic 2-dimensional rectangle.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Kevin Finley (aka Horati)
 */
public class Rect2i implements Positioned2i, Sized2i
{
    private Tuple2i upperLeft;
    private Tuple2i size;
    private List<RepositionListener2i> repositionListeners = new ArrayList<RepositionListener2i>();
    private List<ResizeListener2i> resizeListeners = new ArrayList<ResizeListener2i>();
    private boolean isDirty = true;
    
    /**
     * {@inheritDoc}
     */
    public void addRepositionListener( RepositionListener2i listener )
    {
        this.repositionListeners.add( listener );
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeRepositionListener( RepositionListener2i listener )
    {
        this.repositionListeners.remove( listener );
    }
    
    protected void fireRepositionEvent( int oldLeft, int oldTop, int newLeft, int newTop )
    {
        for ( int i = 0; i < repositionListeners.size(); i++ )
        {
            repositionListeners.get( i ).onObjectRepositioned( this, oldLeft, oldTop, newLeft, newTop );
        }
    }
    
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
    
    protected void fireResizeEvent(int oldWidth, int oldHeight, int newWidth, int newHeight)
    {
        for (int i = 0; i < resizeListeners.size(); i++)
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
     * Sets the upper-left corner's coordinates.
     * 
     * @param left
     * @param top
     * 
     * @return true, if the location actually has changed
     */
    public boolean setLocation(int left, int top)
    {
        final int oldLeft = getLeft();
        final int oldTop = getTop();
        
        if ( ( oldLeft != left ) || ( oldTop != top ) )
        {
            this.upperLeft.set( left, top );
            
            isDirty = true;
            
            fireRepositionEvent( oldLeft, oldTop, left, top );
            
            return( true );
        }
        
        return( false );
    }
    
    /**
     * Sets the upper-left corner's coordinates.
     * 
     * @param upperLeft
     * 
     * @return true, if the location actually has changed
     */
    public boolean setLocation(Tuple2i upperLeft)
    {
        return( setLocation( upperLeft.getX(), upperLeft.getY() ) );
    }
    
    /**
     * @return the upper-left corner's coordinates
     */
    public Tuple2i getLocation()
    {
        return( upperLeft );
    }
    
    /**
     * @return the upper-left corner's x-coordinate
     */
    public int getLeft()
    {
        return( upperLeft.getX() );
    }
    
    /**
     * @return the upper-left corner's y-coordinate
     */
    public int getTop()
    {
        return( upperLeft.getY() );
    }
    
    /**
     * Sets the rectangle's size.
     * 
     * @param width
     * @param height
     * 
     * @return true, if the size actually has changed
     */
    public boolean setSize(int width, int height)
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
    public boolean setSize(Tuple2i size)
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
        if (getHeight() != 0)
            return( (float)getWidth() / (float)getHeight() );
        
        return( 0 );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param left the upper-left corner's x-coordinate
     * @param top the upper-left corner's y-coordinate
     * @param width the rectangle's width
     * @param height the rectangle's height
     */
    public void set(int left, int top, int width, int height)
    {
        setLocation( left, top );
        setSize( width, height );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param pos
     * @param size
     */
    public void set(Positioned2i pos, Sized2i size)
    {
        setLocation( pos.getLocation() );
        setSize( size.getSize() );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param rect
     */
    public void set(Rect2i rect)
    {
        set( rect, rect );
    }
    
    public boolean equals(Rect2i rect)
    {
        if (upperLeft != rect.getLocation())
            return( false );
        if (size != rect.getSize())
            return( false );
        
        return( true );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return( false );
        
        if (!(o instanceof Rect2i))
            return( false );
        
        return( equals( (Rect2i)o ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( "rectangle( x = " + getLeft() + ", y = " + getTop() + ", width = " + getWidth() + ", height = " + getHeight() + ", aspect = " + getAspect() + " )" );
    }
    
    /**
     * Creates a new 2-dimensional rectangle.
     * 
     * @param left the upper-left corner's x-coordinate
     * @param top the upper-left corner's y-coordinate
     * @param width the rectangle's width
     * @param height the rectangle's height
     */
    public Rect2i(int left, int top, int width, int height)
    {
        this.upperLeft = new Point2i( left, top );
        this.size = new Point2i( width, height );
    }
    
    /**
     * Creates a new 2-dimensional rectangle and copies the template's coordinates.
     * 
     * @param template
     */
    public Rect2i(Rect2i template)
    {
        this( template.getLeft(), template.getTop(), template.getWidth(), template.getHeight() );
    }
    
    /**
     * Creates a new 2-dimensional rectangle with zero position and size.
     */
    public Rect2i()
    {
        this( 0, 0, 0, 0 );
    }
}
