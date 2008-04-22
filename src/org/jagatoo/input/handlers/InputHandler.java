/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.input.handlers;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.managers.InputBindingsManager;
import org.jagatoo.input.managers.InputStatesManager;

/**
 * This is a basic abstract implementation of the InputHandler interface.
 * It has implementations for the most basic methods of InputHandler.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class InputHandler< A extends InputAction >
{
    public static final int MOUSE_MOVEMENT_SUSPENDED = 1;
    public static final int MOUSE_BUTTONS_SUSPENDED = 2;
    public static final int MOUSE_WHEEL_SUSPENDED = 4;
    public static final int KEYBOARD_SUSPENDED = 8;
    
    protected int suspendMask = 0;
    private InputSystem inputSystem = null;
    
    private boolean mouseSmoothingEnabled = false;
    
    private final InputBindingsManager< A > bindingsManager;
    private final InputStatesManager statesManager;
    
    public InputBindingsManager< A > getBindingsManager()
    {
        return( bindingsManager );
    }
    
    public InputStatesManager getStatesManager()
    {
        return( statesManager );
    }
    
    /**
     * Enables or disables mouse-smoothing.
     * 
     * @param enabled
     */
    public void setMouseSmoothingEnabled( boolean enabled )
    {
        this.mouseSmoothingEnabled = enabled;
    }
    
    /**
     * @return if mouse-smoothing is enabled.
     */
    public final boolean isMouseSmoothingEnabled()
    {
        return( mouseSmoothingEnabled );
    }
    
    /**
     * Suspends or resumes this FirstPersonInputHandler.<br>
     * You can OR MOUSE_SUSPENDED and KEYBOARD_SUSPENDED.<br>
     * <br>
     * If an FirstPersonInputAdapter is suspended, it will ignore any input.
     * 
     * @param suspendMask
     */
    public void setSuspendMask( int suspendMask )
    {
        this.suspendMask = suspendMask;
    }
    
    /**
     * @return the suspendMask.<br>
     * <br>
     * If a FirstPersonInputHandler is suspended, it will ignore any input.
     */
    public final int getSuspendMask()
    {
        return( suspendMask );
    }
    
    public void setMouseMovementSuspended( boolean suspended )
    {
        if ( suspended )
            setSuspendMask( suspendMask | MOUSE_MOVEMENT_SUSPENDED );
        else if ( isMouseMovementSuspended() )
            setSuspendMask( suspendMask - ( suspendMask & MOUSE_MOVEMENT_SUSPENDED ) );
    }
    
    public final boolean isMouseMovementSuspended()
    {
        return( ( suspendMask & MOUSE_MOVEMENT_SUSPENDED ) > 0 );
    }
    
    public void setMouseButtonsSuspended( boolean suspended )
    {
        if ( suspended )
            setSuspendMask( suspendMask | MOUSE_BUTTONS_SUSPENDED );
        else if ( isMouseMovementSuspended() )
            setSuspendMask( suspendMask - ( suspendMask & MOUSE_BUTTONS_SUSPENDED ) );
    }
    
    public final boolean isMouseButtonsSuspended()
    {
        return( ( suspendMask & MOUSE_BUTTONS_SUSPENDED ) > 0 );
    }
    
    public void setMouseWheelSuspended( boolean suspended )
    {
        if ( suspended )
            setSuspendMask( suspendMask | MOUSE_WHEEL_SUSPENDED );
        else if ( isMouseMovementSuspended() )
            setSuspendMask( suspendMask - ( suspendMask & MOUSE_WHEEL_SUSPENDED ) );
    }
    
    public boolean isMouseWheelSuspended()
    {
        return( ( suspendMask & MOUSE_WHEEL_SUSPENDED ) > 0 );
    }
    
    public void setMouseSuspended( boolean suspended )
    {
        setMouseMovementSuspended( suspended );
        setMouseButtonsSuspended( suspended );
        setMouseWheelSuspended( suspended );
    }
    
    public final boolean isMouseSuspended()
    {
        return( isMouseMovementSuspended() || isMouseButtonsSuspended() || isMouseWheelSuspended() );
    }
    
    public void setKeyboardSuspended( boolean suspended )
    {
        if ( suspended )
            setSuspendMask( suspendMask | KEYBOARD_SUSPENDED );
        else if ( isMouseMovementSuspended() )
            setSuspendMask( suspendMask - ( suspendMask & KEYBOARD_SUSPENDED ) );
    }
    
    public final boolean isKeyboardSuspended()
    {
        return( ( suspendMask & KEYBOARD_SUSPENDED ) > 0 );
    }
    
    /**
     * Suspends or resumes this {@link InputHandler}.<br>
     * <br>
     * If an {@link InputHandler} is suspended, it will ignore any input.
     * 
     * @param suspended
     */
    public void setSuspended( boolean suspended )
    {
        if ( suspended )
            setSuspendMask( MOUSE_MOVEMENT_SUSPENDED | MOUSE_BUTTONS_SUSPENDED | MOUSE_WHEEL_SUSPENDED | KEYBOARD_SUSPENDED );
        else
            setSuspendMask( 0 );
    }
    
    /**
     * @return if this {@link InputHandler} is currently suspended.<br>
     * <br>
     * If an {@link InputHandler} is suspended, it will ignore any input.
     */
    public final boolean isSuspended()
    {
        return( isKeyboardSuspended() || isMouseMovementSuspended() || isMouseButtonsSuspended() || isMouseWheelSuspended() );
    }
    
    /**
     * Must be invoked each frame (if not keyboard is suspended).
     */
    protected void pollInputStates( long nanoTime )
    {
        final Keyboard keyboard = isKeyboardSuspended() ? getInputSystem().getKeyboard() : null;
        final Mouse mouse = isMouseSuspended() ? getInputSystem().getMouse() : null;
        
        statesManager.update( getBindingsManager(), keyboard, mouse, nanoTime );
    }
    
    /**
     * This method is called by the InputSystem (each frame) to update the InputHandler.
     * 
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    public abstract void update( long nanoTime ) throws InputSystemException;
    
    /**
     * {@inheritDoc}
     */
    public void setInputSystem( InputSystem inputSystem )
    {
        this.inputSystem = inputSystem;
    }
    
    /**
     * {@inheritDoc}
     */
    public final InputSystem getInputSystem()
    {
        return( inputSystem );
    }
    
    protected InputStatesManager createInputStatesManager( int numActions )
    {
        return( new InputStatesManager( numActions ) );
    }
    
    public InputHandler( InputBindingsManager< A > bindingsManager )
    {
        this.bindingsManager = bindingsManager;
        if ( bindingsManager == null )
            this.statesManager = null;
        else
            this.statesManager = createInputStatesManager( bindingsManager.getNumActions() );
    }
}
