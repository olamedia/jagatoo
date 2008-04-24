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

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.actions.InvokableInputAction;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.listeners.InputStateListener;
import org.jagatoo.logging.Log;

/**
 * The {@link InputBindingsAdapter} can be used to bind {@link DeviceComponent}s
 * to {@link InvokableInputAction}s.<br>
 * You must add the instance as an {@link InputStateListener} to the {@link InputSystem}
 * or one of its {@link InputDevice}s.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputBindingsAdapter< A extends InvokableInputAction > extends InputBindingsManager< A > implements InputStateListener
{
    /**
     * {@inheritDoc}
     */
    public void onInputStateChanged( InputEvent e, DeviceComponent comp, int delta, int state )
    {
        InvokableInputAction action = getBoundAction( comp );
        
        if ( ( action == null ) && ( comp != null ) && ( comp.getType() == DeviceComponent.Type.MOUSE_WHEEL ) )
        {
            action = getBoundAction( MouseWheel.GLOBAL_WHEEL );
        }
        
        if ( action != null )
        {
            try
            {
                action.invokeAction( null, comp, delta, state, e.getWhen() );
            }
            catch ( InputSystemException ex )
            {
                Log.print( InputSystem.LOG_CHANNEL, ex );
                ex.printStackTrace();
            }
        }
    }
    
    public InputBindingsAdapter( int numCommands )
    {
        super( numCommands );
    }
}
