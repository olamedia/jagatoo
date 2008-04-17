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

import org.jagatoo.input.actions.InputActionInterface;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.DigitalDeviceComponent.DigiState;

/**
 * Manages key states.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class KeyStatesManager
{
    private final KeyBindingsManager< ? extends InputActionInterface > keyBindings;
    
    private KeyStatesManipulator manipulator = null;
    
    protected final boolean[] states1;
    protected final boolean[] states2;
    
    protected boolean[] currStates;
    
    protected boolean swapper = false;
    
    /**
     * @return the {@link KeyStatesManipulator} for this manager.
     */
    public KeyStatesManipulator getKeyStatesManipulator()
    {
        if ( manipulator == null )
        {
            manipulator = new KeyStatesManipulator( this );
        }
        
        return( manipulator );
    }
    
    /**
     * @return the number of key-states (or commands).
     */
    public final int getNumStates()
    {
        return( states1.length );
    }
    
    public final KeyBindingsManager< ? extends InputActionInterface > getKeyBindings()
    {
        return( keyBindings );
    }
    
    /**
     * @param command
     * 
     * @return the current key-state for the given command.
     */
    public final DigiState getKeyState( InputActionInterface command )
    {
        final int ordinal = command.ordinal();
        
        if ( swapper )
        {
            if ( states1[ ordinal ] )
            {
                if ( states2[ ordinal ] )
                    return( DigiState.DOWN );
                else
                    return( DigiState.DOWNED );
            }
            else
            {
                if ( states2[ ordinal ] )
                    return( DigiState.UPPED );
                else
                    return( DigiState.UP );
            }
        }
        else
        {
            if ( states2[ ordinal ] )
            {
                if ( states1[ ordinal ] )
                    return( DigiState.DOWN );
                else
                    return( DigiState.DOWNED );
            }
            else
            {
                if ( states1[ ordinal ] )
                    return( DigiState.UPPED );
                else
                    return( DigiState.UP );
            }
        }
    }
    
    /**
     * @param command
     * 
     * @return the current key-state for the given command.
     */
    public final boolean getSimpleKeyState( InputActionInterface command )
    {
        return( currStates[ command.ordinal() ] );
    }
    
    /**
     * @param command
     * 
     * @return whether the state for the given command's state has changed.
     */
    public final boolean hasKeyStateChanged( InputActionInterface command )
    {
        return( states1[ command.ordinal() ] != states2[ command.ordinal() ] );
    }
    
    /*
    public void applyStates( boolean[] states )
    {
        swapper = !swapper;
        
        if ( swapper )
            currStates = states1;
        else
            currStates = states2;
        
        System.arraycopy( states, 0, currStates, 0, currStates.length );
    }
    */
    
    /**
     * Updates the key-states array.
     * 
     * @param keyboard the {@link Keyboard} to take the states from
     * @param mouse the {@link Mouse} to take the states from
     * @param controllers the {@link Controller}s to take the states from
     */
    public void update( final Keyboard keyboard, final Mouse mouse, final Controller[] controllers )
    {
        if ( keyboard == null )
        {
            System.err.println( "null keyboard" );
            return;
        }
        
        swapper = !swapper;
        
        if ( swapper )
            currStates = states1;
        else
            currStates = states2;
        
        keyBindings.pollInputStates( keyboard, mouse, controllers, currStates );
        
        if ( manipulator != null )
        {
            manipulator.apply( currStates );
        }
    }
    
    public KeyStatesManager( KeyBindingsManager< ? extends InputActionInterface > keyBindings )
    {
        this.keyBindings = keyBindings;
        
        final int numActions = keyBindings.getNumActions();
        
        this.states1 = new boolean[ numActions ];
        this.states2 = new boolean[ numActions ];
        
        for ( int i = 0; i < numActions; i++ )
        {
            states1[ i ] = false;
            states2[ i ] = false;
        }
        
        currStates = states2;
        
        swapper = false;
    }
}
