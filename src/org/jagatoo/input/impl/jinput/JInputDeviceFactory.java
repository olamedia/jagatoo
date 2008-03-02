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
package org.jagatoo.input.impl.jinput;

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.DeviceFactory;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.Canvas;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class JInputDeviceFactory extends DeviceFactory
{
    @Override
    public Class< ? extends Mouse > getExpectedMouseClass()
    {
        return( JInputMouse.class );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JInputMouse[] initMouses() throws InputSystemException
    {
        net.java.games.input.Controller[] controllers = net.java.games.input.ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        JInputMouse[] tmpMouses = new JInputMouse[ controllers.length ];
        int numMouses = 0;
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ].getType() == net.java.games.input.Controller.Type.MOUSE )
            {
                tmpMouses[ numMouses++ ] = new JInputMouse( getEveneQueue(), getCanvas(), (net.java.games.input.Mouse)controllers[ i ] );
            }
        }
        
        JInputMouse[] mouses = new JInputMouse[ numMouses ];
        System.arraycopy( tmpMouses, 0, mouses, 0, numMouses );
        
        return( mouses );
    }
    
    @Override
    public Class< ? extends Keyboard > getExpectedKeyboardClass()
    {
        return( JInputKeyboard.class );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JInputKeyboard[] initKeyboards() throws InputSystemException
    {
        net.java.games.input.Controller[] controllers = net.java.games.input.ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        JInputKeyboard[] tmpKeyboards = new JInputKeyboard[ controllers.length ];
        int numKeyboards = 0;
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( controllers[ i ].getType() == net.java.games.input.Controller.Type.KEYBOARD )
            {
                if ( controllers[ i ].getComponents().length > 10 )
                {
                    tmpKeyboards[ numKeyboards++ ] = new JInputKeyboard( getEveneQueue(), getCanvas(), (net.java.games.input.Keyboard)controllers[ i ] );
                }
            }
        }
        
        JInputKeyboard[] keyboards = new JInputKeyboard[ numKeyboards ];
        System.arraycopy( tmpKeyboards, 0, keyboards, 0, numKeyboards );
        
        return( keyboards );
    }
    
    @Override
    public Class< ? extends Controller > getExpectedControllerClass()
    {
        return( JInputController.class );
    }
    
    private static final boolean isController( net.java.games.input.Controller.Type type )
    {
        if ( type == net.java.games.input.Controller.Type.FINGERSTICK )
            return( true );
        if ( type == net.java.games.input.Controller.Type.GAMEPAD )
            return( true );
        if ( type == net.java.games.input.Controller.Type.HEADTRACKER )
            return( true );
        if ( type == net.java.games.input.Controller.Type.RUDDER )
            return( true );
        if ( type == net.java.games.input.Controller.Type.STICK )
            return( true );
        if ( type == net.java.games.input.Controller.Type.TRACKBALL )
            return( true );
        if ( type == net.java.games.input.Controller.Type.TRACKPAD )
            return( true );
        if ( type == net.java.games.input.Controller.Type.WHEEL )
            return( true );
        
        return( false );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JInputController[] initControllers() throws InputSystemException
    {
        net.java.games.input.Controller[] controllers = net.java.games.input.ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        JInputController[] tmpControllers = new JInputController[ controllers.length ];
        int numControllers = 0;
        for ( int i = 0; i < controllers.length; i++ )
        {
            if ( isController( controllers[ i ].getType() ) )
            {
                if ( controllers[ i ].getComponents().length > 10 )
                {
                    tmpControllers[ numControllers++ ] = new JInputController( getEveneQueue(), (net.java.games.input.Controller)controllers[ i ] );
                }
            }
        }
        
        JInputController[] controllers2 = new JInputController[ numControllers ];
        System.arraycopy( tmpControllers, 0, controllers2, 0, numControllers );
        
        return( controllers2 );
    }
    
    public JInputDeviceFactory( Canvas canvas, EventQueue eveneQueue )
    {
        super( canvas, eveneQueue );
    }
}
