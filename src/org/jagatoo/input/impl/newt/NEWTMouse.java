/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.input.impl.newt;

import javax.media.nativewindow.util.Point;
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
import org.jagatoo.input.events.MouseEventPool;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.render.InputSourceWindow;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;


/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class NEWTMouse extends Mouse {

    private GLWindow newtWindow;
    
    private Point los;
    
    private int lastAbsoluteX;
    
    private int lastAbsoluteY;
    
    @SuppressWarnings("unused")
    private int lastRelX = -1;
    @SuppressWarnings("unused")
    private int lastRelY = -1;
    
    private int nextIgnoredX = -1;
    private int nextIgnoredY = -1;
    
    private boolean needsRecenter;
    
    private long lastKnownNanoTime = -1L;
    
    private final MouseListener mouseListener;

    protected NEWTMouse( MouseFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Mouse", buttonMap.length, true );
        newtWindow = (GLWindow) sourceWindow.getDrawable();
        mouseListener = new MouseListener() {
            
            public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseReleased(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mousePressed(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseMoved(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseExited(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseEntered(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseDragged(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
            
            public void mouseClicked(com.jogamp.newt.event.MouseEvent me){
                processMouseEvent(me);
            }
        };
        newtWindow.addMouseListener(mouseListener);
        trapInitialLocationOnScreen( newtWindow );
    }
    
    private static final MouseButton[] buttonMap = new MouseButton[ /*12*/3 ];
    static
    {
        buttonMap[ com.jogamp.newt.event.MouseEvent.BUTTON1 ] = MouseButtons.LEFT_BUTTON;
        buttonMap[ com.jogamp.newt.event.MouseEvent.BUTTON3 ] = MouseButtons.RIGHT_BUTTON;
        buttonMap[ com.jogamp.newt.event.MouseEvent.BUTTON2 ] = MouseButtons.MIDDLE_BUTTON;
    }
    
    public static final MouseButton convertButton( int newtButton )
    {
        return ( buttonMap[ newtButton ] );
    }
    
    /**
     * Calculates the positions needed for exclusive mode.
     * Make sure to call either this method after the first frame is rendered or call the setExclusive(true) after it.
     */
    private void updateCenters()
    {
        // calculate center-of-component (in absolute sizes)
        los = newtWindow.getLocationOnScreen(los);
        final int centerX = los.getX() + ( newtWindow.getWidth() / 2) + 1;
        final int centerY = los.getY() + ( newtWindow.getHeight() / 2 ) + 1;
        newtWindow.setPosition( centerX, centerY );
    }
    
    /**
     * Moves the mouse back to the center.
     */
    private void recenter() throws InputSystemException
    {
        
        nextIgnoredX = newtWindow.getX() - los.getX();
        nextIgnoredY = newtWindow.getY() - los.getY();
        
        lastRelX = nextIgnoredX;
        lastRelY = nextIgnoredY;
        
        needsRecenter = false;
        
        newtWindow.warpPointer( newtWindow.getX(), newtWindow.getY() );
    }
        
    @Override
    public void centerMouse() throws InputSystemException
    {
        int centerX = 0;
        int centerY = 0;
        try
        {
            centerX = getSourceWindow().getWidth() / 2;
            centerY = getSourceWindow().getHeight() / 2;
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
        setPosition(centerX, centerY);
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
                newtWindow.warpPointer(los.getX() + x, los.getY() + y);
            }
            else
            {
                lastAbsoluteX = x;
                lastAbsoluteY = y;
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
        
            if ( t instanceof Error )
                throw (Error)t;
        
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
        
            throw new InputSystemException( t );
        }
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
                    
                    switch( moEvent.getSubType() )
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
    public void updateMouse( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        lastKnownNanoTime = nanoTime;
        
        try
        {
            notifyStatesManagersFromQueue( is, eventQueue, nanoTime );
            
            getEventQueue().dequeueAndFire( is, InputEvent.Type.MOUSE_EVENT );
            
            handleClickedEvents( nanoTime, is.getMouseButtonClickThreshold() );
            
            if ( !isAbsolute() && needsRecenter )
            {
                //recenter();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
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
            
            if ( absolute )
            {
                setPosition( getCurrentX(), getCurrentY() );
            }
            else
            {
                recenter();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
    }
    
    private void processMouseEvent( com.jogamp.newt.event.MouseEvent _e )
    {
        if ( !isEnabled() || !getSourceWindow().receivesInputEvents() )
            return;
        
        switch ( _e.getEventType() )
        {
            case com.jogamp.newt.event.MouseEvent.EVENT_MOUSE_WHEEL_MOVED:
            {
                processMouseWheelEvent(_e);
                break;
            }
            case com.jogamp.newt.event.MouseEvent.EVENT_MOUSE_PRESSED:
            {
                final MouseButton button = convertButton( _e.getButton() );
                if ( button != null )
                {
                    MouseButtonPressedEvent e = prepareMouseButtonPressedEvent( button, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                }
                
                break;
            }
            case com.jogamp.newt.event.MouseEvent.EVENT_MOUSE_RELEASED:
            {
                final MouseButton button = convertButton( _e.getButton() );
                if ( button != null )
                {
                    MouseButtonReleasedEvent e = prepareMouseButtonReleasedEvent( button, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                }
                
                break;
            }
            case com.jogamp.newt.event.MouseEvent.EVENT_MOUSE_MOVED:
            case com.jogamp.newt.event.MouseEvent.EVENT_MOUSE_DRAGGED:
            {
                if ( isAbsolute() )
                {
                    final int x = _e.getX();
                    final int y = _e.getY();
                    final int dx = x - getCurrentX();
                    final int dy = y - getCurrentY();
                    
                    storePosition( x, y );
                    
                    MouseMovedEvent e = prepareMouseMovedEvent( x, y, dx, dy, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                    
                    lastAbsoluteX = x;
                    lastAbsoluteY = y;
                }
                else
                {
                    final int dx = _e.getX() - newtWindow.getX() + los.getX();
                    final int dy = _e.getY() - newtWindow.getY() + los.getY();
                    
                    if ( ( dx != 0 ) || ( dy != 0 ) )
                    {
                        lastRelX = _e.getX();
                        lastRelY = _e.getY();
                        
                        nextIgnoredX = -1;
                        nextIgnoredY = -1;
                        
                        MouseMovedEvent e = prepareMouseMovedEvent( lastAbsoluteX, lastAbsoluteY, dx, dy, lastKnownNanoTime );
                        
                        if ( e == null )
                            return;
                        
                        getEventQueue().enqueue( e );
                        
                        needsRecenter = true;
                        
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
                
                break;
            }
        }
    }
    
    private void processMouseWheelEvent( com.jogamp.newt.event.MouseEvent _e )
    {
        if ( !isEnabled() )
            return;
        
        com.jogamp.newt.event.MouseEvent __e = _e;
        //FIXME confirm, not tested
        final boolean isPageMove = /*__e.getScrollType() == java.awt.event.MouseWheelEvent.WHEEL_BLOCK_SCROLL*/false;
        
        MouseWheelEvent e = MouseEventPool.allocWheel( this, getWheel(), -__e.getWheelRotation(), isPageMove, lastKnownNanoTime, 0L );
        
        if ( e != null )
            getEventQueue().enqueue( e );
    }
    
    private final void trapInitialLocationOnScreen( final GLWindow glWindow )
    {
        if ( glWindow.isVisible() )
        {
            this.los = glWindow.getLocationOnScreen(los);
        }
        else
        {
            glWindow.addWindowListener(new WindowAdapter(){
                
                public void windowRepaint(WindowUpdateEvent arg0){
                    NEWTMouse.this.los = glWindow.getLocationOnScreen(los);
                    glWindow.removeWindowListener( this );                    
                }
            });
        }
    }

    @Override
    protected void destroyImpl() throws InputSystemException
    {
        newtWindow.removeMouseListener(mouseListener);
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

}
