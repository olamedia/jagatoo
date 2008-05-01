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
package org.jagatoo.input.impl.swt;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.KeyboardFactory;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.KeyID;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.KeyPressedEvent;
import org.jagatoo.input.events.KeyReleasedEvent;
import org.jagatoo.input.events.KeyTypedEvent;
import org.jagatoo.input.events.KeyboardEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * SWT implementation of the Keyboard class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class SWTKeyboard extends Keyboard
{
    private long lastGameTimeDelta = System.nanoTime();
    
    /**
     * <p>
     * An hash map of net.jtank.input.KeyCode keys with their SWT equivalents as
     * the keys.
     * </p>
     * 
     * <p>
     * Example:
     * <code>assert(KEY_CONVERSION.get(SWT.CTRL) == KeyCode.VK_CTRL)</code>
     * should not fail.
     * </p>
     */
    protected static final HashMap< Integer, org.jagatoo.input.devices.components.Key > KEYCODE_CONVERSION;
    protected static final HashMap< Character, org.jagatoo.input.devices.components.Key > CHAR_CONVERSION;
    
    /*
     * Builds the conversion HashMaps to convert SWT key codes/chars to
     * net.jtank.input.KeyCode ones
     */
    static
    {
        CHAR_CONVERSION = new HashMap< Character, org.jagatoo.input.devices.components.Key >();
        
        CHAR_CONVERSION.put( '0', Keys._0 );
        CHAR_CONVERSION.put( '1', Keys._1 );
        CHAR_CONVERSION.put( '2', Keys._2 );
        CHAR_CONVERSION.put( '3', Keys._3 );
        CHAR_CONVERSION.put( '4', Keys._4 );
        CHAR_CONVERSION.put( '5', Keys._5 );
        CHAR_CONVERSION.put( '6', Keys._6 );
        CHAR_CONVERSION.put( '7', Keys._7 );
        CHAR_CONVERSION.put( '8', Keys._8 );
        CHAR_CONVERSION.put( '9', Keys._9 );
        CHAR_CONVERSION.put( 'a', Keys.A );
        CHAR_CONVERSION.put( 'b', Keys.B );
        CHAR_CONVERSION.put( 'c', Keys.C );
        CHAR_CONVERSION.put( 'd', Keys.D );
        CHAR_CONVERSION.put( 'e', Keys.E );
        CHAR_CONVERSION.put( 'f', Keys.F );
        CHAR_CONVERSION.put( 'g', Keys.G );
        CHAR_CONVERSION.put( 'h', Keys.H );
        CHAR_CONVERSION.put( 'i', Keys.I );
        CHAR_CONVERSION.put( 'j', Keys.J );
        CHAR_CONVERSION.put( 'k', Keys.K );
        CHAR_CONVERSION.put( 'l', Keys.L );
        CHAR_CONVERSION.put( 'm', Keys.M );
        CHAR_CONVERSION.put( 'n', Keys.N );
        CHAR_CONVERSION.put( 'o', Keys.O );
        CHAR_CONVERSION.put( 'p', Keys.P );
        CHAR_CONVERSION.put( 'q', Keys.Q );
        CHAR_CONVERSION.put( 'r', Keys.R );
        CHAR_CONVERSION.put( 's', Keys.S );
        CHAR_CONVERSION.put( 't', Keys.T );
        CHAR_CONVERSION.put( 'u', Keys.U );
        CHAR_CONVERSION.put( 'v', Keys.V );
        CHAR_CONVERSION.put( 'w', Keys.W );
        CHAR_CONVERSION.put( 'x', Keys.X );
        CHAR_CONVERSION.put( 'y', Keys.Y );
        CHAR_CONVERSION.put( 'z', Keys.Z );
        CHAR_CONVERSION.put( '\b', Keys.BACK_SPACE );
        CHAR_CONVERSION.put( ' ', Keys.SPACE );
        CHAR_CONVERSION.put( '\r', Keys.ENTER );
        CHAR_CONVERSION.put( SWT.ESC, Keys.ESCAPE );
        CHAR_CONVERSION.put( SWT.DEL, Keys.DELETE );
        
        KEYCODE_CONVERSION = new HashMap< Integer, org.jagatoo.input.devices.components.Key >();
        
        // Loops though all of the registered keys in KeyCode
        for ( KeyID keyID : KeyID.values() )
        {
            final org.jagatoo.input.devices.components.Key key = keyID.getKey();
            
            try
            {
                // Converts the KeyCode constant name to the SWT constant name
                Field swtKey = SWT.class.getField( keyID.toString() );
                int swtKeyInt = swtKey.getInt( null );
                
                //System.out.println( field + " " + swtKeyInt + "=" + keyInt );
                
                // Sets SWT index to be the KeyCode value
                KEYCODE_CONVERSION.put( swtKeyInt, key );
            }
            catch ( NoSuchFieldException e )
            {
                if ( !CHAR_CONVERSION.containsValue( key ) )
                {
                    //System.out.println( "No field: " + e.getMessage() + " key code = " + keyInt );
                }
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Returns the HIAL key code for the given SWT key code. This is an O(1)
     * complexity operation.
     * 
     * @param ev the SWT key code to convert
     * 
     * @return the HIAL key code for the given SWT key code
     */
    public static org.jagatoo.input.devices.components.Key convertKey( org.eclipse.swt.events.KeyEvent ev )
    {
        try
        {
            return( KEYCODE_CONVERSION.get( ev.keyCode ) );
        }
        catch ( NullPointerException e )
        {
            try
            {
                return( CHAR_CONVERSION.get( Character.toLowerCase( ev.character ) ) );
            }
            catch ( NullPointerException e2 )
            {
                return( null );
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasKeyStateChanged( Key key, boolean keyState )
    {
        return( true );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    private final void notifyStatesManagersFromQueue( InputSystem is, EventQueue eventQueue, long nanoTime )
    {
        if ( eventQueue.getNumEvents() == 0 )
            return;
        
        synchronized ( EventQueue.LOCK )
        {
            for ( int i = 0; i < eventQueue.getNumEvents(); i++ )
            {
                final InputEvent event = eventQueue.getEvent( i );
                
                if ( event.getType() == InputEvent.Type.KEYBOARD_EVENT )
                {
                    final KeyboardEvent kbEvent = (KeyboardEvent)event;
                    
                    switch( kbEvent.getSubType() )
                    {
                        case PRESSED:
                            is.notifyInputStatesManagers( this, kbEvent.getComponent(), 1, +1, nanoTime );
                            break;
                        case RELEASED:
                            is.notifyInputStatesManagers( this, kbEvent.getComponent(), 0, -1, nanoTime );
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        try
        {
            notifyStatesManagersFromQueue( is, eventQueue, nanoTime );
            
            getEventQueue().dequeueAndFire( is );
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
        
        lastGameTimeDelta = System.nanoTime() - nanoTime;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        try
        {
        }
        catch ( Throwable t )
        {
            throw( new InputSystemException( t ) );
        }
    }
    
    protected SWTKeyboard( KeyboardFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Keyboard" );
        
        try
        {
            final org.eclipse.swt.widgets.Control control = (org.eclipse.swt.widgets.Control)sourceWindow.getDrawable();
            control.addKeyListener( new org.eclipse.swt.events.KeyListener()
            {
                public void keyPressed( org.eclipse.swt.events.KeyEvent _e )
                {
                    final org.jagatoo.input.devices.components.Key key = convertKey( _e );
                    final long when = ( (long)_e.time * 1000000L ) - lastGameTimeDelta;
                    
                    if ( key != null )
                    {
                        final int modifierMask = applyModifier( key, true );
                        
                        KeyPressedEvent e = prepareKeyPressedEvent( key, modifierMask, when, 0L );
                        
                        if ( e != null )
                        {
                            //fireOnKeyPressed( e, true );
                            getEventQueue().enqueue( e );
                        }
                        
                        if ( _e.character != '\0' )
                        {
                            KeyTypedEvent kte = prepareKeyTypedEvent( _e.character, modifierMask, when, 0L );
                            
                            if ( e != null )
                            {
                                getEventQueue().enqueue( kte );
                            }
                        }
                    }
                }
                
                public void keyReleased( org.eclipse.swt.events.KeyEvent _e )
                {
                    final org.jagatoo.input.devices.components.Key key = convertKey( _e );
                    final long when = ( (long)_e.time * 1000000L ) - lastGameTimeDelta;
                    
                    if ( key != null )
                    {
                        final int modifierMask = applyModifier( key, true );
                        
                        KeyReleasedEvent e = prepareKeyReleasedEvent( key, modifierMask, when, 0L );
                        
                        if ( e != null )
                        {
                            //fireOnKeyReleased( e, true );
                            getEventQueue().enqueue( e );
                        }
                    }
                }
            });
        }
        catch ( Throwable e )
        {
            throw( new InputSystemException( e ) );
        }
    }
}