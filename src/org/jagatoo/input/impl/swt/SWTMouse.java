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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.MouseFactory;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseEvent;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * SWT implementation of the Mouse class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class SWTMouse extends Mouse
{
    private int calibrationStep = -1;
    
    private final org.eclipse.swt.widgets.Control control;
    private final java.awt.Point centerControl = new java.awt.Point( 0, 0 );
    private final java.awt.Point los = new java.awt.Point( 0, 0 );
    private int calibX = 1, calibY = 1;
    
    private int lastAbsoluteX = 0;
    private int lastAbsoluteY = 0;
    
    private long lastGameTimeDelta = System.nanoTime();
    
    private static MouseButton convertButton( int swtButton )
    {
        switch ( swtButton )
        {
            case 1:
                return MouseButtons.LEFT_BUTTON;
            case 2:
                return MouseButtons.MIDDLE_BUTTON;
            case 3:
                return MouseButtons.RIGHT_BUTTON;
        }
        
        return( null );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition( int x, int y ) throws InputSystemException
    {
        super.setPosition( x, y );
        
        try
        {
            if ( isAbsolute() )
            {
                /*
                ensureRobot();
                robot.mouseMove( los.x + x, los.y + y );
                */
            }
            else
            {
                /*
                lastAbsoluteX = x;
                lastAbsoluteY = y;
                */
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
    public void centerMouse() throws InputSystemException
    {
        try
        {
            control.getDisplay().setCursorLocation( los.x + calibX + centerControl.x,
                                                    los.y + calibY + centerControl.y
                                                  );
        }
        catch ( Throwable t )
        {
            throw( new InputSystemException( t ) );
        }
        
        //setPosition( centerX, centerY );
    }
    
    /**
     * Calculates the positions needed for exlusive mode.
     * Make sure to call either this method after the first frame is rendered or call the setExclusive(true) after it.
     */
    private void updateCenters()
    {
        // calculate center-of-component (in absolute sizes)
        control.getDisplay().asyncExec( new Runnable()
        {
            public void run()
            {
                los.x = control.getLocation().x;
                los.y = control.getLocation().y;
                
                Composite parent = control.getParent();
                while ( parent != null )
                {
                    los.x += parent.getLocation().x;
                    los.y += parent.getLocation().y;
                    
                    parent = parent.getParent();
                }
                
                centerControl.x = ( control.getSize().x / 2 ) + 1;
                centerControl.y = ( control.getSize().y / 2 ) + 1;
                
                parent = control.getParent();
                while ( ( centerControl.x == 1 ) && ( centerControl.y == 1 ) && ( parent != null ) )
                {
                    centerControl.x = ( parent.getSize().x / 2 ) + 1;
                    centerControl.y = ( parent.getSize().y / 2 ) + 1;
                    
                    parent = parent.getParent();
                }
                
                calibrationStep = 0;
            }
        } );
    }
    
    /**
     * Moves the mouse back to the center.
     */
    private void recenter() throws InputSystemException
    {
        if ( control.getDisplay().getThread().equals( Thread.currentThread() ) )
        {
            control.getDisplay().setCursorLocation( los.x + calibX + centerControl.x, los.y + calibY + centerControl.y );
        }
        else
        {
            control.getDisplay().asyncExec( new Runnable()
            {
                public void run()
                {
                    control.getDisplay().setCursorLocation( los.x + calibX + centerControl.x, los.y + calibY + centerControl.y );
                }
            } );
        }
    }
    
    /**
     * Calibrates the mouse to calculate Window-Decoration size.
     * 
     * @param mouseX
     * @param mouseY
     */
    private void calibrate( int mouseX, int mouseY )
    {
        if ( ( getCurrentX() == -1 ) || ( getCurrentY() == -1 ) )
            return;
        
        switch ( calibrationStep )
        {
            case 0:
                control.getDisplay().setCursorLocation( los.x + mouseX, los.y + mouseY );
                calibX = mouseX;
                calibY = mouseY;
                
                calibrationStep = 1;
                break;
            
            case 1:
                calibX = ( calibX - mouseX );
                calibY = ( calibY - mouseY );
                //System.out.println( calibX + ", " + calibY );
                
                try
                {
                    recenter();
                }
                catch ( InputSystemException e )
                {
                    e.printStackTrace();
                }
                calibrationStep = -1;
                break;
        }
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
                
                if ( event.getType() == InputEvent.Type.MOUSE_EVENT )
                {
                    final MouseEvent moEvent = (MouseEvent)event;
                    
                    switch ( moEvent.getSubType() )
                    {
                        case BUTTON_PRESSED:
                            is.notifyInputStatesManagers( this, moEvent.getComponent(), 1, +1, nanoTime );
                            break;
                        case BUTTON_RELEASED:
                            is.notifyInputStatesManagers( this, moEvent.getComponent(), 0, -1, nanoTime );
                            break;
                        case WHEEL_MOVED:
                            final MouseWheelEvent mwEvent = (MouseWheelEvent)moEvent;
                            final MouseWheel wheel = (MouseWheel)mwEvent.getComponent();
                            is.notifyInputStatesManagers( this, wheel, wheel.getIntValue(), mwEvent.getWheelDelta(), nanoTime );
                            break;
                        case MOVED:
                            final MouseMovedEvent mmEvent = (MouseMovedEvent)moEvent;
                            if ( mmEvent.getDX() != 0 )
                                is.notifyInputStatesManagers( this, mmEvent.getMouse().getXAxis(), mmEvent.getX(), mmEvent.getDX(), nanoTime );
                            
                            if ( mmEvent.getDY() != 0 )
                                is.notifyInputStatesManagers( this, mmEvent.getMouse().getYAxis(), mmEvent.getY(), mmEvent.getDY(), nanoTime );
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
            
            getEventQueue().dequeueAndFire( is, InputEvent.Type.MOUSE_EVENT );
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
    protected void setAbsoluteImpl( boolean absolute ) throws InputSystemException
    {
        try
        {
            updateCenters();
            recenter();
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
    public void destroyImpl() throws InputSystemException
    {
        try
        {
            //java.awt.Toolkit.getDefaultToolkit().removeAWTEventListener( eventListener );
        }
        catch ( Throwable t )
        {
            throw( new InputSystemException( t ) );
        }
    }
    
    protected SWTMouse( MouseFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Mouse", 12, true );
        
        try
        {
            this.control = (org.eclipse.swt.widgets.Control)sourceWindow.getDrawable();
            
            //final org.eclipse.swt.widgets.Display display = control.getDisplay();
            
            control.addMouseListener( new org.eclipse.swt.events.MouseListener()
            {
                public void mouseDoubleClick( org.eclipse.swt.events.MouseEvent _e )
                {
                }
                
                public void mouseDown( org.eclipse.swt.events.MouseEvent _e )
                {
                    final MouseButton button = convertButton( _e.button );
                    if ( button != null )
                    {
                        MouseButtonPressedEvent e = prepareMouseButtonPressedEvent( button, System.nanoTime() - lastGameTimeDelta );
                        
                        if ( e == null )
                            return;
                        
                        getEventQueue().enqueue( e );
                    }
                }
                
                public void mouseUp( org.eclipse.swt.events.MouseEvent _e )
                {
                    final MouseButton button = convertButton( _e.button );
                    if ( button != null )
                    {
                        MouseButtonReleasedEvent e = prepareMouseButtonReleasedEvent( button, System.nanoTime() - lastGameTimeDelta );
                        
                        if ( e == null )
                            return;
                        
                        getEventQueue().enqueue( e );
                    }
                }
            } );
            
            control.addMouseMoveListener( new org.eclipse.swt.events.MouseMoveListener()
            {
                public void mouseMove( org.eclipse.swt.events.MouseEvent _e )
                {
                    final int x = _e.x;
                    final int y = _e.y;
                    //final int dX = ( x - centerControl.x );
                    //final int dY = -( y - centerControl.y );
                    final long when = System.nanoTime() - lastGameTimeDelta;
                    
                    boolean doEvent = true;
                    if ( !isAbsolute() )
                    {
                        if ( calibrationStep != -1 )
                        {
                            calibrate( x, y );
                            doEvent = false;
                        }
                    }
                    
                    if ( doEvent && ( ( getCurrentX() != x ) || ( getCurrentY() != y ) ) )
                    {
                        if ( isAbsolute() )
                        {
                            final int dx = x - getCurrentX();
                            final int dy = -( y - getCurrentY() );
                            
                            storePosition( x, y );
                            
                            MouseMovedEvent e = prepareMouseMovedEvent( x, y, dx, dy, when );
                            
                            if ( e == null )
                                return;
                            
                            getEventQueue().enqueue( e );
                            
                            lastAbsoluteX = x;
                            lastAbsoluteY = y;
                        }
                        else
                        {
                            final int dx = x - centerControl.x;
                            final int dy = -( y - centerControl.y );
                            
                            if ( dx != 0 || dy != 0 )
                            {
                                MouseMovedEvent e = prepareMouseMovedEvent( lastAbsoluteX, lastAbsoluteY, dx, dy, when );
                                
                                if ( e == null )
                                    return;
                                
                                getEventQueue().enqueue( e );
                                
                                try
                                {
                                    recenter();
                                }
                                catch ( InputSystemException ise )
                                {
                                    ise.printStackTrace();
                                }
                            }
                        }
                    }
                    
                }
            } );
            
            control.addListener( SWT.MouseWheel, new org.eclipse.swt.widgets.Listener()
            {
                public void handleEvent( org.eclipse.swt.widgets.Event _e )
                {
                    
                    long when = System.nanoTime() - lastGameTimeDelta;
                    
                    MouseWheelEvent e = prepareMouseWheelMovedEvent( -_e.count, false, when );
                    
                    if ( e != null )
                        getEventQueue().enqueue( e );
                }
            } );
        }
        catch ( Throwable e )
        {
            throw( new InputSystemException( e ) );
        }
    }
}
