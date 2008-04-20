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

import org.jagatoo.datatypes.util.RepositionListener2i;
import org.openmali.vecmath2.Tuple2i;


/**
 * A basic 2-dimensional rectangle.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Kevin Finley (aka Horati)
 */
public class Rect2i extends Dim2i implements Positioned2i
{
    private int left, top;
    
    private final ArrayList<RepositionListener2i> repositionListeners = new ArrayList<RepositionListener2i>();
    
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
            this.left = left;
            this.top = top;
            
            this.isDirty = true;
            
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
    public boolean setLocation( Tuple2i upperLeft )
    {
        return( setLocation( upperLeft.getX(), upperLeft.getY() ) );
    }
    
    /**
     * @return the upper-left corner's coordinates
     */
    public Tuple2i getLocation()
    {
        return( new Tuple2i( left, top ) );
    }
    
    /**
     * @return the upper-left corner's x-coordinate
     */
    public int getLeft()
    {
        return( left );
    }
    
    /**
     * @return the upper-left corner's y-coordinate
     */
    public int getTop()
    {
        return( top );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param left the upper-left corner's x-coordinate
     * @param top the upper-left corner's y-coordinate
     * @param width the rectangle's width
     * @param height the rectangle's height
     */
    public void set( int left, int top, int width, int height )
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
    public void set( Positioned2iRO pos, Sized2iRO size )
    {
        setLocation( pos.getLeft(), pos.getTop() );
        setSize( size.getWidth(), size.getHeight() );
    }
    
    /**
     * Sets this rectangle's coordinates to the given rectangle's ones.
     * 
     * @param rect
     */
    public void set( Rect2i rect )
    {
        set( rect, rect );
    }
    
    public boolean equals( Rect2i rect )
    {
        if ( rect == null )
            return( false );
        
        if ( rect == this )
            return( true );
        
        return( ( rect.getLeft() == this.getLeft() ) && ( rect.getTop() == this.getTop() ) &&
                ( rect.getWidth() == this.getWidth() ) && ( rect.getHeight() == this.getHeight() )
              );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o)
    {
        if ( o == null )
            return( false );
        
        if ( !( o instanceof Rect2i ) )
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
    public Rect2i( int left, int top, int width, int height )
    {
        super( width, height );
        
        this.left = left;
        this.top = top;
    }
    
    /**
     * Creates a new 2-dimensional rectangle and copies the template's coordinates.
     * 
     * @param template
     */
    public Rect2i( Rect2i template )
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
