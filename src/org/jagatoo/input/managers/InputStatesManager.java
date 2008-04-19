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
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.DigiState;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;

/**
 * Manages key states.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputStatesManager
{
    private InputStatesManipulator manipulator = null;
    
    protected final short[] states1;
    protected final short[] states2;
    
    protected short[] currStates;
    
    protected boolean swapper = false;
    
    /**
     * @return the {@link InputStatesManipulator} for this manager.
     */
    public InputStatesManipulator getKeyStatesManipulator()
    {
        if ( manipulator == null )
        {
            manipulator = new InputStatesManipulator( this );
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
    
    /**
     * @param action
     * 
     * @return the current key-state for the given action.
     */
    public final DigiState getInputState( InputActionInterface action )
    {
        final int ordinal = action.ordinal();
        
        if ( states1[ ordinal ] == states2[ ordinal ] )
        {
            if ( states1[ ordinal ] > 0 )
                return( DigiState.POSITIVE );
            else
                return( DigiState.NEGATIVE );
        }
        else if ( swapper )
        {
            if ( states1[ ordinal ] > states2[ ordinal ] )
                return( DigiState.MADE_POSITIVE );
            else
                return( DigiState.MADE_NEGATIVE );
        }
        else
        {
            if ( states2[ ordinal ] > states1[ ordinal ] )
                return( DigiState.MADE_POSITIVE );
            else
                return( DigiState.MADE_NEGATIVE );
        }
    }
    
    /**
     * @param action
     * 
     * @return the current key-state for the given action.
     */
    public final short getSimpleInputState( InputActionInterface action )
    {
        return( currStates[ action.ordinal() ] );
    }
    
    /**
     * @param action
     * 
     * @return whether the state for the given action's state has changed.
     */
    public final boolean hasInputStateChanged( InputActionInterface action )
    {
        return( states1[ action.ordinal() ] != states2[ action.ordinal() ] );
    }
    
    /*
    public void applyStates( short[] states )
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
     * Polls the key-states for all bound keys and writes them into the boolean array.
     * 
     * @param boundKeys the input-bindings
     * @param keyboard the {@link Keyboard} to take the states from
     * @param mouse the {@link Mouse} to take the states from
     * @param states the target state-array. Must have at least the same length as the number of actions.
     */
    private final void pollInputStates( final DeviceComponent[][] boundKeys, final Keyboard keyboard, final Mouse mouse, final short[] states )
    {
        final int numActions = boundKeys.length;
        final int numBindingSets = InputBindingsManager.NUM_KEY_SETS;
        
        boolean flag = false;
        for ( int i = 0; i < numActions; i++ )
        {
            flag = false;
            states[ i ] = 0;
            for ( int j = 0; ( j < numBindingSets ) && !flag && ( boundKeys[ i ] != null ); j++ )
            {
                final DeviceComponent comp = boundKeys[ i ][ j ];
                
                switch ( comp.getType() )
                {
                    case KEY:
                        states[ i ] = keyboard.isKeyPressed( (Key)comp ) ? (short)1 : (short)0;
                        flag = true;
                        break;
                    case MOUSE_AXIS:
                        final MouseAxis axis = (MouseAxis)comp;
                        states[ i ] = (short)axis.getIntValue();
                        flag = true;
                        break;
                    case MOUSE_BUTTON:
                        states[ i ] = mouse.isButtonPressed( (MouseButton)comp ) ? (short)1 : (short)0;
                        flag = true;
                        break;
                    case MOUSE_WHEEL:
                        states[ i ] = (short)mouse.getWheel().getIntValue();
                        flag = true;
                        break;
                    case CONTROLLER_AXIS:
                        ControllerAxis cAxis = (ControllerAxis)comp;
                        states[ i ] = (short)cAxis.getIntValue();
                        flag = true;
                        break;
                    case CONTROLLER_BUTTON:
                        ControllerButton cButton = (ControllerButton)comp;
                        states[ i ] = cButton.getBooleanState() ? (short)1 : (short)0;
                        flag = true;
                        break;
                }
            }
        }
    }
    
    /**
     * Updates the key-states array.
     * 
     * @param inputBindings the {@link InputBindingsManager} to use for input-bindings
     * @param keyboard the {@link Keyboard} to take the states from
     * @param mouse the {@link Mouse} to take the states from
     */
    public void update( final InputBindingsManager< ? extends InputActionInterface > inputBindings, final Keyboard keyboard, final Mouse mouse )
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
        
        pollInputStates( inputBindings.boundKeys, keyboard, mouse, currStates );
        
        if ( manipulator != null )
        {
            manipulator.apply( currStates );
        }
    }
    
    /**
     * Creates a new {@link InputStatesManager}.
     * 
     * @param numActions the number of available (bindeable) actions.
     */
    public InputStatesManager( final int numActions )
    {
        this.states1 = new short[ numActions ];
        this.states2 = new short[ numActions ];
        
        for ( int i = 0; i < numActions; i++ )
        {
            states1[ i ] = 0;
            states2[ i ] = 0;
        }
        
        currStates = states2;
        
        swapper = false;
    }
}
