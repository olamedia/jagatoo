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

import org.jagatoo.datatypes.Enableable;
import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.listeners.InputStateListener;
import org.jagatoo.input.misc.InputSourceWindow;

public abstract class InputDevice implements Enableable
{
    private final InputSourceWindow sourceWindow;
    
    private final EventQueue eventQueue;
    
    private final String name;
    
    private boolean enabled = true;
    
    private final ArrayList< InputStateListener > stateListeners = new ArrayList< InputStateListener >();
    
    public final InputSourceWindow getSourceWindow()
    {
        return( sourceWindow );
    }
    
    protected final EventQueue getEventQueue()
    {
        return( eventQueue );
    }
    
    /**
     * @return this Device's name.
     */
    public final String getName()
    {
        return( name );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o )
    {
        if ( o == this )
            return( true );
        
        if ( ( o.getClass() != this.getClass() ) || ( !((InputDevice)o).getName().equals( this.getName() ) ) )
            return( false );
        
        final InputDevice idO = (InputDevice)o;
        
        return( idO.getSourceWindow() == this.getSourceWindow() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }
    
    /**
     * {@inheritDoc}
     */
    public final boolean isEnabled()
    {
        return( enabled );
    }
    
    public void addStateListener( InputStateListener l )
    {
        stateListeners.add( l );
    }
    
    public void removeStateListener( InputStateListener l )
    {
        stateListeners.remove( l );
    }
    
    public void fireStateEventsAndDoActions( InputEvent e, int delta, int state )
    {
        if ( !isEnabled() )
        {
            return;
        }
        
        for ( int i = 0; i < stateListeners.size(); i++ )
        {
            stateListeners.get( i ).onInputStateChanged( e, delta, state );
        }
        
        // TODO: Implement MouseAxis class to avoid this selection!
        if ( e.getComponent() != null )
            e.getComponent().notifyBoundActions( delta, state, this );
    }
    
    /**
     * Processes pending events from the system
     * and places them into the EventQueue.<br>
     * The events are not fired (listeners are not notified).
     * They are fired when the {@link #update(InputSystem, EventQueue, long)}
     * method is invoked.
     * 
     * @param is
     * @param eventQueue
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    public abstract void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException;
    
    /**
     * Processes pending events from the system
     * and directly fires them (notifes the listeners).<br>
     * This method also flushes the EventQueue, if the previously
     * called {@link #collectEvents(InputSystem, EventQueue, long)}
     * method placed events into it.
     * 
     * @param is
     * @param eventQueue
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    public abstract void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException;
    
    /**
     * Destroys the InputDevice.
     * This must be called when the InputDevice is unregistered.
     */
    public abstract void destroy() throws InputSystemException;
    
    public InputDevice( InputSourceWindow sourceWindow, EventQueue eveneQueue, String name ) throws InputSystemException
    {
        if ( sourceWindow == null )
            throw( new InputSystemException( "The InputSourceWindow must not be null." ) );
        
        this.sourceWindow = sourceWindow;
        this.eventQueue = eveneQueue;
        this.name = name;
        
        if ( name == null )
            throw( new InputSystemException( "The name must not be null." ) );
    }
}
