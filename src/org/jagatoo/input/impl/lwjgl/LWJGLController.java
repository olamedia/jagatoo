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
package org.jagatoo.input.impl.lwjgl;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DigiState;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.InputSourceWindow;

/**
 * LWJGL implementation of a Controller.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLController extends Controller
{
    private final org.lwjgl.input.Controller controller;
    
    protected final org.lwjgl.input.Controller getController()
    {
        return( controller );
    }
    
    protected final int getIndex()
    {
        return( controller.getIndex() );
    }
    
    protected final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( !getSourceWindow().receivsInputEvents() )
            return;
        
        final boolean isQueued = ( eventQueue != null );
        
        try
        {
            //controller.poll();
            
            for ( int i = 0; i < getAxesCount(); i++ )
            {
                final ControllerAxis axis = getAxis( i );
                final float oldValue = axis.getFloatValue();
                final float newValue = controller.getAxisValue( i );
                
                if ( newValue != oldValue )
                {
                    axis.setValue( newValue );
                    
                    final ControllerAxisChangedEvent axisEv = prepareControllerAxisChanged( axis, newValue - oldValue, nanoTime );
                    
                    if ( isQueued )
                        eventQueue.enqueue( axisEv );
                    else
                        fireOnControllerAxisChanged( axisEv, true );
                }
            }
            
            for ( int i = 0; i < getButtonsCount(); i++ )
            {
                final ControllerButton button = getButton( i );
                final boolean oldState = button.getBooleanState();
                final boolean newState = controller.isButtonPressed( i );
                
                if ( newState != oldState )
                {
                    if ( newState )
                    {
                        button.setState( DigiState.POSITIVE );
                        
                        final ControllerButtonPressedEvent pressedEv = prepareControllerButtonPressed( button, nanoTime );
                        
                        if ( isQueued )
                            eventQueue.enqueue( pressedEv );
                        else
                            fireOnControllerButtonPressed( pressedEv, true );
                    }
                    else
                    {
                        button.setState( DigiState.NEGATIVE );
                        
                        final ControllerButtonReleasedEvent releasedEv = prepareControllerButtonReleased( button, nanoTime );
                        
                        if ( isQueued )
                            eventQueue.enqueue( releasedEv );
                        else
                            fireOnControllerButtonReleased( releasedEv, true );
                    }
                }
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw( (InputSystemException)t );
            
            if ( t instanceof Error )
                throw( (Error)t );
            
            if ( t instanceof RuntimeException )
                throw( (RuntimeException)t );
            
            throw( new InputSystemException( t ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( eventQueue == null )
            throw( new InputSystemException( "EventQueue must not be null here!" ) );
        
        collectOrFireEvents( is, eventQueue, nanoTime );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        collectOrFireEvents( is, null, nanoTime );
        
        getEventQueue().dequeueAndFire( is );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        /*
        try
        {
            if ( controller.isCreated() )
            {
                controller.destroy();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw( (InputSystemException)t );
            
            if ( t instanceof Error )
                throw( (Error)t );
            
            if ( t instanceof RuntimeException )
                throw( (RuntimeException)t );
            
            throw( new InputSystemException( t ) );
        }
        */
    }
    
    private static final LWJGLControllerAxis[] createAxesArray( org.lwjgl.input.Controller controller )
    {
        LWJGLControllerAxis[] axes = new LWJGLControllerAxis[ controller.getAxisCount() ];
        
        for ( int i = 0; i < controller.getAxisCount(); i++ )
        {
            axes[ i ] = new LWJGLControllerAxis( controller, i );
        }
        
        return( axes );
    }
    
    private static final ControllerButton[] createButtonsArray( org.lwjgl.input.Controller controller )
    {
        ControllerButton[] buttons = new ControllerButton[ controller.getButtonCount() ];
        
        for ( int i = 0; i < controller.getButtonCount(); i++ )
        {
            buttons[ i ] = new ControllerButton( i, controller.getButtonName( i ) );
        }
        
        return( buttons );
    }
    
    protected LWJGLController( InputSourceWindow sourceWindow, EventQueue eveneQueue, org.lwjgl.input.Controller controller ) throws InputSystemException
    {
        super( sourceWindow, eveneQueue, controller.getName(), createAxesArray( controller ), createButtonsArray( controller ) );
        
        this.controller = controller;
    }
}
