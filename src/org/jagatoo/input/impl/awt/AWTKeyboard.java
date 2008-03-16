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
package org.jagatoo.input.impl.awt;

import java.util.BitSet;
import java.util.HashMap;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.KeyPressedEvent;
import org.jagatoo.input.events.KeyReleasedEvent;
import org.jagatoo.input.events.KeyTypedEvent;
import org.jagatoo.input.events.KeyboardEvent;
import org.jagatoo.input.localization.KeyboardLocalizer;
import org.jagatoo.input.misc.Canvas;

/**
 * AWT implementation of the Keyboard class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class AWTKeyboard extends Keyboard
{
    private static final boolean IS_WINDOWS = ( System.getenv( "windir" ) != null );
    
    private static final Key convertKey( int awtKey )
    {
        switch ( awtKey )
        {
            case java.awt.event.KeyEvent.VK_ESCAPE: return( Keys.ESCAPE );
            
            case java.awt.event.KeyEvent.VK_F1: return( Keys.F1 );
            case java.awt.event.KeyEvent.VK_F2: return( Keys.F2 );
            case java.awt.event.KeyEvent.VK_F3: return( Keys.F3 );
            case java.awt.event.KeyEvent.VK_F4: return( Keys.F4 );
            case java.awt.event.KeyEvent.VK_F5: return( Keys.F5 );
            case java.awt.event.KeyEvent.VK_F6: return( Keys.F6 );
            case java.awt.event.KeyEvent.VK_F7: return( Keys.F7 );
            case java.awt.event.KeyEvent.VK_F8: return( Keys.F8 );
            case java.awt.event.KeyEvent.VK_F9: return( Keys.F9 );
            case java.awt.event.KeyEvent.VK_F10: return( Keys.F10 );
            case java.awt.event.KeyEvent.VK_F11: return( Keys.F11 );
            case java.awt.event.KeyEvent.VK_F12: return( Keys.F12 );
            
            case java.awt.event.KeyEvent.VK_PAUSE: return( Keys.PAUSE );
            case java.awt.event.KeyEvent.VK_SCROLL_LOCK: return( Keys.SCROLL_LOCK );
            
            case 130:
                if ( IS_WINDOWS )
                {
                    // On Windows the CIRCUMFLEX (^) Key is mapped to the wrong code!
                    return( Keys.CIRCUMFLEX );
                }
                
                return( null );
                
            case java.awt.event.KeyEvent.VK_CIRCUMFLEX: return( Keys.CIRCUMFLEX );
            
            case java.awt.event.KeyEvent.VK_0: return( Keys._0 );
            case java.awt.event.KeyEvent.VK_1: return( Keys._1 );
            case java.awt.event.KeyEvent.VK_2: return( Keys._2 );
            case java.awt.event.KeyEvent.VK_3: return( Keys._3 );
            case java.awt.event.KeyEvent.VK_4: return( Keys._4 );
            case java.awt.event.KeyEvent.VK_5: return( Keys._5 );
            case java.awt.event.KeyEvent.VK_6: return( Keys._6 );
            case java.awt.event.KeyEvent.VK_7: return( Keys._7 );
            case java.awt.event.KeyEvent.VK_8: return( Keys._8 );
            case java.awt.event.KeyEvent.VK_9: return( Keys._9 );
            
            case java.awt.event.KeyEvent.VK_A: return( Keys.A );
            case java.awt.event.KeyEvent.VK_B: return( Keys.B );
            case java.awt.event.KeyEvent.VK_C: return( Keys.C );
            case java.awt.event.KeyEvent.VK_D: return( Keys.D );
            case java.awt.event.KeyEvent.VK_E: return( Keys.E );
            case java.awt.event.KeyEvent.VK_F: return( Keys.F );
            case java.awt.event.KeyEvent.VK_G: return( Keys.G );
            case java.awt.event.KeyEvent.VK_H: return( Keys.H );
            case java.awt.event.KeyEvent.VK_I: return( Keys.I );
            case java.awt.event.KeyEvent.VK_J: return( Keys.J );
            case java.awt.event.KeyEvent.VK_K: return( Keys.K );
            case java.awt.event.KeyEvent.VK_L: return( Keys.L );
            case java.awt.event.KeyEvent.VK_M: return( Keys.M );
            case java.awt.event.KeyEvent.VK_N: return( Keys.N );
            case java.awt.event.KeyEvent.VK_O: return( Keys.O );
            case java.awt.event.KeyEvent.VK_P: return( Keys.P );
            case java.awt.event.KeyEvent.VK_Q: return( Keys.Q );
            case java.awt.event.KeyEvent.VK_R: return( Keys.R );
            case java.awt.event.KeyEvent.VK_S: return( Keys.S );
            case java.awt.event.KeyEvent.VK_T: return( Keys.T );
            case java.awt.event.KeyEvent.VK_U: return( Keys.U );
            case java.awt.event.KeyEvent.VK_V: return( Keys.V );
            case java.awt.event.KeyEvent.VK_W: return( Keys.W );
            case java.awt.event.KeyEvent.VK_X: return( Keys.X );
            case java.awt.event.KeyEvent.VK_Y: return( Keys.Y );
            case java.awt.event.KeyEvent.VK_Z: return( Keys.Z );
            
            case java.awt.event.KeyEvent.VK_TAB: return( Keys.TAB );
            case java.awt.event.KeyEvent.VK_SPACE: return( Keys.SPACE );
            case java.awt.event.KeyEvent.VK_BACK_SPACE: return( Keys.BACK_SPACE );
            
            case java.awt.event.KeyEvent.VK_ALT: return( Keys.ALT );
            case java.awt.event.KeyEvent.VK_ALT_GRAPH: return( Keys.ALT_GRAPH );
            case java.awt.event.KeyEvent.VK_CAPS_LOCK: return( Keys.CAPS_LOCK );
            
            case java.awt.event.KeyEvent.VK_DELETE: return( Keys.DELETE );
            case java.awt.event.KeyEvent.VK_INSERT: return( Keys.INSERT );
            case java.awt.event.KeyEvent.VK_END: return( Keys.END );
            case java.awt.event.KeyEvent.VK_HOME: return( Keys.HOME );
            case java.awt.event.KeyEvent.VK_PAGE_UP: return( Keys.PAGE_UP );
            case java.awt.event.KeyEvent.VK_PAGE_DOWN: return( Keys.PAGE_DOWN );
            
            case java.awt.event.KeyEvent.VK_RIGHT: return( Keys.RIGHT );
            case java.awt.event.KeyEvent.VK_LEFT: return( Keys.LEFT );
            case java.awt.event.KeyEvent.VK_UP: return( Keys.UP );
            case java.awt.event.KeyEvent.VK_DOWN: return( Keys.DOWN );
            
            case java.awt.event.KeyEvent.VK_NUM_LOCK      : return( Keys.NUM_LOCK );
            
            case java.awt.event.KeyEvent.VK_DIVIDE      : return( Keys.NUMPAD_DIVIDE );
            case java.awt.event.KeyEvent.VK_MULTIPLY   : return( Keys.NUMPAD_MULTIPLY );
            case java.awt.event.KeyEvent.VK_SUBTRACT   : return( Keys.NUMPAD_SUBTRACT );
            case java.awt.event.KeyEvent.VK_ADD         : return( Keys.NUMPAD_ADD );
            //case java.awt.event.KeyEvent.VK_NUM : return( Keys.NUMPAD_DECIMAL );
            
            case java.awt.event.KeyEvent.VK_NUMPAD0: return( Keys.NUMPAD0 );
            case java.awt.event.KeyEvent.VK_NUMPAD1: return( Keys.NUMPAD1 );
            case java.awt.event.KeyEvent.VK_NUMPAD2: return( Keys.NUMPAD2 );
            case java.awt.event.KeyEvent.VK_NUMPAD3: return( Keys.NUMPAD3 );
            case java.awt.event.KeyEvent.VK_NUMPAD4: return( Keys.NUMPAD4 );
            case java.awt.event.KeyEvent.VK_NUMPAD5: return( Keys.NUMPAD5 );
            case java.awt.event.KeyEvent.VK_NUMPAD6: return( Keys.NUMPAD6 );
            case java.awt.event.KeyEvent.VK_NUMPAD7: return( Keys.NUMPAD7 );
            case java.awt.event.KeyEvent.VK_NUMPAD8: return( Keys.NUMPAD8 );
            case java.awt.event.KeyEvent.VK_NUMPAD9: return( Keys.NUMPAD9 );
        }
        
        return( null );
    }
    
    public static final Key convertKey( int awtKey, int keyLocation, char keyChar )
    {
        Key key;
        switch ( awtKey )
        {
            case java.awt.event.KeyEvent.VK_SHIFT:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_SHIFT;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_SHIFT;
                break;
            case java.awt.event.KeyEvent.VK_CONTROL:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_CONTROL;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_CONTROL;
                break;
            case java.awt.event.KeyEvent.VK_META:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_META;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_META;
                break;
            case java.awt.event.KeyEvent.VK_ENTER:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_NUMPAD )
                    key = Keys.NUMPAD_ENTER;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_STANDARD )*/
                    key = Keys.ENTER;
                break;
            case java.awt.event.KeyEvent.VK_DECIMAL:
                //if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_NUMPAD )
                    key = Keys.NUMPAD_DECIMAL;
                break;
            default:
                key = convertKey( awtKey );
        }
        
        if ( key == null )
            key = KeyboardLocalizer.getMapping().getLocalizedKey( keyChar );
        
        if ( ( key == Keys.DELETE ) && ( keyChar != '\0' ) )
            key = Keys.NUMPAD_DECIMAL;
        
        return( key );
    }
    
    private final BitSet pressedKeys = new BitSet();
    private final HashMap< Key, KeyboardEvent[] > pressedReleased = new HashMap< Key, KeyboardEvent[] >();
    
    private final BitSet oldKeyStates = new BitSet();
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasKeyStateChanged( Key key, boolean keyState )
    {
        final int bitIndex = key.getKeyCode() - 1;
        
        //System.out.println( keyState + ", " + oldKeyStates.get( bitIndex ) );
        
        if ( keyState )
        {
            if ( !oldKeyStates.get( bitIndex ) )
            {
                oldKeyStates.set( bitIndex );
                
                return( true );
            }
            
            return( false );
        }
        else
        {
            if ( oldKeyStates.get( bitIndex ) )
            {
                oldKeyStates.clear( bitIndex );
                
                return( true );
            }
            
            return( false );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        try
        {
            getEventQueue().dequeueAndFire( is );
            
            keyEventFixThread.fireEvents();
            
            /*
            if ( !pressedReleased.isEmpty() )
            {
                synchronized ( pressedReleased )
                {
                    Iterator< KeyboardEvent[] > it = pressedReleased.values().iterator();
                    while ( it.hasNext() )
                    {
                        KeyboardEvent[] events = it.next();
                        
                        if ( events[ 0 ] != null )
                        {
                            final KeyPressedEvent e = (KeyPressedEvent)events[ 0 ];
                            if ( pressedKeys.get( e.getKey().getKeyCode() ) )
                            {
                                fireOnKeyPressed( e, true );
                                events[ 0 ] = null;
                            }
                        }
                        
                        if ( events[ 1 ] != null )
                        {
                            final KeyReleasedEvent e = (KeyReleasedEvent)events[ 1 ];
                            if ( !pressedKeys.get( e.getKey().getKeyCode() ) )
                            {
                                fireOnKeyReleased( e, true );
                                events[ 1 ] = null;
                            }
                        }
                        
                        if ( ( events[ 0 ] == null ) && ( events[ 1 ] == null ) )
                        {
                            it.remove();
                        }
                    }
                }
            }
            */
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
    
    
    private class KeyEventFixThread implements Runnable
    {
        private KeyPressedEvent lastPressedEvent = null;
        private KeyReleasedEvent lastReleasedEvent = null;
        private long lastPressedTime = -1L;
        private long lastReleasedTime = -1L;
        
        public void processEvent( java.awt.event.KeyEvent _e )
        {
            final Key key = convertKey( _e.getKeyCode(), _e.getKeyLocation(), _e.getKeyChar() );
            
            if ( key != null )
            {
                synchronized ( this )
                {
                    switch ( _e.getID() )
                    {
                        case java.awt.event.KeyEvent.KEY_PRESSED:
                        {
                            if ( ( lastReleasedEvent != null ) && ( _e.getWhen() == lastReleasedTime ) )
                            {
                                lastReleasedEvent = null;
                                lastPressedEvent = null;
                                return;
                            }
                            
                            if ( lastPressedEvent == null )
                            {
                                final int modifierMask = applyModifier( key, true );
                                
                                KeyPressedEvent e = prepareKeyPressedEvent( key, modifierMask, 0L, 0L );
                                
                                lastPressedEvent = e;
                            }
                            
                            lastPressedTime = _e.getWhen();
                            
                            break;
                        }
                        case java.awt.event.KeyEvent.KEY_RELEASED:
                        {
                            if ( lastReleasedEvent == null )
                            {
                                final int modifierMask = applyModifier( key, false );
                                
                                KeyReleasedEvent e = prepareKeyReleasedEvent( key, modifierMask, 0L, 0L );
                                
                                lastReleasedEvent = e;
                            }
                            
                            lastReleasedTime = _e.getWhen();
                            
                            break;
                        }
                    }
                }
            }
        }
        
        public void fireEvents()
        {
            synchronized ( this )
            {
                if ( lastPressedEvent != null )
                {
                    fireOnKeyPressed( lastPressedEvent, true );
                }
                else if ( lastReleasedEvent != null )
                {
                    fireOnKeyReleased( lastReleasedEvent, true );
                }
            }
        }
        
        public void run()
        {
            
        }
    };
    
    private final KeyEventFixThread keyEventFixThread = new KeyEventFixThread();
    
    private void processKeyEvent( java.awt.event.KeyEvent _e )
    {
        if ( !isEnabled() )
            return;
        
        switch ( _e.getID() )
        {
            case java.awt.event.KeyEvent.KEY_PRESSED:
            case java.awt.event.KeyEvent.KEY_RELEASED:
            {
                keyEventFixThread.processEvent( _e );
                
                break;
            }
            case java.awt.event.KeyEvent.KEY_TYPED:
            {
                //final Key key = convertKey( _e.getKeyCode(), _e.getKeyLocation(), _e.getKeyChar() );
                final int modifierMask = getModifierMask();
                
                char keyChar = _e.getKeyChar();
                if ( _e.getKeyChar() == 65452 )
                {
                    keyChar = KeyboardLocalizer.getMapping().getModifiedChar( Keys.NUMPAD_DECIMAL, '\0', 0 );
                }
                
                KeyTypedEvent e = prepareKeyTypedEvent( keyChar, modifierMask, 0L, 0L );
                
                getEventQueue().enqueue( e );
                
                break;
            }
        }
    }
    
    private final java.awt.event.AWTEventListener eventListener = new java.awt.event.AWTEventListener()
    {
        public void eventDispatched( java.awt.AWTEvent event )
        {
            processKeyEvent( (java.awt.event.KeyEvent)event );
        }
        
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        try
        {
            java.awt.Toolkit.getDefaultToolkit().removeAWTEventListener( eventListener );
        }
        catch ( Throwable t )
        {
            throw( new InputSystemException( t ) );
        }
    }
    
    protected AWTKeyboard( EventQueue eveneQueue, Canvas canvas ) throws InputSystemException
    {
        super( eveneQueue, canvas, "Primary Keyboard" );
        
        try
        {
            /*
            final java.awt.Component component = (java.awt.Component)canvas.getDrawable();
            component.addKeyListener( new java.awt.event.KeyListener()
            {
                public void keyPressed( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
                
                public void keyReleased( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
                
                public void keyTyped( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
            } );
            */
            
            java.awt.Toolkit.getDefaultToolkit().addAWTEventListener( eventListener, java.awt.AWTEvent.KEY_EVENT_MASK );
            new Thread( keyEventFixThread ).start();
        }
        catch ( Throwable e )
        {
            throw( new InputSystemException( e ) );
        }
    }
}
