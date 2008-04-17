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
package org.jagatoo.input.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jagatoo.input.actions.InputActionInterface;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.MouseButton;

/**
 * This is a generic KeyBindings handler.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputBindingsManager< A extends InputActionInterface >
{
    private static final int NUM_KEY_SETS = InputBindingsSet.values().length;
    
    private final HashMap< DeviceComponent, A > keyBindings;
    private final DeviceComponent[][] boundKeys;
    
    private final ArrayList< InputBindingListener< A > > listeners = new ArrayList< InputBindingListener< A > >();
    
    public final void addKeyBindingListener( InputBindingListener< A > l )
    {
        listeners.add( l );
    }
    
    public final void removeKeyBindingListener( InputBindingListener< A > l )
    {
        listeners.remove( l );
    }
    
    protected final void notifyBound( DeviceComponent key, InputBindingsSet set, A action )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onInputComponentBound( key, set, action );
        }
    }
    
    protected final void notifyUnbound( DeviceComponent key, InputBindingsSet set, A action )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onInputComponentUnbound( key, set, action );
        }
    }
    
    public final int getNumActions()
    {
        return( boundKeys.length );
    }
    
    public final Set< DeviceComponent > getBoundKeys()
    {
        return( keyBindings.keySet() );
    }
    
    public final Collection< A > getBoundActions()
    {
        return( keyBindings.values() );
    }
    
    /**
     * Unbinds a specific key.
     * 
     * @param key the key to unbind
     * 
     * @return the {@link InputActionInterface}, this key was previously bound to
     */
    public final A unbindKey( DeviceComponent key )
    {
        final A prevBound = keyBindings.remove( key );
        
        if ( prevBound != null )
        {
            final int ordinal = prevBound.ordinal();
            
            if ( boundKeys[ ordinal ] != null )
            {
                for ( int i = 0; i < NUM_KEY_SETS; i++ )
                {
                    if ( boundKeys[ ordinal ][ i ] == key )
                    {
                        /*
                        for ( int j = i + 1; j < NUM_KEY_SETS; j++ )
                        {
                            boundKeys[ ordinal ][ j - 1 ] = boundKeys[ ordinal ][ j ];
                            boundKeys[ ordinal ][ j ] = 0;
                        }
                        */
                        boundKeys[ ordinal ][ i ] = null;
                        
                        notifyUnbound( key, InputBindingsSet.values()[ i ], prevBound );
                        
                        return( prevBound );
                    }
                }
            }
        }
        
        return( null );
    }
    
    /**
     * Unbinds a key from a specific KeyCommand.
     * 
     * @param action the InputAction to un-map from its key
     * 
     * @return the key, that was previously bound to this command.
     */
    public final DeviceComponent unbindKey( A action )
    {
        DeviceComponent[] bounds = boundKeys[ action.ordinal() ];
        
        if ( bounds == null )
            return( null );
        
        DeviceComponent prev = null;
        DeviceComponent[] prevs = new DeviceComponent[ NUM_KEY_SETS ];
        for ( int i = 0; i < NUM_KEY_SETS; i++ )
        {
            if ( ( prev == null ) && ( bounds[ i ] != null ) )
                prev = bounds[ i ];
            
            prevs[ i ] = bounds[ i ];
        }
        
        for ( int i = 0; i < prevs.length; i++ )
        {
            if ( prevs[ i ] != null )
                unbindKey( prevs[ i ] );
        }
        
        return( prev );
    }
    
    /**
     * @param key the key of the requested InputAction
     * @param set the {@link InputBindingsSet} (may be null for an arbitrary set)
     * 
     * @return the InputAction, that is currently bound to the given key.
     */
    public final A getBoundAction( DeviceComponent key, InputBindingsSet set )
    {
        if ( set == null )
            return( keyBindings.get( key ) );
        
        final A result = keyBindings.get( key );
        
        if ( ( boundKeys[ result.ordinal() ] != null ) && ( boundKeys[ result.ordinal() ][ set.ordinal() ] == key ) )
            return( result );
        
        return( null );
    }
    
    /**
     * @param key the key of the requested InputAction
     * 
     * @return the InputAction, that is currently bound to the given key.
     */
    public final A getBoundAction( DeviceComponent key )
    {
        return( getBoundAction( key, null ) );
    }
    
    /**
     * @param action
     * @param set
     * 
     * @return the key, the given action is bound to.
     */
    public final DeviceComponent getBoundKey( A action, InputBindingsSet set )
    {
        final DeviceComponent[] keys = boundKeys[ action.ordinal() ];
        
        if ( keys == null )
            return( null );
        else
            return( keys[ set.ordinal() ] );
    }
    
    /**
     * Binds a key to a specific InputAction.
     * 
     * @param key the key to bind
     * @param action the InputAction to map to the given key
     * @param set the {@link InputBindingsSet} the key is to be bound at. (may be null to take the next free set or the PRIMARY)
     * 
     * @return the {@link InputBindingsSet}, the key has been bound at.
     */
    public final InputBindingsSet bindKey( DeviceComponent key, A action, InputBindingsSet set )
    {
        DeviceComponent[] keys = boundKeys[ action.ordinal() ];
        
        if ( keys == null )
        {
            keys = new DeviceComponent[ NUM_KEY_SETS ];
            boundKeys[ action.ordinal() ] = keys;
            for ( int i = 0; i < NUM_KEY_SETS; i++ )
                keys[ i ] = null;
        }
        
        if ( set == null )
        {
            for ( int i = 0; i < NUM_KEY_SETS; i++ )
            {
                if ( keys[ i ] == null )
                {
                    set = InputBindingsSet.values()[ i ];
                    break;
                }
            }
            
            if ( set == null )
                set = InputBindingsSet.PRIMARY;
        }
        
        if ( keys[ set.ordinal() ] != null )
        {
            unbindKey( key );
        }
        
        keys[ set.ordinal() ] = key;
        keyBindings.put( key, action );
        
        notifyBound( key, set, action );
        
        return( set );
    }
    
    /**
     * Binds a key to a specific InputAction at the fist free {@link InputBindingsSet} or {@link InputBindingsSet#PRIMARY}.
     *
     * @param key the key to bind
     * @param action the InputAction to map to the given key
     * 
     * @return the {@link InputBindingsSet}, the key has been bound at.
     */
    public final InputBindingsSet bindKey( DeviceComponent key, A action )
    {
        return( bindKey( key, action, null ) );
    }
    
    /**
     * Clears the key bindings Map.
     * 
     * @param set
     */
    public final void unbindAllKeys( InputBindingsSet set )
    {
        for ( int i = 0; i < boundKeys.length; i++ )
        {
            if ( boundKeys[ i ] != null )
            {
                if ( set == null )
                {
                    for ( int j = 0; j < NUM_KEY_SETS; j++ )
                    {
                        if ( boundKeys[ i ][ j ] != null )
                        {
                            notifyUnbound( boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], keyBindings.get( boundKeys[ i ][ j ] ) );
                            
                            keyBindings.remove( boundKeys[ i ][ j ] );
                            boundKeys[ i ][ j ] = null;
                        }
                    }
                }
                else
                {
                    final int j = set.ordinal();
                    if ( boundKeys[ i ][ j ] != null )
                    {
                        notifyUnbound( boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], keyBindings.get( boundKeys[ i ][ j ] ) );
                        
                        keyBindings.remove( boundKeys[ i ][ j ] );
                        boundKeys[ i ][ j ] = null;
                    }
                }
            }
        }
    }
    
    /**
     * Clears the key bindings Map.
     */
    public final void unbindAllKeys()
    {
        unbindAllKeys( null );
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void setKeyBindings( Map< DeviceComponent, ? extends A > keyBindings, boolean clearBefore )
    {
        if ( clearBefore )
            unbindAllKeys();
        
        for ( DeviceComponent key: keyBindings.keySet() )
        {
            bindKey( key, keyBindings.get( key ) );
        }
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void setKeyBindings( Map< DeviceComponent, ? extends A > keyBindings )
    {
        setKeyBindings( keyBindings, true );
    }
    
    /**
     * @return a Map with the KeyCodes mapped to InputActions.
     */
    public Map< DeviceComponent, A > getKeyBindingsMap()
    {
        return( keyBindings );
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void set( InputBindingsManager< ? extends A > keyBindings, boolean clearBefore )
    {
        if ( clearBefore )
            unbindAllKeys();
        
        final int n = Math.min( this.boundKeys.length, keyBindings.boundKeys.length );
        
        for ( int i = 0; i < n; i++ )
        {
            if ( keyBindings.boundKeys[ i ] == null )
            {
                if ( this.boundKeys[ i ] != null )
                {
                    for ( int j = 0; j < NUM_KEY_SETS; j++ )
                        this.boundKeys[ i ][ j ] = null;
                }
            }
            else
            {
                if ( this.boundKeys[ i ] == null )
                    this.boundKeys[ i ] = new DeviceComponent[ NUM_KEY_SETS ];
                
                for ( int j = 0; j < NUM_KEY_SETS; j++ )
                {
                    this.boundKeys[ i ][ j ] = keyBindings.boundKeys[ i ][ j ];
                    
                    if ( keyBindings.boundKeys[ i ][ j ] != null )
                    {
                        notifyBound( keyBindings.boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], keyBindings.keyBindings.get( keyBindings.boundKeys[ i ][ j ] ) );
                    }
                }
            }
        }
        
        for ( DeviceComponent key: keyBindings.keyBindings.keySet() )
        {
            this.keyBindings.put( key, keyBindings.keyBindings.get( key ) );
        }
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void set( InputBindingsManager< ? extends A > keyBindings )
    {
        set( keyBindings, true );
    }
    
    /**
     * Polls the key-states for all bound keys and writes them into the boolean array.
     * 
     * @param keyboard the {@link Keyboard} to take the states from
     * @param mouse the {@link Mouse} to take the states from
     * @param controllers the {@link Controller}s to take the states from
     * @param states the target state-array. Must have at least the same length as the number of actions.
     */
    public final void pollInputStates( final Keyboard keyboard, final Mouse mouse, final Controller[] controllers, final boolean[] states )
    {
        for ( int i = 0; i < boundKeys.length; i++ )
        {
            states[ i ] = false;
            for ( int j = 0; ( j < NUM_KEY_SETS ) && !states[ i ] && ( boundKeys[ i ] != null ); j++ )
            {
                switch ( boundKeys[ i ][ j ].getType() )
                {
                    case KEY:
                        states[ i ] = keyboard.isKeyPressed( (Key)boundKeys[ i ][ j ] );
                        break;
                    case MOUSE_BUTTON:
                        states[ i ] = mouse.isButtonPressed( (MouseButton)boundKeys[ i ][ j ] );
                        break;
                    case CONTROLLER_BUTTON:
                        if ( controllers != null )
                        {
                            for ( int c = 0; ( c < controllers.length ) && !states[ i ]; c++ )
                                states[ i ] = controllers[ c ].isButtonPressed( (ControllerButton)boundKeys[ i ][ j ] );
                            break;
                        }
                }
            }
        }
    }
    
    public InputBindingsManager( int numCommands )
    {
        this.keyBindings = new HashMap< DeviceComponent, A >();
        this.boundKeys = new DeviceComponent[ numCommands ][];
    }
}
