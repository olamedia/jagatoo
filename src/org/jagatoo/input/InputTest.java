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
package org.jagatoo.input;

import javax.media.opengl.GL;

import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.devices.components.DigitalDeviceComponent.DigiState;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.events.KeyPressedEvent;
import org.jagatoo.input.events.KeyReleasedEvent;
import org.jagatoo.input.events.KeyStateEvent;
import org.jagatoo.input.events.KeyTypedEvent;
import org.jagatoo.input.events.MouseButtonEvent;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseStoppedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.impl.awt.AWTInputSystem;
import org.jagatoo.input.impl.jinput.JInputInputSystem;
import org.jagatoo.input.impl.lwjgl.LWJGLInputSystem;
import org.jagatoo.input.listeners.InputListener;
import org.jagatoo.input.misc.Canvas;
import org.jagatoo.input.misc.Cursor;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputTest implements InputListener
{
    public void onMouseButtonPressed( MouseButtonPressedEvent e, MouseButton button )
    {
        System.out.println( "Button pressed: " + e.getButton() + ", " + e.getX() + ", " + e.getY() );
    }
    
    public void onMouseButtonReleased( MouseButtonReleasedEvent e, MouseButton button )
    {
        System.out.println( "Button released: " + e.getButton() + ", " + e.getX() + ", " + e.getY() );
    }
    
    public void onMouseButtonStateChanged( MouseButtonEvent e, MouseButton button, DigiState state )
    {
        //System.out.println( "mouse button state: " + e.getButton() + ", " + state );
        
        try
        {
            if ( e.getButton() == MouseButtons.RIGHT_BUTTON )
            {
                e.getMouse().setAbsolute( !state.getBooleanValue() );
            }
        }
        catch ( InputSystemException ex )
        {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
    private int xxx = 0;
    
    public void onMouseMoved( MouseMovedEvent e, int x, int y, int dx, int dy )
    {
        System.out.println( "Mouse moved: " + x + ", " + y + ", " + dx + ", " + dy + ", " + e.getMouse().getButtonsState() );
        /*
        xxx += dx;
        System.out.println( xxx );
        */
    }
    
    public void onMouseWheelMoved( MouseWheelEvent e, int wheelDelta )
    {
        System.out.println( "Wheel moved: " + e.getWheelDelta() + ", " + e.isPageMove() + ", " + e.getMouse().getX() + ", " + e.getMouse().getY() + ", " + e.getMouse().getButtonsState() );
    }
    
    public void onMouseStopped( MouseStoppedEvent e, int x, int y )
    {
        //System.out.println( "Mouse stopped: " + x + ", " + y );
    }
    
    public void onKeyPressed( KeyPressedEvent e, Key key )
    {
        System.out.println( "key pressed: " + e.getKey() + ", " + e.getModifierMask() );
    }
    
    public void onKeyReleased( KeyReleasedEvent e, Key key )
    {
        System.out.println( "key released: " + e.getKey() + ", " + e.getModifierMask() );
    }
    
    public void onKeyStateChanged( KeyStateEvent e, Key key, DigiState state )
    {
        //System.out.println( "key state: " + e.getKey() + ", " + state );
    }
    
    public void onKeyTyped( KeyTypedEvent e, char keyChar )
    {
        //System.out.println( "key typed: " + keyChar + ", " + (int)keyChar + ", " + e.getModifierMask() );
    }
    
    public void onControllerAxisChanged( ControllerAxisChangedEvent e, ControllerAxis axis, float axisDelta )
    {
        //System.out.println( "axis changed: " + axis.getName() + ", normValue = " + axis.getNormalizedValue() );
        System.out.println( "axis changed: " + axis + ", normValue = " + axis.getNormalizedValue() + ", delta = " + axisDelta );
    }
    
    public void onControllerButtonPressed( ControllerButtonPressedEvent e, ControllerButton button )
    {
        System.out.println( "controller-button pressed: " + button );
    }
    
    public void onControllerButtonReleased( ControllerButtonReleasedEvent e, ControllerButton button )
    {
        System.out.println( "controller-button released: " + button );
    }
    
    public void onControllerButtonStateChanged( ControllerButtonEvent e, ControllerButton button, DigiState state )
    {
        System.out.println( "controller-button state: " + button + ", " + state );
    }
    
    private class TestAction implements InputAction
    {
        public void doAction( int delta, int state )
        {
            System.out.println( "TestAction: " + delta + ", " + state );
        }
    }
    
    private void setupInputSystem( InputSystem is ) throws Throwable
    {
        try
        {
            is.registerNewMouse();
            is.getMouse().addMouseListener( this );
            //is.getMouse().startMouseStopManager();
            
            MouseButtons.MIDDLE_BUTTON.bindAction( new TestAction() );
            is.getMouse().getWheel().bindAction( new TestAction() );
        }
        catch ( InputSystemException ex )
        {
            ex.printStackTrace();
        }
        
        try
        {
            is.registerNewKeyboard();
            is.getKeyboard().addKeyboardListener( this );
        }
        catch ( InputSystemException ex )
        {
            ex.printStackTrace();
        }
        
        try
        {
            Controller[] controllers = is.getDeviceFactory().getControllers();
            if ( controllers.length > 0 )
            {
                System.out.println( controllers[ 0 ].getName() );
                is.registerController( controllers[ 0 ] );
                controllers[ 0 ].addControllerListener( this );
            }
            //org.jagatoo.input.util.ControllerCalibrator.start( controllers[ 0 ] );
        }
        catch ( InputSystemException ex )
        {
            ex.printStackTrace();
        }
    }
    
    
    private static class LWJGLCanvas implements Canvas
    {
        private Cursor cursor = Cursor.DEFAULT_CURSOR;
        
        public Object getDrawable()
        {
            return( null );
        }
        
        public int getWidth()
        {
            return( org.lwjgl.opengl.Display.getDisplayMode().getWidth() );
        }
        
        public int getHeight()
        {
            return( org.lwjgl.opengl.Display.getDisplayMode().getHeight() );
        }
        
        public void setCursor( Cursor cursor )
        {
            this.cursor = cursor;
        }
        
        public void refreshCursor()
        {
        }
        
        public Cursor getCursor()
        {
            return( cursor );
        }
    };
    
    private static class AWTCanvas implements Canvas
    {
        final java.awt.Component component;
        
        private Cursor cursor = Cursor.DEFAULT_CURSOR;
        
        public Object getDrawable()
        {
            return( component );
        }
        
        public int getWidth()
        {
            return( component.getWidth() );
        }
        
        public int getHeight()
        {
            return( component.getHeight() );
        }
        
        public void setCursor( Cursor cursor )
        {
            this.cursor = cursor;
        }
        
        public void refreshCursor()
        {
        }
        
        public Cursor getCursor()
        {
            return( cursor );
        }
        
        public AWTCanvas( java.awt.Component component )
        {
            this.component = component;
        }
    };
    
    
    @SuppressWarnings("unused")
    private void startLWJGL() throws Throwable
    {
        Display.setDisplayMode( new DisplayMode( 1024, 768 ) );
        Display.create();
        Display.setLocation( 0, 0 );
        
        InputSystem is = null;
        try
        {
            is = new LWJGLInputSystem( new LWJGLCanvas() );
            setupInputSystem( is );
            
            while ( !Display.isCloseRequested() )
            {
                is.update( System.nanoTime() );
            }
        }
        catch ( InputSystemException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( is != null )
                is.destroy();
            
            Display.destroy();
        }
    }
    
    @SuppressWarnings("unused")
    private void startJInput() throws Throwable
    {
        Display.setDisplayMode( new DisplayMode( 1024, 768 ) );
        Display.create();
        Display.setLocation( 0, 0 );
        
        InputSystem is = null;
        try
        {
            is = new JInputInputSystem( new LWJGLCanvas() );
            setupInputSystem( is );
            
            while ( !Display.isCloseRequested() )
            {
                is.update( System.nanoTime() );
                
                Thread.yield();
            }
        }
        catch ( InputSystemException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( is != null )
                is.destroy();
            
            Display.destroy();
        }
    }
    
    @SuppressWarnings("unused")
    private void startAWT() throws Throwable
    {
        final JOGLBase jogl = new JOGLBase( "Test", 1024, 768 )
        {
            @Override
            protected void render( GL gl, int canvasWidth, int canvasHeight, long nanoTime )
            {
                //System.out.println( "sdfsdfsdf" );
            }
        };
        
        jogl.getFrame().setLocation( 0, 0 );
        
        InputSystem is = null;
        try
        {
            is = new AWTInputSystem( new AWTCanvas( jogl.getCanvas() ) );
            setupInputSystem( is );
            
            while ( jogl.getFrame().isDisplayable() )
            {
                is.update( System.nanoTime() );
                
                Thread.yield();
                //Thread.sleep( 200L );
            }
        }
        catch ( InputSystemException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( is != null )
                is.destroy();
        }
    }
    
    public InputTest() throws Throwable
    {
        startLWJGL();
        //startJInput();
        //startAWT();
    }
    
    public static void main( String[] args ) throws Throwable
    {
        new InputTest();
    }
}
