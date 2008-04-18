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

import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.DeviceFactory;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.InputSourceWindow;

public abstract class InputSystem
{
    private DeviceFactory deviceFactory;
    
    private Keyboard[] keyboards = new Keyboard[ 0 ];
    private Mouse[] mouses = new Mouse[ 0 ];
    private Controller[] controllers = new Controller[ 0 ];
    
    private Keyboard[] keyboards_out = null;
    private Mouse[] mouses_out = null;
    private Controller[] controllers_out = null;
    
    private final EventQueue eventQueue;
    
    protected final void setDeviceFactory( DeviceFactory devFact )
    {
        this.deviceFactory = devFact;
    }
    
    public final DeviceFactory getDeviceFactory()
    {
        return( deviceFactory );
    }
    
    
    public void registerKeyboard( Keyboard keyboard ) throws InputSystemException
    {
        if ( !keyboard.getClass().isAssignableFrom( getDeviceFactory().getExpectedKeyboardClass() ) )
            throw( new InputSystemException( "This Keyboard is not an instance of " + getDeviceFactory().getExpectedKeyboardClass() + "." ) );
        
        for ( int i = 0; i < keyboards.length; i++ )
        {
            if ( keyboards[ i ] == keyboard )
                throw( new InputSystemException( "This Keyboard is already registered at this InputSystem." ) );
        }
        
        Keyboard[] keyboards2 = new Keyboard[ keyboards.length + 1 ];
        System.arraycopy( keyboards, 0, keyboards2, 0, keyboards.length );
        keyboards2[ keyboards.length ] = keyboard;
        
        this.keyboards = keyboards2;
    }
    
    /**
     * Registers a new Keyboard.
     * 
     * @return the registered Keyboard.
     * 
     * @throws InputSystemException
     */
    public final Keyboard registerNewKeyboard() throws InputSystemException
    {
        Keyboard[] allKeyboards = getDeviceFactory().getKeyboards();
        Keyboard[] newKeyboards = new Keyboard[ allKeyboards.length ];
        int numNewKeyboards = 0;
        
        for ( int i = 0; i < allKeyboards.length; i++ )
        {
            boolean b = false;
            for ( int j = 0; j < this.keyboards.length; j++ )
            {
                if ( this.keyboards[ j ].equals( allKeyboards[ i ] ) )
                {
                    b = true;
                    break;
                }
            }
            
            if ( !b )
            {
                newKeyboards[ numNewKeyboards++ ] = allKeyboards[ i ];
            }
        }
        
        if ( numNewKeyboards == 0 )
        {
            throw( new InputSystemException( "There's no available Keyboard to register." ) );
        }
        
        registerKeyboard( newKeyboards[ 0 ] );
        
        return( newKeyboards[ 0 ] );
    }
    
    public void deregisterKeyboard( Keyboard keyboard ) throws InputSystemException
    {
        if ( keyboards.length == 0 )
            throw( new InputSystemException( "This Keyboard is not registered at this InputSystem." ) );
        
        boolean found = false;
        Keyboard[] keyboards2 = new Keyboard[ keyboards.length - 1 ];
        int j = 0;
        for ( int i = 0; i < keyboards.length; i++ )
        {
            if ( keyboards[ i ] == keyboard )
                found = true;
            else
                keyboards2[ j++ ] = keyboards[ i ];
        }
        
        if ( !found )
            throw( new InputSystemException( "This Keyboard is not registered at this InputSystem." ) );
        
        keyboard.destroy();
        
        this.keyboards = keyboards2;
    }
    
    public final boolean hasKeyboard()
    {
        return( keyboards.length > 0 );
    }
    
    public final int getKeyboardsCount()
    {
        return( keyboards.length );
    }
    
    /**
     * @return the first registered Keyboard.
     */
    public final Keyboard getKeyboard()
    {
        if ( keyboards.length >= 1 )
            return( keyboards[ 0 ] );
        
        return( null );
    }
    
    /**
     * @param index
     * 
     * @return the i-th registered Keyboard.
     */
    public final Keyboard getKeyboard( int index )
    {
        return( keyboards[ index ] );
    }
    
    /**
     * @return an array of all registered Keyboards in this InputSystem.
     */
    public final Keyboard[] getKeyboards()
    {
        if ( ( keyboards_out == null ) || ( keyboards_out.length != keyboards.length ) )
        {
            keyboards_out = new Keyboard[ keyboards.length ];
            System.arraycopy( keyboards, 0, keyboards_out, 0, keyboards.length );
        }
        
        return( keyboards_out );
    }
    
    /**
     * Checks, wether the given Keyboard is already registered at this InputSystem.
     * 
     * @param keyboard
     * 
     * @return true, if the Keyboard is registered.
     */
    public final boolean isKeyboardRegistered( Keyboard keyboard )
    {
        for ( int i = 0; i < keyboards.length; i++ )
        {
            if ( keyboards[ i ] == keyboard )
                return( true );
        }
        
        return( false );
    }
    
    
    public void registerMouse( Mouse mouse ) throws InputSystemException
    {
        if ( !mouse.getClass().isAssignableFrom( getDeviceFactory().getExpectedMouseClass() ) )
            throw( new InputSystemException( "This mouse is not an instance of " + getDeviceFactory().getExpectedMouseClass() + "." ) );
        
        for ( int i = 0; i < mouses.length; i++ )
        {
            if ( mouses[ i ] == mouse )
                throw( new InputSystemException( "This Mouse is already registered at this InputSystem." ) );
        }
        
        Mouse[] mouses2 = new Mouse[ mouses.length + 1 ];
        System.arraycopy( mouses, 0, mouses2, 0, mouses.length );
        mouses2[ mouses.length ] = mouse;
        
        this.mouses = mouses2;
    }
    
    /**
     * Registers a new Mouse.
     * 
     * @return the registered Mouse.
     * 
     * @throws InputSystemException
     */
    public final Mouse registerNewMouse() throws InputSystemException
    {
        Mouse[] allMouses = getDeviceFactory().getMouses();
        Mouse[] newMouses = new Mouse[ allMouses.length ];
        int numNewMouses = 0;
        
        for ( int i = 0; i < allMouses.length; i++ )
        {
            boolean b = false;
            for ( int j = 0; j < this.mouses.length; j++ )
            {
                if ( this.mouses[ j ].equals( allMouses[ i ] ) )
                {
                    b = true;
                    break;
                }
            }
            
            if ( !b )
            {
                newMouses[ numNewMouses++ ] = allMouses[ i ];
            }
        }
        
        if ( numNewMouses == 0 )
        {
            throw( new InputSystemException( "There's no available Mouse to register." ) );
        }
        
        registerMouse( newMouses[ 0 ] );
        
        return( newMouses[ 0 ] );
    }
    
    public void deregisterMouse( Mouse mouse ) throws InputSystemException
    {
        if ( mouses.length == 0 )
            throw( new InputSystemException( "This Mouse is not registered at this InputSystem." ) );
        
        boolean found = false;
        Mouse[] mouses2 = new Mouse[ mouses.length - 1 ];
        int j = 0;
        for ( int i = 0; i < mouses.length; i++ )
        {
            if ( mouses[ i ] == mouse )
                found = true;
            else
                mouses2[ j++ ] = mouses[ i ];
        }
        
        if ( !found )
            throw( new InputSystemException( "This Mouse is not registered at this InputSystem." ) );
        
        mouse.destroy();
        
        this.mouses = mouses2;
    }
    
    public final boolean hasMouse()
    {
        return( mouses.length > 0 );
    }
    
    public final int getMousesCount()
    {
        return( mouses.length );
    }
    
    /**
     * @return the first registered Mouse.
     */
    public final Mouse getMouse()
    {
        if ( mouses.length >= 1 )
            return( mouses[ 0 ] );
        
        return( null );
    }
    
    /**
     * @param index
     * 
     * @return the i-th registered Mouse.
     */
    public final Mouse getMouse( int index )
    {
        return( mouses[ index ] );
    }
    
    public final Mouse[] getMouses()
    {
        if ( ( mouses_out == null ) || ( mouses_out.length != mouses.length ) )
        {
            mouses_out = new Mouse[ mouses.length ];
            System.arraycopy( mouses, 0, mouses_out, 0, mouses.length );
        }
        
        return( mouses_out );
    }
    
    /**
     * Checks, wether the given Mouse is already registered at this InputSystem.
     * 
     * @param mouse
     * 
     * @return true, if the Mouse is registered.
     */
    public final boolean isMouseRegistered( Mouse mouse )
    {
        for ( int i = 0; i < mouses.length; i++ )
        {
            if ( mouses[ i ] == mouse )
                return( true );
        }
        
        return( false );
    }
    
    
    public void registerController( Controller controller ) throws InputSystemException
    {
        if ( !controller.getClass().isAssignableFrom( getDeviceFactory().getExpectedControllerClass() ) )
            throw( new InputSystemException( "This controller is not an instance of " + getDeviceFactory().getExpectedControllerClass() + "." ) );
        
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ] == controller )
                throw( new InputSystemException( "This Controller is already registered at this InputSystem." ) );
        }
        
        Controller[] controllers2 = new Controller[ controllers.length + 1 ];
        System.arraycopy( controllers, 0, controllers2, 0, controllers.length );
        controllers2[ controllers.length ] = controller;
        
        this.controllers = controllers2;
    }
    
    /**
     * Registers a new Controller.
     * 
     * @return the registered Controller.
     * 
     * @throws InputSystemException
     */
    public final Controller registerNewController() throws InputSystemException
    {
        Controller[] allControllers = getDeviceFactory().getControllers();
        Controller[] newControllers = new Controller[ allControllers.length ];
        int numNewControllers = 0;
        
        for ( int i = 0; i < allControllers.length; i++ )
        {
            boolean b = false;
            for ( int j = 0; j < this.controllers.length; j++ )
            {
                if ( this.controllers[ j ].equals( allControllers[ i ] ) )
                {
                    b = true;
                    break;
                }
            }
            
            if ( !b )
            {
                newControllers[ numNewControllers++ ] = allControllers[ i ];
            }
        }
        
        if ( numNewControllers == 0 )
        {
            throw( new InputSystemException( "There's no available Controller to register." ) );
        }
        
        registerController( newControllers[ 0 ] );
        
        return( newControllers[ 0 ] );
    }
    
    public void deregisterController( Controller controller ) throws InputSystemException
    {
        if ( controllers.length == 0 )
            throw( new InputSystemException( "This Controller is not registered at this InputSystem." ) );
        
        boolean found = false;
        Controller[] controllers2 = new Controller[ controllers.length - 1 ];
        int j = 0;
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ] == controller )
                found = true;
            else
                controllers2[ j++ ] = controllers[ i ];
        }
        
        if ( !found )
            throw( new InputSystemException( "This Controller is not registered at this InputSystem." ) );
        
        controller.destroy();
        
        this.controllers = controllers2;
    }
    
    public final boolean hasController()
    {
        return( controllers.length > 0 );
    }
    
    public final int getControllersCount()
    {
        return( controllers.length );
    }
    
    /**
     * @return the first registered Controller.
     */
    public final Controller getController()
    {
        if ( controllers.length >= 1 )
            return( controllers[ 0 ] );
        
        return( null );
    }
    
    /**
     * @param index
     * 
     * @return the i-th registered Controller.
     */
    public final Controller getController( int index )
    {
        return( controllers[ index ] );
    }
    
    public final Controller[] getControllers()
    {
        if ( ( controllers_out == null ) || ( controllers_out.length != controllers.length ) )
        {
            controllers_out = new Controller[ controllers.length ];
            System.arraycopy( controllers, 0, controllers_out, 0, controllers.length );
        }
        
        return( controllers_out );
    }
    
    /**
     * Checks, wether the given Controller is already registered at this InputSystem.
     * 
     * @param controller
     * 
     * @return true, if the Controller is registered.
     */
    public final boolean isControllerRegistered( Controller controller )
    {
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ] == controller )
                return( true );
        }
        
        return( false );
    }
    
    protected void collectKeyboardEvents( long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < keyboards.length; i++ )
        {
            if ( keyboards[ i ].isEnabled() )
                keyboards[ i ].collectEvents( this, eventQueue, nanoTime );
        }
    }
    
    protected void collectMouseEvents( long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < mouses.length; i++ )
        {
            if ( mouses[ i ].isEnabled() )
                mouses[ i ].collectEvents( this, eventQueue, nanoTime );
        }
    }
    
    protected void collectControllerEvents( EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ].isEnabled() )
                controllers[ i ].collectEvents( this, eventQueue, nanoTime );
        }
    }
    
    /**
     * Processes pending events from the system
     * and places them into the EventQueue.<br>
     * The events are not fired (listeners are not notified).
     * They are fired when the {@link #update(InputSystem, EventQueue, long)}
     * method is invoked.
     * 
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    public void collectEvents( long nanoTime ) throws InputSystemException
    {
        collectKeyboardEvents( nanoTime );
        collectMouseEvents( nanoTime );
        collectControllerEvents( eventQueue, nanoTime );
    }
    
    protected void updateKeyboards( long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < keyboards.length; i++ )
        {
            if ( keyboards[ i ].isEnabled() )
                keyboards[ i ].update( this, eventQueue, nanoTime );
        }
    }
    
    protected void updateMouses( long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < mouses.length; i++ )
        {
            if ( mouses[ i ].isEnabled() )
                mouses[ i ].update( this, eventQueue, nanoTime );
        }
    }
    
    protected void updateControllers( EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ].isEnabled() )
                controllers[ i ].update( this, eventQueue, nanoTime );
        }
    }
    
    /**
     * Processes pending events from the system
     * and directly fires them (notifes the listeners).<br>
     * This method also flushes the EventQueue, if the previously
     * called {@link #collectEvents(InputSystem, EventQueue, long)}
     * method placed events into it.
     * 
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    public void update( long nanoTime ) throws InputSystemException
    {
        updateKeyboards( nanoTime );
        updateMouses( nanoTime );
        updateControllers( eventQueue, nanoTime );
    }
    
    public void destroy() throws InputSystemException
    {
        for ( int i = keyboards.length - 1; i >= 0; i-- )
        {
            deregisterKeyboard( keyboards[ i ] );
        }
        
        for ( int i = mouses.length - 1; i >= 0; i-- )
        {
            deregisterMouse( mouses[ i ] );
        }
        
        for ( int i = controllers.length - 1; i >= 0; i-- )
        {
            deregisterController( controllers[ i ] );
        }
    }
    
    protected abstract DeviceFactory createDeviceFactory( InputSourceWindow sourceWindow, EventQueue eventQueue );
    
    public InputSystem( InputSourceWindow sourceWindow )
    {
        this.eventQueue = new EventQueue();
        this.deviceFactory = createDeviceFactory( sourceWindow, eventQueue );
    }
}
