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
package org.jagatoo.input.impl.awt;

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.DeviceFactory;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.InputSourceWindow;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class AWTDeviceFactory extends DeviceFactory
{
    @Override
    public Class< ? extends Mouse > getExpectedMouseClass()
    {
        return( AWTMouse.class );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected AWTMouse[] initMouses( Mouse[] currentMouses ) throws InputSystemException
    {
        if ( ( currentMouses != null ) && ( currentMouses.length == 1 ) )
        {
            if ( ( currentMouses[ 0 ] instanceof AWTMouse ) && ( currentMouses[ 0 ].getSourceWindow() == this.getSourceWindow() ) )
            {
                return( new AWTMouse[] { (AWTMouse)currentMouses[ 0 ] } );
            }
        }
        
        return( new AWTMouse[] { new AWTMouse( getSourceWindow(), getEveneQueue() ) } );
    }
    
    @Override
    public Class< ? extends Keyboard > getExpectedKeyboardClass()
    {
        return( AWTKeyboard.class );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected AWTKeyboard[] initKeyboards( Keyboard[] currentKeyboards ) throws InputSystemException
    {
        if ( ( currentKeyboards != null ) && ( currentKeyboards.length == 1 ) )
        {
            if ( ( currentKeyboards[ 0 ] instanceof AWTKeyboard ) && ( currentKeyboards[ 0 ].getSourceWindow() == this.getSourceWindow() ) )
            {
                return( new AWTKeyboard[] { (AWTKeyboard)currentKeyboards[ 0 ] } );
            }
        }
        
        return( new AWTKeyboard[] { new AWTKeyboard( getSourceWindow(), getEveneQueue() ) } );
    }
    
    @Override
    public Class< ? extends Controller > getExpectedControllerClass()
    {
        return( Controller.class );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Controller[] initControllers( Controller[] currentControllers ) throws InputSystemException
    {
        return( new Controller[ 0 ] );
    }
    
    public AWTDeviceFactory( InputSourceWindow sourceWindow, EventQueue eveneQueue )
    {
        super( sourceWindow, eveneQueue );
    }
}
