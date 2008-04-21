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
package org.jagatoo.input.devices;

import java.util.ArrayList;

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DigiState;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseEvent;
import org.jagatoo.input.events.MouseEventPool;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseStoppedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.listeners.MouseListener;
import org.jagatoo.input.listeners.MouseStopListener;
import org.jagatoo.input.managers.MouseStopManager;
import org.jagatoo.input.misc.InputSourceWindow;

public abstract class Mouse extends InputDevice
{
    private final MouseAxis xAxis, yAxis;
    private final MouseButton[] buttons;
    private final MouseWheel wheel;
    
    private boolean isAbsolute = true;
    
    private MouseStopManager stopManager = null;
    
    private int buttonState = 0;
    
    private final ArrayList< MouseListener > listeners = new ArrayList< MouseListener >();
    private final ArrayList< MouseStopListener > stopListeners = new ArrayList< MouseStopListener >();
    private int numListeners = 0;
    private int numStopListeners = 0;
    
    private long lastWhen_buttonPressed = -1L;
    private long lastWhen_buttonReleased = -1L;
    private long lastWhen_moved = -1L;
    private long lastWhen_wheelMoved = -1L;
    private long lastWhen_stopped = -1L;
    
    public final void startMouseStopManager() throws InputSystemException
    {
        if ( stopManager == null )
            stopManager = new MouseStopManager( this );
        
        stopManager.start();
    }
    
    public final void stopMouseStopManager() throws InputSystemException
    {
        if ( stopManager == null )
            return;
        
        stopManager.stopMe();
    }
    
    public final boolean isMouseStopManagerRunning()
    {
        if ( stopManager == null )
            return( false );
        
        return( stopManager.isSearching() );
    }
    
    protected final void notifyMouseStopManager( long nanoTime )
    {
        if ( stopManager == null )
            return;
        
        stopManager.notifyMouseMoved( nanoTime );
    }
    
    /**
     * @return the MouseAxis instance for this Mouse's X-axis.
     */
    public final MouseAxis getXAxis()
    {
        return( xAxis );
    }
    
    /**
     * @return the MouseAxis instance for this Mouse's Y-axis.
     */
    public final MouseAxis getYAxis()
    {
        return( yAxis );
    }
    
    /**
     * This method stores the positional values and may do
     * some additional implementation dependent stuff.
     * 
     * @param x
     * @param y
     */
    public void setPosition( int x, int y ) throws InputSystemException
    {
        this.xAxis.setValue( x );
        this.yAxis.setValue( y );
    }
    
    /**
     * Centers the mouse cursor on the source-window.
     * 
     * @throws InputSystemException
     */
    public abstract void centerMouse() throws InputSystemException;
    
    /**
     * This method simply stores the positional values.
     * 
     * @param x
     * @param y
     */
    protected void storePosition( int x, int y )
    {
        this.xAxis.setValue( x );
        this.yAxis.setValue( y );
    }
    
    /**
     * @return the current x-position of this Mouse.
     */
    public final int getCurrentX()
    {
        return( xAxis.getIntValue() );
    }
    
    /**
     * @return the current y-position of this Mouse.
     */
    public final int getCurrentY()
    {
        return( yAxis.getIntValue() );
    }
    
    /**
     * @return the number of {@link ControllerButton}s mounted to this {@link Controller}.
     */
    public final int getButtonsCount()
    {
        return( buttons.length );
    }
    
    /**
     * @return the number of {@link ControllerButton}s mounted to this {@link Controller}.
     */
    public final MouseButton getButton( int index )
    {
        return( buttons[ index ] );
    }
    
    /**
     * Sets the new button-state.
     * 
     * @param buttonState
     */
    protected void setButtonsState( int buttonState )
    {
        this.buttonState = buttonState;
    }
    
    /**
     * Adds bits to the button-state's bitmask.
     * 
     * @param buttonState
     */
    protected void addButtonsState( int buttonState )
    {
        this.buttonState |= buttonState;
    }
    
    /**
     * Adds bits to the button-state's bitmask.
     * 
     * @param button
     */
    protected void addButtonsState( MouseButton button )
    {
        this.buttonState |= button.getMaskValue();
    }
    
    /**
     * Removes bits from the button-state's bitmask.
     * 
     * @param buttonState
     */
    protected void removeButtonsState( int buttonState )
    {
        this.buttonState &= ~buttonState;
    }
    
    /**
     * Removes bits from the button-state's bitmask.
     * 
     * @param button
     */
    protected void removeButtonsState( MouseButton button )
    {
        this.buttonState &= ~button.getMaskValue();
    }
    
    /**
     * @return a bitmask with set bits for all pressed buttons.
     */
    public final int getButtonsState()
    {
        return( buttonState );
    }
    
    /**
     * @return the MouseWheel mounted to this Mouse.
     */
    public final MouseWheel getWheel()
    {
        return( wheel );
    }
    
    /**
     * @param button
     * 
     * @return true, if the queried {@link MouseButton} is pressed on this Mouse.
     */
    public final boolean isButtonPressed( MouseButton button )
    {
        return( ( buttonState & button.getMaskValue() ) != 0 );
    }
    
    /**
     * @param button
     * 
     * @return the appropriate state.
     */
    public final DigiState getButtonState( MouseButton button )
    {
        if ( isButtonPressed( button ) )
            return( DigiState.POSITIVE );
        else
            return( DigiState.NEGATIVE );
    }
    
    public void addMouseStopListener( MouseStopListener l )
    {
        boolean contains = false;
        for ( int i = 0; i < stopListeners.size(); i++ )
        {
            if ( stopListeners.get( i ) == l )
            {
                contains = true;
                break;
            }
        }
        
        if ( !contains )
            stopListeners.add( l );
        numStopListeners = stopListeners.size();
    }
    
    public void removeMouseStopListener( MouseStopListener l )
    {
        stopListeners.remove( l );
        numStopListeners = stopListeners.size();
    }
    
    public void addMouseListener( MouseListener l )
    {
        boolean contains = false;
        for ( int i = 0; i < listeners.size(); i++ )
        {
            if ( listeners.get( i ) == l )
            {
                contains = true;
                break;
            }
        }
        
        if ( !contains )
            listeners.add( l );
        if ( l instanceof MouseStopListener )
            addMouseStopListener( (MouseStopListener)l );
        numListeners = listeners.size();
    }
    
    public void removeMouseListener( MouseListener l )
    {
        listeners.remove( l );
        if ( l instanceof MouseStopListener )
            removeMouseStopListener( (MouseStopListener)l );
        numListeners = listeners.size();
    }
    
    protected final MouseButtonPressedEvent prepareMouseButtonPressedEvent( MouseButton button, long when )
    {
        addButtonsState( button );
        
        if ( !isEnabled() || ( numListeners == 0 ) )
            return( null );
        
        MouseButtonPressedEvent e = MouseEventPool.allocPressed( this, button, when, lastWhen_buttonPressed );
        
        lastWhen_buttonPressed = when;
        
        return( e );
    }
    
    public final void fireOnMouseButtonPressed( MouseButtonPressedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || ( numListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freePressed( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseButtonStateChanged( e, e.getButton(), e.getButtonState() );
            listeners.get( i ).onMouseButtonPressed( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, +1, 1 );
        
        if ( consumeEvent )
            MouseEventPool.freePressed( e );
    }
    
    protected final MouseButtonReleasedEvent prepareMouseButtonReleasedEvent( MouseButton button, long when )
    {
        removeButtonsState( button );
        
        if ( !isEnabled() || ( numListeners == 0 ) )
            return( null );
        
        MouseButtonReleasedEvent e = MouseEventPool.allocReleased( this, button, when, lastWhen_buttonReleased );
        
        lastWhen_buttonReleased = when;
        
        return( e );
    }
    
    public final void fireOnMouseButtonReleased( MouseButtonReleasedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || ( numListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freeReleased( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseButtonStateChanged( e, e.getButton(), e.getButtonState() );
            listeners.get( i ).onMouseButtonReleased( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, -1, 0 );
        
        if ( consumeEvent )
            MouseEventPool.freeReleased( e );
    }
    
    protected final MouseMovedEvent prepareMouseMovedEvent( int x, int y, int dx, int dy, long when )
    {
        if ( !isEnabled() )
            return( null );
        
        notifyMouseStopManager( when );
        
        if ( numListeners == 0 )
            return( null );
        
        MouseMovedEvent e = MouseEventPool.allocMoved( this, x, y, dx, dy, when, lastWhen_moved );
        
        lastWhen_moved = when;
        
        return( e );
    }
    
    public final void fireOnMouseMoved( MouseMovedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() )
        {
            if ( consumeEvent )
                MouseEventPool.freeMoved( e );
            return;
        }
        
        if ( numListeners == 0 )
        {
            if ( consumeEvent )
                MouseEventPool.freeMoved( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseMoved( e, e.getX(), e.getY(), e.getDX(), e.getDY() );
        }
        
        if ( e.getDX() != 0 )
            fireStateEventsAndDoActions( e, e.getDX(), e.getX() );
        if ( e.getDY() != 0 )
            fireStateEventsAndDoActions( e, e.getDY(), e.getY() );
        
        if ( consumeEvent )
            MouseEventPool.freeMoved( e );
    }
    
    protected final MouseWheelEvent prepareMouseWheelMovedEvent( int wheelDelta, boolean isPageMove, long when )
    {
        final MouseWheel wheel = getWheel();
        
        if ( wheel == null )
            return( null );
        
        wheel.addValue( wheelDelta );
        
        if ( !isEnabled() || ( numListeners == 0 ) )
            return( null );
        
        MouseWheelEvent e = MouseEventPool.allocWheel( this, wheel, wheelDelta, isPageMove, when, lastWhen_wheelMoved );
        
        lastWhen_wheelMoved = when;
        
        return( e );
    }
    
    public final void fireOnMouseWheelMoved( MouseWheelEvent e, boolean consumeEvent )
    {
        final MouseWheel wheel = getWheel();
        
        if ( wheel == null )
        {
            if ( consumeEvent )
                MouseEventPool.freeWheel( e );
            return;
        }
        
        if ( !isEnabled() || ( numListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freeWheel( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseWheelMoved( e, e.getWheelDelta() );
        }
        
        fireStateEventsAndDoActions( e, e.getWheelDelta(), e.getMouse().getWheel().getIntValue() );
        
        if ( consumeEvent )
            MouseEventPool.freeWheel( e );
    }
    
    public final MouseStoppedEvent prepareMouseStoppedEvent( long when )
    {
        if ( !isEnabled() || ( numStopListeners == 0 ) )
            return( null );
        
        MouseStoppedEvent e = MouseEventPool.allocStopped( this, getCurrentX(), getCurrentY(), when, lastWhen_stopped );
        
        lastWhen_stopped = when;
        
        return( e );
    }
    
    public final void fireOnMouseStopped( MouseStoppedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || ( numStopListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freeStopped( e );
            return;
        }
        
        for ( int i = 0; i < stopListeners.size(); i++ )
        {
            stopListeners.get( i ).onMouseStopped( e, e.getX(), e.getY() );
        }
        
        fireStateEventsAndDoActions( e, 0, 0 );
        
        if ( consumeEvent )
            MouseEventPool.freeStopped( e );
    }
    
    public final void fireMouseEvent( MouseEvent e, boolean consumeEvent )
    {
        switch ( e.getSubType() )
        {
            case BUTTON_PRESSED:
                fireOnMouseButtonPressed( (MouseButtonPressedEvent)e, consumeEvent );
                break;
            case BUTTON_RELEASED:
                fireOnMouseButtonReleased( (MouseButtonReleasedEvent)e, consumeEvent );
                break;
            case MOVED:
                fireOnMouseMoved( (MouseMovedEvent)e, consumeEvent );
                break;
            case STOPPED:
                fireOnMouseStopped( (MouseStoppedEvent)e, consumeEvent );
                break;
            case WHEEL_MOVED:
                fireOnMouseWheelMoved( (MouseWheelEvent)e, consumeEvent );
                break;
        }
    }
    
    /**
     * Makes this Mouse an absolute mouse (only delta positions become valid) or relative.
     * This method is implicitly called by {@link #setAbsolute(boolean)},
     * but only if the value has changed.
     * 
     * @param absolute
     */
    protected abstract void setAbsoluteImpl( boolean absolute ) throws InputSystemException;
    
    /**
     * Makes this Mouse an absolute mouse (only delta positions become valid) or relative.
     * 
     * @param absolute
     */
    public final void setAbsolute( boolean absolute ) throws InputSystemException
    {
        if ( !this.isAbsolute && absolute )
        {
            this.isAbsolute = absolute;
            setAbsoluteImpl( absolute );
            getSourceWindow().refreshCursor();
        }
        else if ( this.isAbsolute && !absolute )
        {
            this.isAbsolute = absolute;
            setAbsoluteImpl( absolute );
            getSourceWindow().refreshCursor();
        }
    }
    
    /**
     * @return whether this Mouse is absolute or not.
     */
    public final boolean isAbsolute()
    {
        return( isAbsolute );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( this.getClass().getSimpleName() + " { name = \"" + getName() + "\", numButtons = " + getButtonsCount() + ", hasWheel = " + ( getWheel() != null ) + " }" );
    }
    
    /**
     * Destroys the Mouse.
     */
    protected abstract void destroyImpl() throws InputSystemException;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void destroy() throws InputSystemException
    {
        if ( stopManager != null )
        {
            if ( stopManager.isSearching() )
                stopManager.stopMe();
            
            stopManager = null;
        }
        
        this.setAbsolute( true );
        
        destroyImpl();
    }
    
    protected Mouse( InputSourceWindow sourceWindow, EventQueue eveneQueue, String name, int numButtons, boolean hasWheel ) throws InputSystemException
    {
        super( sourceWindow, eveneQueue, name );
        
        this.xAxis = new MouseAxis( this, 'X', "Mouse-X-Axis" );
        this.yAxis = new MouseAxis( this, 'Y', "Mouse-Y-Axis" );
        this.buttons = new MouseButton[ numButtons ];
        
        for ( int i = 0; i < buttons.length; i++ )
        {
            buttons[ i ] = MouseButtons.getByIndex( i );
        }
        
        if ( hasWheel )
            this.wheel = new MouseWheel( this );
        else
            this.wheel = null;
    }
}
