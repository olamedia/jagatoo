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
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.events.ControllerEvent;
import org.jagatoo.input.events.ControllerEventPool;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.listeners.ControllerListener;
import org.jagatoo.input.misc.InputSourceWindow;

public abstract class Controller extends InputDevice
{
    private final ControllerFactory sourceFactory;
    
    private final ControllerAxis[] axes;
    private final ControllerButton[] buttons;
    
    private final ArrayList< ControllerListener > listeners = new ArrayList< ControllerListener >();
    private int numListeners = 0;
    
    private final long[] lastWhen_axisChanged;
    private final long[] lastWhen_buttonPressed;
    private final long[] lastWhen_buttonReleased;
    
    /**
     * @return the {@link ControllerFactory}, that created this instance.
     */
    public final ControllerFactory getSourceFactory()
    {
        return( sourceFactory );
    }
    
    /**
     * @return the number of {@link ControllerAxis} mounted to this {@link Controller}.
     */
    public final int getAxesCount()
    {
        return( axes.length );
    }
    
    /**
     * @param index
     * 
     * @return the index'th ControllerAxis.
     */
    public final ControllerAxis getAxis( int index )
    {
        return( axes[ index ] );
    }
    
    /**
     * @return the number of {@link ControllerButton}s mounted to this {@link Controller}.
     */
    public final int getButtonsCount()
    {
        return( buttons.length );
    }
    
    /**
     * @param index
     * 
     * @return the index'th ControllerButton.
     */
    public final ControllerButton getButton( int index )
    {
        return( buttons[ index ] );
    }
    
    /**
     * @return true, of at least one {@link ControllerListener} is currently registered.
     */
    public final boolean hasControllerListener()
    {
        return( numListeners > 0 );
    }
    
    /**
     * @return true, if one of {@link #hasInputStateListener()} or {@link #hasControllerListener()} return true.
     */
    public final boolean hasListener()
    {
        return( hasInputStateListener() || hasControllerListener() );
    }
    
    public void addControllerListener( ControllerListener l )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            if ( listeners.get( i ) == l )
                return;
        }
        
        listeners.add( l );
        numListeners = listeners.size();
    }
    
    public void removeControllerListener( ControllerListener l )
    {
        listeners.remove( l );
        numListeners = listeners.size();
    }
    
    /**
     * @param button
     * 
     * @return true, if the queried {@link ControllerButton} is pressed on this Controller.
     */
    public final boolean isButtonPressed( ControllerButton button )
    {
        //return( ( buttonState & button.getMaskValue() ) != 0 );
        return( false );
    }
    
    public final int getButtonState( ControllerButton button )
    {
        return( isButtonPressed( button ) ? 1 : 0 );
    }
    
    protected final ControllerButtonPressedEvent prepareControllerButtonPressed( ControllerButton button, long when )
    {
        if ( !isEnabled() || !hasListener() )
            return( null );
        
        ControllerButtonPressedEvent e = ControllerEventPool.allocPressed( this, button, when, lastWhen_buttonPressed[ button.getIndex() ] );
        
        lastWhen_buttonPressed[ button.getIndex() ] = when;
        
        return( e );
    }
    
    public final void fireOnControllerButtonPressed( ControllerButtonPressedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                ControllerEventPool.freePressed( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onControllerButtonStateChanged( e, e.getButton(), e.getButtonState() );
            listeners.get( i ).onControllerButtonPressed( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, +1, 1 );
        
        if ( consumeEvent )
            ControllerEventPool.freePressed( e );
    }
    
    public final ControllerButtonReleasedEvent prepareControllerButtonReleased( ControllerButton button, long when )
    {
        if ( !isEnabled() || !hasListener() )
            return( null );
        
        ControllerButtonReleasedEvent e = ControllerEventPool.allocReleased( this, button, when, lastWhen_buttonReleased[ button.getIndex() ] );
        
        lastWhen_buttonReleased[ button.getIndex() ] = when;
        
        return( e );
    }
    
    public final void fireOnControllerButtonReleased( ControllerButtonReleasedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                ControllerEventPool.freeReleased( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onControllerButtonStateChanged( e, e.getButton(), e.getButtonState() );
            listeners.get( i ).onControllerButtonReleased( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, -1, 0 );
        
        if ( consumeEvent )
            ControllerEventPool.freeReleased( e );
    }
    
    public final ControllerAxisChangedEvent prepareControllerAxisChanged( ControllerAxis axis, float delta, long when )
    {
        if ( !isEnabled() || !hasListener() )
            return( null );
        
        ControllerAxisChangedEvent e = ControllerEventPool.allocAxis( this, axis, delta, when, lastWhen_axisChanged[ axis.getIndex() ] );
        
        lastWhen_axisChanged[ axis.getIndex() ] = when;
        
        return( e );
    }
    
    public final void fireOnControllerAxisChanged( ControllerAxisChangedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                ControllerEventPool.freeAxis( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onControllerAxisChanged( e, e.getAxis(), e.getAxisDelta() );
        }
        
        fireStateEventsAndDoActions( e, (int)( e.getAxis().getScale() * e.getAxisDelta() ), e.getAxis().getIntValue() );
        
        if ( consumeEvent )
            ControllerEventPool.freeAxis( e );
    }
    
    public final void fireControllerEvent( ControllerEvent e, boolean consumeEvent )
    {
        switch ( e.getSubType() )
        {
            case BUTTON_PRESSED:
                fireOnControllerButtonPressed( (ControllerButtonPressedEvent)e, consumeEvent );
                break;
            case BUTTON_RELEASED:
                fireOnControllerButtonReleased( (ControllerButtonReleasedEvent)e, consumeEvent );
                break;
            case AXIS_CHANGED:
                fireOnControllerAxisChanged( (ControllerAxisChangedEvent)e, consumeEvent );
                break;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( this.getClass().getSimpleName() + " { name = \"" + getName() + "\", numAxes = " + getAxesCount() + ", numButtons = " + getButtonsCount() + " }" );
    }
    
    /**
     * Destroys the Controller.
     */
    protected abstract void destroyImpl() throws InputSystemException;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void destroy() throws InputSystemException
    {
        destroyImpl();
    }
    
    protected abstract ControllerAxis[] createAxesArray( Object implObj );
    
    protected abstract ControllerButton[] createButtonsArray( Object implObj );
    
    protected Controller( ControllerFactory sourceFactory, InputSourceWindow sourceWindow, EventQueue eventQueue, String name, Object implObj ) throws InputSystemException
    {
        super( sourceWindow, eventQueue, name );
        
        this.sourceFactory = sourceFactory;
        
        this.axes = createAxesArray( implObj );
        this.lastWhen_axisChanged = new long[ axes.length ];
        this.buttons = createButtonsArray( implObj );
        this.lastWhen_buttonPressed = new long[ buttons.length ];
        this.lastWhen_buttonReleased = new long[ buttons.length ];
        
        for ( int i = 0; i < axes.length; i++ )
        {
            lastWhen_axisChanged[ i ] = -1L;
        }
        
        for ( int i = 0; i < buttons.length; i++ )
        {
            lastWhen_buttonPressed[ i ] = -1L;
            lastWhen_buttonReleased[ i ] = -1L;
        }
    }
}
