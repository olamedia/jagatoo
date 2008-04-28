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
import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.listeners.InputListener;
import org.jagatoo.input.listeners.InputStateListener;
import org.jagatoo.input.managers.InputBindingsManager;

/**
 * This is a simple {@link InputHandler} extension, that additionally
 * implements {@link InputListener} and {@link InputStateListener} and
 * keeps empty-stub-implementations of these methods like an adapter.
 * It is automatically added as listener to the {@link InputSystem}.
 * So you don't have to care about that in any way. (Just override
 * the empty method stubs for the event methods.)
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ListeningInputHandler< A extends InputAction > extends InputHandler< A > implements InputListener, InputStateListener
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputSystem( InputSystem inputSystem )
    {
        if ( inputSystem == this.getInputSystem() )
            return;
        
        if ( this.getInputSystem() != null )
        {
            getInputSystem().removeInputListener( this );
            getInputSystem().removeInputStateListener( this );
        }
        
        super.setInputSystem( inputSystem );
        
        if ( inputSystem != null )
        {
            getInputSystem().addInputListener( this );
            getInputSystem().addInputStateListener( this );
        }
    }
    
    public ListeningInputHandler( InputBindingsManager< A > bindingsManager )
    {
        super( bindingsManager );
    }
}
