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

import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.actions.InvokableInputAction;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.DigiState;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.devices.components.MouseWheel.WheelUpDownComponent;

/**
 * Manages state-changes on any kind of {@link InputDevice}.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputStatesManager
{
    private InputStatesManipulator manipulator = null;
    
    protected final short[] states1;
    protected final short[] states2;
    
    protected short[] prevStates;
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
    public final DigiState getInputState( InputAction action )
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
    public final int getSimpleInputState( InputAction action )
    {
        return( currStates[ action.ordinal() ] );
    }
    
    /**
     * @param action
     * 
     * @return whether the state for the given action's state has changed.
     */
    public final boolean hasInputStateChanged( InputAction action )
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
    private final void pollInputStates( final InputBindingsManager< ? extends InputAction > inputBindings, final Keyboard keyboard, final Mouse mouse, final short[] prevStates, final short[] currStates )
    {
        InputDevice device = null;
        final DeviceComponent[][] boundKeys = inputBindings.boundKeys;
        final int numActions = boundKeys.length;
        final int numBindingSets = InputBindingsManager.NUM_KEY_SETS;
        
        boolean flag = false;
        for ( int i = 0; i < numActions; i++ )
        {
            flag = false;
            currStates[ i ] = 0;
            for ( int j = 0; ( j < numBindingSets ) && !flag && ( boundKeys[ i ] != null ); j++ )
            {
                final DeviceComponent comp = boundKeys[ i ][ j ];
                
                switch ( comp.getType() )
                {
                    case KEY:
                        flag = true;
                        
                        if ( keyboard == null )
                            continue;
                        
                        device = keyboard;
                        currStates[ i ] = keyboard.isKeyPressed( (Key)comp ) ? (short)1 : (short)0;
                        break;
                    case MOUSE_AXIS:
                        flag = true;
                        
                        if ( mouse == null )
                            continue;
                        
                        final MouseAxis mAxis = (MouseAxis)comp;
                        device = mAxis.getMouse();
                        currStates[ i ] = (short)mAxis.getIntValue();
                        break;
                    case MOUSE_BUTTON:
                        flag = true;
                        
                        if ( mouse == null )
                            continue;
                        
                        currStates[ i ] = mouse.isButtonPressed( (MouseButton)comp ) ? (short)1 : (short)0;
                        device = mouse;
                        break;
                    case MOUSE_WHEEL:
                        flag = true;
                        if ( comp instanceof WheelUpDownComponent )
                        {
                            WheelUpDownComponent mWheelUD = (WheelUpDownComponent)comp;
                            MouseWheel mWheel = (MouseWheel)comp;
                            
                            if ( mWheel == MouseWheel.GLOBAL_WHEEL )
                            {
                                if ( mouse == null )
                                    continue;
                                
                                mWheel = mouse.getWheel();
                                mWheelUD = ( mWheelUD.getIntValue() > 0 ) ? mWheel.getUp() : mWheel.getDown();
                            }
                            
                            device = mWheel.getMouse();
                            
                            prevStates[ i ] = (short)0;
                            currStates[ i ] = (short)mWheelUD.getIntValue();
                        }
                        else
                        {
                            MouseWheel mWheel = (MouseWheel)comp;
                            
                            if ( mWheel == MouseWheel.GLOBAL_WHEEL )
                            {
                                if ( mouse == null )
                                    continue;
                                
                                mWheel = mouse.getWheel();
                            }
                            
                            device = mWheel.getMouse();
                            currStates[ i ] = (short)mWheel.getIntValue();
                        }
                        break;
                    case CONTROLLER_AXIS:
                        flag = true;
                        ControllerAxis cAxis = (ControllerAxis)comp;
                        device = cAxis.getController();
                        currStates[ i ] = (short)cAxis.getIntValue();
                        break;
                    case CONTROLLER_BUTTON:
                        flag = true;
                        ControllerButton cButton = (ControllerButton)comp;
                        device = cButton.getController();
                        currStates[ i ] = cButton.getBooleanState() ? (short)1 : (short)0;
                        break;
                }
            }
            
            if ( currStates[ i ] != prevStates[ i ] )
            {
                int j = 0;
                DeviceComponent comp = boundKeys[ i ][ j ];
                while ( comp == null )
                {
                    comp = boundKeys[ i ][ j++ ];
                }
                
                final InputAction action = inputBindings.getBoundAction( comp );
                
                if ( action instanceof InvokableInputAction )
                {
                    final InvokableInputAction invAction = (InvokableInputAction)action;
                    
                    invAction.invokeAction( device, comp, ( currStates[ i ] - prevStates[ i ] ), currStates[ i ] );
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
    public void update( final InputBindingsManager< ? extends InputAction > inputBindings, final Keyboard keyboard, final Mouse mouse )
    {
        if ( keyboard == null )
        {
            System.err.println( "null keyboard" );
            return;
        }
        
        swapper = !swapper;
        
        if ( swapper )
        {
            currStates = states1;
            prevStates = states2;
        }
        else
        {
            currStates = states2;
            prevStates = states1;
        }
        
        pollInputStates( inputBindings, keyboard, mouse, prevStates, currStates );
        
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
