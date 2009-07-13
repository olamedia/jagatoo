/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
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

/**
 * The {@link InputStatesManipulator} provides a simple interface for
 * key states manipulations.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputStatesManipulator
{
    private final Short[] states;
    
    /**
     * Sets the state for the given action.
     * 
     * @param action
     * @param state the state. Use null to not override the real input-state.
     */
    public final void setState( InputAction action, Short state )
    {
        states[ action.ordinal() ] = state;
    }
    
    /**
     * @param action
     * 
     * @return the current state for the given action. (null, if the state is not manipulated).
     */
    public final Short getState( InputAction action )
    {
        return ( states[ action.ordinal() ] );
    }
    
    /**
     * Applies the new key states to the {@link InputStatesManager}.
     */
    protected void apply( final short[] currStates )
    {
        for ( int i = 0; i < this.states.length; i++ )
        {
            if ( this.states[ i ] != null )
            {
                currStates[ i ] = this.states[ i ].shortValue();
            }
        }
    }
    
    protected InputStatesManipulator( InputStatesManager keyStatesManager )
    {
        this.states = new Short[ keyStatesManager.getNumStates() ];
        for ( int i = 0; i < states.length; i++ )
        {
            states[ i ] = null;
        }
    }
}
