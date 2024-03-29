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
package org.jagatoo.input.events;

import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.components.InputState;
import org.jagatoo.input.devices.components.Key;

/**
 * This type of eevnt is fired when a key was pressed or released.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class KeyStateEvent extends KeyboardEvent
{
    public final InputState getKeyState()
    {
        return ( getKeyboard().getKeyState( getKey() ) );
    }
    
    public final boolean getKeyBooleanState()
    {
        return ( getKeyboard().isKeyPressed( getKey() ) );
    }
    
    /**
     * Creates a new KeyboardEvent with the default settings
     * 
     * @param subType
     */
    protected KeyStateEvent( SubType subType )
    {
        super( subType );
    }
    
    /**
     * Initialises the new KeyboardEvent using the given values.
     * 
     * @param keyboard
     * @param subType
     * @param key the key whose state changed
     * @param modifierMask
     * @param when the timestamp of the KeyboardEvent 
     * @param lastWhen
     */
    protected KeyStateEvent( Keyboard keyboard, SubType subType, Key key, int modifierMask, long when, long lastWhen )
    {
        this( subType );
        
        set( keyboard, key, modifierMask, when, lastWhen );
    }
}
